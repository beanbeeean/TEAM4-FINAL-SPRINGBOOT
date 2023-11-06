package com.office.libooksserver.admin.controller;

import com.office.libooksserver.admin.service.AdminService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/admin/management")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/userManagement")
    @ResponseBody
    public Map<String, Object> userList(@RequestParam(name = "keyword", required = false) String keyword ) {

        log.info("[AdminUserController] userList()");
        log.info("keyword : " + keyword);

        return adminService.showUsers(keyword);
    }

    @GetMapping("/adminManagement")
    @ResponseBody
    public Map<String, Object> adminList(@RequestParam(name = "keyword", required = false) String keyword ) {

        log.info("[AdminUserController] adminList()");
        log.info("keyword : " + keyword);

        return adminService.showAdmins(keyword);
    }
    @GetMapping("/change_user_state")
    @ResponseBody
    public Object changeUserInfo(@RequestParam(name = "no", required = false) int no) {

        log.info("[AdminUserController] changeUserInfo()");
        log.info("no : " + no);

        return adminService.changeUserState(no);
    }

}

