package org.bmartins.sandbox.springwebappxmless.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {
	
	@RequestMapping(value={"/index.htm", "/"})
	@ResponseBody
	public String index() {
		return "<html><body><p>Welcome</p></body></html>";
	}
	
}
