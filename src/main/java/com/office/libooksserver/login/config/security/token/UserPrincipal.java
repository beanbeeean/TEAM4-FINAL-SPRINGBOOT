package com.office.libooksserver.login.config.security.token;

import com.office.libooksserver.user.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Log4j2
public class UserPrincipal implements OAuth2User, UserDetails{
    
    private Long u_no;
    private String u_email;
    private String u_password;
    private Collection<? extends GrantedAuthority> u_authorities;
    private Map<String, Object> u_attributes;

    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.u_no = id;
        this.u_email = email;
        this.u_password = password;
        this.u_authorities = authorities;
    }

    public static UserPrincipal create(UserDto user) {

        log.info("create[] : xx" );
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getU_role()));
        return new UserPrincipal(
                user.getU_no(),
                user.getU_email(),
                user.getU_password(),
                authorities
        );
    }

    public static UserPrincipal create(UserDto user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.u_attributes = attributes;
    }
    
    public Long getU_no() {
        return u_no;
    }

    public String getU_email() {
        return u_email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return u_attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return u_authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(u_no);
    }

    @Override
    public String getPassword() {
        return u_password;
    }

    @Override
    public String getUsername() {
        return u_email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
