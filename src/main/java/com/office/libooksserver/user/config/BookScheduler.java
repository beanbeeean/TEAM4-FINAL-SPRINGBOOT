package com.office.libooksserver.user.config;

import com.office.libooksserver.user.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class BookScheduler {

    @Autowired
    BookService bookService;

    @Scheduled(cron = "0 10 0 * * *")
    public void bookMethod() {

        bookService.insertBooks("ItemNewAll");
        bookService.insertBooks("ItemNewSpecial");
        bookService.insertBooks("Bestseller");
    }
}
