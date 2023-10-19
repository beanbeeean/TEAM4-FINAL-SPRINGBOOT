package com.office.libooksserver;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
