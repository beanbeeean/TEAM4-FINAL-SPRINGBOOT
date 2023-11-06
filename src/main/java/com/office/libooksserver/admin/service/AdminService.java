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

    public Object changeUserState(int no) {
        log.info("[AdminUserService] changeUserState()");

        int result = iAdminDaoMapper.updateUserState(no);
        log.info("result : "+ result );

        return result;
    }

    public Map<String, Object> showMembers(String keyword) {
        log.info("[AdminUserService] showMembers()");
        log.info("keyword : " + keyword);

        Map<String, Object> map = new HashMap<>();
        List<UserDto> dtos = iAdminDaoMapper.selectMembersByFiltering(keyword);
        log.info("dtos" + dtos.size());

        map.put("dtos",dtos);

        return map;
    }
}
