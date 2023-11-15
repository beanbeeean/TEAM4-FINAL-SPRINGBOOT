package com.office.libooksserver.user.controller;

import com.office.libooksserver.common.s3.S3ServiceImpl;
import com.office.libooksserver.common.s3.S3Uploader;
import com.office.libooksserver.login.config.security.token.CurrentUser;
import com.office.libooksserver.login.config.security.token.UserPrincipal;
import com.office.libooksserver.user.dto.UserDto;
import com.office.libooksserver.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    S3ServiceImpl s3ServiceImpl;

    private final S3Uploader s3Uploader;

    @GetMapping("/myPage")
    public ResponseEntity<?> myPage(@Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal) {

        return ResponseEntity.ok(userService.myPage(userPrincipal.getU_email()));
    }

    @PostMapping("/myReadReservation")
    public ResponseEntity<?> myReadReservation(@RequestBody Map<String, String> time, @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal) {


        System.out.println("myReadReservation : ");
        System.out.println("time : "+ time.get("startDate"));
        System.out.println("time : "+ time.get("endDate"));


        return ResponseEntity.ok(userService.myReadReservation(time.get("startDate"), time.get("endDate"), userPrincipal.getU_email()));

    }

    @PostMapping("/myStudyReservation")
    public ResponseEntity<?> myStudyReservation(@RequestBody Map<String, String> time, @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal) {

        System.out.println("myStudyReservation : ");
        System.out.println("time : "+ time.get("startDate"));
        System.out.println("time : "+ time.get("endDate"));

        return ResponseEntity.ok(userService.myStudyReservation(time.get("startDate"), time.get("endDate"), userPrincipal.getU_email()));

    }

    @PostMapping("/userUpdate")
    public ResponseEntity<?> userUpdate(@RequestBody UserDto userDto) {

        System.out.println("userDto: " + userDto);
        userService.userUpdate(userDto);

        return ResponseEntity.ok(userService.myPage(userDto.getU_email()));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal, @RequestParam(value="file", required=false) MultipartFile file) throws IOException {

        System.out.println("file: " + file);
        String url = s3ServiceImpl.createTeam(userPrincipal.getU_email(), file);
        userService.imgUpdate(userPrincipal.getU_email(), url);

        return new ResponseEntity(null, HttpStatus.OK);
    }

}
