package com.office.libooksserver.admin.controller;

import com.office.libooksserver.admin.service.AdminBookService;
import com.office.libooksserver.user.dto.BookDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/admin/management")
public class AdminBookController {

    @Autowired
    AdminBookService adminBookService;

    @GetMapping("/bookManagement")
    @ResponseBody
    public Map<String, Object> home(@RequestParam(name = "category", required = false) String category,
                                    @RequestParam(name = "keyword", required = false) String keyword ) {

        log.info("[AdminBookController] home()");
        log.info("category : " + category);
        log.info("keyword : " + keyword);

        return adminBookService.showBooks(category, keyword);
    }

    @GetMapping("/checkout_book_user_list{bNo}")
    @ResponseBody
    public Map<String, Object> showChkUserList(@PathVariable int bNo) {
        log.info("[AdminBookController] showChkUserList()");

        return adminBookService.showChkUserList(bNo);
    }

    @GetMapping("/change_book_state")
    @ResponseBody
    public Object changeBookInfo(@RequestParam(name = "no", required = false) int no,
                                 @RequestParam(name = "stock", required = false) int stock,
                                    @RequestParam(name = "state", required = false) int state ) {

        log.info("[AdminBookController] changeBookInfo()");
        log.info("no : " + no);
        log.info("stock : " + stock);
        log.info("state : " + state);

        return adminBookService.changeBookInfo(no, stock, state);
    }

    @GetMapping("/return_book")
    @ResponseBody
    public Object changeChkBookState(@RequestParam(name = "b_no", required = false) int b_no,
                                     @RequestParam(name = "chk_b_no", required = false) int chk_b_no) {

        log.info("[AdminBookController] changeChkBookState()");
        log.info("b_no : " + b_no);
        log.info("chk_b_no : " + chk_b_no);

        return adminBookService.changeChkBookState(b_no, chk_b_no);
    }
}

