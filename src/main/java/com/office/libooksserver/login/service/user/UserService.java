package com.office.libooksserver.login.service.user;

import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.login.payload.response.ApiResponse;
import com.office.libooksserver.reservation.readroom.ReadRoomDto;
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

    public UserDto myPage(String email){

        return userMapper.findByEmail(email);

    }

    public ReadRoomDto myReservation(String email){

        return userMapper.myReservation(email);

    }

    public void userUpdate(UserDto userDto) {

        userMapper.userUpdate(userDto);

    }

    public void imgUpdate(String email, String url) {

        userMapper.imgUpdate(email, url);

    }

    public ResponseEntity<?> readByUser(UserPrincipal userPrincipal){

        log.info("readByUser[]" );

        UserDto user = userMapper.findByid(userPrincipal.getU_no());

        if (user == null) {
            throw new UsernameNotFoundException("User not found with id : " + userPrincipal.getU_no());
        }

        ApiResponse apiResponse = ApiResponse.builder().check(true).information(user).build();
        return ResponseEntity.ok(apiResponse);
    }
}
