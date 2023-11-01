package com.office.libooksserver.admin.service;

import com.office.libooksserver.admin.service.implement.IAdminBookDaoMapper;
import com.office.libooksserver.user.dto.BookDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class AdminBookService {

    @Autowired
    IAdminBookDaoMapper iAdminBookDaoMapper;

    public Map<String, Object> showBooks(String category, String keyword) {
        log.info("[AdminBookService] showBooks()");
        log.info("category : " + category);
        log.info("keyword : " + keyword);

        Map<String, Object> map = new HashMap<>();
        List<BookDto> dtos = iAdminBookDaoMapper.selectBooksByFiltering(category, keyword);
        log.info("dtos" + dtos.size());

        map.put("dtos",dtos);

        return map;
    }

    public Map<String, Object> showChkUserList(int bNo) {
        log.info("[AdminBookService] showChkUserList()");

        Map<String, Object> map = new HashMap<>();
        List<BookDto> dtos = iAdminBookDaoMapper.selectByChkUserList(bNo);
        log.info("dtos" + dtos.size());

        map.put("dtos",dtos);

        return map;
    }

    public int changeBookInfo(int no, int stock, int state) {
        log.info("[AdminBookService] changeBookInfo()");

        int result = iAdminBookDaoMapper.updateBookInfo(no, stock, state);
        log.info("result : "+ result );

        return result;
    }

    public Object changeChkBookState(@PathVariable int no) {
        log.info("[AdminBookService] changeChkBookState()");
        log.info("no" + no);

        int result = iAdminBookDaoMapper.changeChkBookState(no);
        log.info("result : "+ result );

        return result;
    }
}
