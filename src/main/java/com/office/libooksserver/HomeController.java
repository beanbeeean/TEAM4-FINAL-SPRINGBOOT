package com.office.libooksserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HomeController {

	@GetMapping("/test")
	public String test() {
		return "First Controller";
	}
	
	@GetMapping("/showMe")
	public List<String> hello(){
		return Arrays.asList("첫번째");
	}
}
