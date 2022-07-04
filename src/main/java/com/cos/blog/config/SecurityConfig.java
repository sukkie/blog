package com.cos.blog.config;


import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 빈등록
@EnableWebSecurity // 시큐리티 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리체크하겠다
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    // 스프링 시큐리티가 로그인 처리를 진행할때 비밀번호 암호화에 사용했던 해쉬값을 알아야 비밀번호 체크가 진행 됨.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // /auth/** 는 허용해주고 나머지는 모두 인증을 거쳐야 함.
        http
                .csrf().disable()   // csrf토큰 비활성화
                .headers().frameOptions().disable() // frame 활성
                .and()
                .authorizeRequests()
                .antMatchers("/", "/auth/**", "/h2-console/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
                .permitAll() // 위 URL은 인증없이 허용
                .anyRequest()
                .authenticated()
                .and()
                // 인증이 걸린 페이지로 들어오면 로그인 페이지로 이동시킴
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소의 요청을 가로채 로그인 처리를 함.
                .defaultSuccessUrl("/"); // 로그  완료 후 / 로 이동.
    }
}
