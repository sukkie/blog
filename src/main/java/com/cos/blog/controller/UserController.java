package com.cos.blog.controller;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserModel;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

// 인증이 안된 사용자들도 출입할 수 있는 경로 /auth/**
// /index.jsp 도 허용
// /static 이하도 허용 /js/**, /css/**, /image/**
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) { // 데이터를 리턴해주는 컨트롤로 함수

        // POST방식으로 key=value 데이터를 요청(카카오 서버로)
        // Retrofit2
        // OkHttp
        // RestTemplate

        // AccessToken 취득
        RestTemplate rt = new RestTemplate();

        // httpheader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // httpbody
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "af17ff2942ba6d33ba4d235915a60749");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        // httpheader, httpbody를 하나에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequst = new HttpEntity<>(params, headers);

        // http요청하기
        ResponseEntity<String> re = rt.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequst,
                String.class);

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(re.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        // AccessToken을 가지고 개인정보 취득
        RestTemplate rt2 = new RestTemplate();

        // httpheader
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // httpheader, httpbody를 하나에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequst = new HttpEntity<>(headers2);

        // http요청하기
        ResponseEntity<String> re2 = rt2.exchange("https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequst,
                String.class);

        System.out.println(re2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(re2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("카카오 번호 : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakaoAccount().getEmail());

        System.out.println("블로그 유저네임 : " + kakaoProfile.getKakaoAccount().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("블로그 이메일 : " + kakaoProfile.getKakaoAccount().getEmail());
        System.out.println("블로그 권한 : " + RoleType.USER);

        UserModel kakakoUserModel = UserModel.builder().username(kakaoProfile.getKakaoAccount().getEmail() + "_" + kakaoProfile.getId())
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .password(kakaoProfile.getKakaoAccount().getEmail() + "_" + kakaoProfile.getId())
                .role(RoleType.USER)
                .oauth("kakao")
                .build();

        // 이미 가입자인지 확인 필요
        // 회원 찾기
        UserModel originUserModel = userService.회원찾기(kakakoUserModel.getUsername());

        // 회원가입 진행
        if (originUserModel == null) {
            userService.회원가입(kakakoUserModel);
        }

        // 세션등록
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(kakakoUserModel.getUsername(),
                        kakaoProfile.getKakaoAccount().getEmail() + "_" + kakaoProfile.getId()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
