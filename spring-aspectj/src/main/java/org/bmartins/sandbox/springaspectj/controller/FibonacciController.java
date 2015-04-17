package org.bmartins.sandbox.springaspectj.controller;

import org.bmartins.sandbox.springaspectj.service.FibonacciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FibonacciController {
	
	@Autowired
	private FibonacciService fibonacciService;
	
	@RequestMapping(value="/fibonacci")	
	@ResponseBody
	public int index(@RequestParam("number") Integer number) {		
		return fibonacciService.getFibonnaci(number);
	}
	
}
