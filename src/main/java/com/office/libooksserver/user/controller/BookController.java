package com.office.libooksserver.user.controller;

import com.office.libooksserver.user.dto.BookDto;
import com.office.libooksserver.user.service.BookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/checkout_books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping({"","/"})
    public Map<String, Object> home() {

        log.info("[BookController] home()");
        bookService.insertBooks("ItemNewAll");
        bookService.insertBooks("ItemNewSpecial");
        bookService.insertBooks("Bestseller");
        return bookService.showBooks();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BookDto getBookByBNo(@PathVariable String id) {

        log.info("id: "+id);

        BookDto bookDto = bookService.getBookDetail(Integer.parseInt(id));

        log.info(bookDto);
        return bookDto;
    }

    @PostMapping("/checkout/{id}")
    public Object checkoutBook(@PathVariable int id) {
        log.info("[BookController] checkoutBook");

        int result = bookService.checkoutBook(id);

        return result;
    }

}
