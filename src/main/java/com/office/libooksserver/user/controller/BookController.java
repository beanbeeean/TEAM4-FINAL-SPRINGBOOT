package com.office.libooksserver.user.controller;

import com.office.libooksserver.login.service.user.UserDto;
import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.dto.CheckoutBookDto;
import com.office.libooksserver.user.dto.CommunityDto;
import com.office.libooksserver.user.service.BookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/checkout_books")
public class BookController {

    @Autowired
    BookService bookService;


    @GetMapping("/home")
    @ResponseBody
    public Map<String, Object> home(@RequestParam(name = "category", required = false) String category,
                                    @RequestParam(name = "keyword", required = false) String keyword ) {

        log.info("[BookController] home()");
        log.info("category : " + category);
        log.info("keyword : " + keyword);

        bookService.getChkBookList();
        return bookService.showBooks(category, keyword);
    }

    @GetMapping("/{bNo}")
    @ResponseBody
    public BookDto getBookByBNo(@PathVariable String bNo) {

        log.info("bNo: "+bNo);

        BookDto bookDto = bookService.getBookDetail(Integer.parseInt(bNo));

        log.info(bookDto);
        return bookDto;
    }

//    @GetMapping("/checkout")
//    public int checkoutBook(@RequestParam(name = "id", required = false) int id,
//                               @RequestParam(name = "u_email", required = false) String u_email ) {
//        log.info("[BookController] checkoutBook");
//        log.info("id : " + id);
//        log.info("u_email : " + u_email);
//
//        int result = bookService.checkoutBook(id, u_email);
//
//        return result;
//    }

    @GetMapping("/checkout")
    public Map<String, Object> checkoutBook(@RequestParam(name = "id", required = false) int id,
                            @RequestParam(name = "u_email", required = false) String u_email ) {
        log.info("[BookController] checkoutBook");

        bookService.checkoutBook(id, u_email);
        return bookService.getChkBookList();
    }

    @GetMapping("/checkout_book_list")
    @ResponseBody
    public Map<String, Object> getChkBookList() {

        log.info("[BookController] getChkBookList()");

        return bookService.getChkBookList();
    }


}
