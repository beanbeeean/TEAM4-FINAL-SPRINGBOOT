package com.office.libooksserver.user.controller;

import com.office.libooksserver.login.service.user.UserDto;
import com.office.libooksserver.user.dto.BookDto;
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

//    @GetMapping({"","/"})
//    public Map<String, Object> home() {
//        log.info("[BookController] home()");
//
////        bookService.insertBooks("ItemNewAll");
////        bookService.insertBooks("ItemNewSpecial");
////        bookService.insertBooks("Bestseller");
//        return bookService.showBooks();
//    }

    @GetMapping("/home")
    @ResponseBody
    public Map<String, Object> home(@RequestParam(name = "category", required = false) String category,
                                    @RequestParam(name = "keyword", required = false) String keyword ) {

        log.info("[BookController] home()");
        log.info("category : " + category);
        log.info("keyword : " + keyword);

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

    @PostMapping("/checkout/{id}")
    public Object checkoutBook(@PathVariable int id) {
        log.info("[BookController] checkoutBook");

        int result = bookService.checkoutBook(id);

        return result;
    }

//    @GetMapping("/search{keyword}")
//    @ResponseBody
//    public Map<String, Object>  searchBooks(@PathVariable String keyword){
//        log.info("[BookController] searchBooks()");
//        log.info("keyword: "+keyword);
//
//        Map<String, Object> resultMap = bookService.searchBooks(keyword);
//
//        return resultMap;
//    }

}
