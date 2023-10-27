package com.office.libooksserver.login.service.user;

import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.login.payload.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    //private final UserRepository userRepository;

    //@RequiredArgsConstructor: Lombok 라이브러리를 사용하여 필수 생성자를 자동으로 생성합니다. 이 생성자는 final로 선언된 필드에 대한 생성자를 자동으로 제공합니다.

//    readByUser(UserPrincipal userPrincipal): 사용자의 정보를 읽어오는 메서드입니다.
//    UserPrincipal 객체에서 사용자 ID를 가져옵니다.
//    userRepository를 사용하여 해당 ID에 대한 사용자 정보를 검색합니다.
//            DefaultAssert.isOptionalPresent(user): user 객체가 존재하는지 확인합니다. 없을 경우 예외를 발생시킵니다.
//    ApiResponse 객체를 생성하여 결과를 반환합니다. 여기서는 check 값을 true로 설정하고, 검색된 사용자 정보를 information 필드에 넣습니다.
//    최종적으로 ResponseEntity를 사용하여 API 응답을 구성하고 반환합니다.

    public ResponseEntity<?> readByUser(UserPrincipal userPrincipal){

        log.info("readByUser[]" );

        UserDto user = userMapper.findByid(userPrincipal.getId());

        if (user == null) {
            throw new UsernameNotFoundException("User not found with id : " + userPrincipal.getId());
        }

        ApiResponse apiResponse = ApiResponse.builder().check(true).information(user).build();
        return ResponseEntity.ok(apiResponse);
    }
}
