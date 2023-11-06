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

//    public Map<String, Object> showChkUserList(int bNo) {
//        log.info("[AdminBookService] showChkUserList()");
//
//        Map<String, Object> map = new HashMap<>();
//        List<BookDto> dtos = iAdminBookDaoMapper.selectByChkUserList(bNo);
//        log.info("dtos" + dtos.size());
//
//        map.put("dtos",dtos);
//
//        return map;
//    }

//    public int changeBookInfo(int no, int stock, int state) {
//        log.info("[AdminBookService] changeBookInfo()");
//
//        int result = iAdminBookDaoMapper.updateBookInfo(no, stock, state);
//        log.info("result : "+ result );
//
//        return result;
//    }

//    public Object changeChkBookState(int bNo, int chkBNo) {
//        log.info("[AdminBookService] changeChkBookState()");
//        log.info("bNo" + bNo);
//        log.info("chkBNo" + chkBNo);
//
//        int result = iAdminBookDaoMapper.changeChkBookState(chkBNo);
//        iAdminBookDaoMapper.increaseBookStock(bNo);
//        log.info("result : "+ result );
//
//        return result;
//    }
}
