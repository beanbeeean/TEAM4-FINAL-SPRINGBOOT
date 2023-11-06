package com.office.libooksserver.admin.service;

import com.office.libooksserver.admin.service.implement.IAdminDaoMapper;
import com.office.libooksserver.login.service.user.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class AdminService {

    @Autowired
    IAdminDaoMapper iAdminDaoMapper;

    public Map<String, Object> showUsers(String keyword) {
        log.info("[AdminUserService] showUsers()");
        log.info("keyword : " + keyword);

        Map<String, Object> map = new HashMap<>();
        List<UserDto> dtos = iAdminDaoMapper.selectUsersByFiltering(keyword);
        log.info("dtos" + dtos.size());

        map.put("dtos",dtos);

        return map;
    }

    public Map<String, Object> showAdmins(String keyword) {
        log.info("[AdminUserService] showAdmins()");
        log.info("keyword : " + keyword);

        Map<String, Object> map = new HashMap<>();
        List<UserDto> dtos = iAdminDaoMapper.selectAdminsByFiltering(keyword);
        log.info("dtos" + dtos.size());

        map.put("dtos",dtos);

        return map;
    }

    public Object changeUserState(int no) {
        log.info("[AdminUserService] changeUserState()");

        int result = iAdminDaoMapper.updateUserState(no);
        log.info("result : "+ result );

        return result;
    }

}
