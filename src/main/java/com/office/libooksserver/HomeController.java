package com.office.libooksserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HomeController {

    @GetMapping("/")
    public String test() {
        return "First Controller";
    }

    @GetMapping("/showMe")
    public List<String> hello(){
        return Arrays.asList("첫번째");
    }
}