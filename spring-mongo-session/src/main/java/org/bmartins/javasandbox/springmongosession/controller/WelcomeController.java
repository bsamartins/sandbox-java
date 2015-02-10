package org.bmartins.javasandbox.springmongosession.controller;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/")
	String welcome(HttpSession session) {
		Test testClass = new Test();
		testClass.setText("Hello World");
		
		session.setAttribute("bla", "bla");
		session.setAttribute("num", new BigDecimalWrapper(new BigDecimal("123456789.987654321")));
		session.setAttribute("test", testClass);				
		
		return "redirect:/get";
	}	

	@RequestMapping(value="/get", produces=MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	String get(HttpSession session) {
		BigDecimalWrapper num = (BigDecimalWrapper)session.getAttribute("num");
		
		StringBuilder result = new StringBuilder();
		result.append("<html><body>");
		result.append("bla: ").append(session.getAttribute("bla")).append("<br>");
		result.append("num: ").append(num.getNumber());
		result.append("</body></html>");
		return result.toString();
	}	

	public static class Test implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String text;
		
		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}		
	}
	
	public static class BigDecimalWrapper {
		
		private BigDecimal number;
		
		public BigDecimalWrapper (BigDecimal number) {
			this.number = number;
		}
		
		public BigDecimal getNumber(){
			return this.number;
		}
	}
}
