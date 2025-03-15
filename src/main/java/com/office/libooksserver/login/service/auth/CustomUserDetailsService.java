package com.office.libooksserver.login.service.auth;

import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.user.dto.UserDto;
import com.office.libooksserver.user.service.implement.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{

    // CustomUserDetailsService 클래스는 Spring Security의 인증 메커니즘에서 사용자의 상세 정보를 로드하는 역할을 합니다. 클래스의 주요 내용과 기능은 다음과 같습니다:

    @Autowired
    UserMapper userMapper;

    // 필드와 생성자:
    //UserRepository: 사용자와 관련된 데이터베이스 연산을 수행하기 위한 저장소입니다.
    //@RequiredArgsConstructor: Lombok을 사용하여 필요한 생성자를 자동 생성합니다.

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("loadUserByUsername[]" );

        UserDto userdto = Optional.ofNullable(userMapper.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다."));

        return UserPrincipal.create(userdto);
    }

    @Transactional
    public UserDetails loadUserById(String email) {

        log.info("loadUserById[]" );

        UserDto user = userMapper.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }

        return UserPrincipal.create(user);
    }

}
