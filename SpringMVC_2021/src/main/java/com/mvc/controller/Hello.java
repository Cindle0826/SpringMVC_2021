package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/hi")
public class Hello {
	@RequestMapping(value = "/hello")
	@ResponseBody
	public String hello() {
		return "hello";
	}
}
