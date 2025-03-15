package com.office.libooksserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
@PropertySource(value = { "classpath:database/database.properties" })
@PropertySource(value = { "classpath:oauth2/oauth2.properties" })
@PropertySource(value = { "classpath:swagger/springdoc.properties" })
public class LibooksServerApplication {

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

//    @PostConstruct
//    public void started() {
//        // timezone UTC 셋팅
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//    }
    public static void main(String[] args) {
        SpringApplication.run(LibooksServerApplication.class, args);
    }

}
