package com.cos.blog.config.auth;

import com.cos.blog.model.UserModel;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청(/auth/loginProc)을 가로채서 로그인을 진행하고 완료가되면,
// UserDetails 타입의 오브젝트를 시큐리티의 고유한 세션 저장소에 저장을 함.
@Getter
public class PrincipalDetail implements UserDetails {

    private UserModel userModel; // composition

    public PrincipalDetail(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public String getPassword() {
        return userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return userModel.getUsername();
    }

    // 계정이 만료되었는지 않았는지를 리턴한다(false면 만료)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는지 않았는지를 리턴한다(false면 잠김)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비번이 만료되었는지 않았는지를 리턴한다(false면 만료)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정의 활성화인지를 리턴(true면 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정의 갖고 있는 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(() -> {
            return "ROLE_" + userModel.getRole(); // ROLE_USER
        });
        return collections;
    }
}
