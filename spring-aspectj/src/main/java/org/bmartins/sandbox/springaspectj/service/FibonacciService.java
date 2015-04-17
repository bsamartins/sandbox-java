package org.bmartins.sandbox.springaspectj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FibonacciService {
	
	private static final Logger LOG = LoggerFactory.getLogger(FibonacciService.class);
	
	public int getFibonnaci(int number) {
		return fibonacciRecursive(number);
	}
	
	private int fibonacciRecursive(int number) {
		if(number == 1 || number == 2) {
			return 1;
		} else {
			return fibonacciRecursive(number - 1) + fibonacciRecursive(number - 2);
		}		
	}
	
}
