package com.office.libooksserver.login.service.token;

import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.login.domain.entity.user.Token;
import com.office.libooksserver.login.payload.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//현재 없어도 됌

@Log4j2
@RequiredArgsConstructor
@Service
public class TokenService {

    @Autowired
    TokenMapper tokenMapper;

    // 확인 필요
    public ResponseEntity<?> findByUserEmail(UserPrincipal userPrincipal){

        log.info("findByUserEmail[]");

        Token token = tokenMapper.findByUserEmail(userPrincipal.getEmail());

        if (token == null) {
            throw new UsernameNotFoundException("User not found with email : " + userPrincipal.getEmail());
        }

        ApiResponse apiResponse = ApiResponse.builder().check(true).information(token).build();
        return ResponseEntity.ok(apiResponse);
    }
}
