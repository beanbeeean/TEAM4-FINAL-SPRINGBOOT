package com.office.libooksserver.common.controller;

import com.office.libooksserver.common.dto.BookDto;
import com.office.libooksserver.common.service.BookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Log4j2
@RequestMapping("/checkout_books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping({"","/"})
    public Map<String, Object> home() {

        log.info("[BookController] home()");
        bookService.insertBooks();
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

}
