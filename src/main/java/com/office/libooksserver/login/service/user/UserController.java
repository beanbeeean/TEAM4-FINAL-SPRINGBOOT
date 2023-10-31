package com.office.libooksserver.login.service.user;

import com.office.libooksserver.login.config.security.token.CurrentUser;
import com.office.libooksserver.login.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/myPage")
    public ResponseEntity<?> myPage(@Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal) {

        System.out.println("userService.myPage(email) : " + userService.myPage(userPrincipal.getU_email()));

        return ResponseEntity.ok(userService.myPage(userPrincipal.getU_email()));
    }

    @GetMapping("/userUpdate")
    public ResponseEntity<?> userUpdate() {

        //System.out.println("userUpdate : " + userPrincipal);
        //System.out.println("test : " + test);

        return ResponseEntity.ok("asd");
    }

}
