package com.mvc.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/hello")
public class Hi {
	@RequestMapping(value = "/{welcome}",method = RequestMethod.GET)
	@ResponseBody
	public String greet(@PathVariable("welcome") String welcome, @RequestParam("name") String name) {
		/*
		 * 解決中文問題
		 * 預設的編碼是: ISO-8859-1
		 * 改變編碼: UTF-8
		 */
		try {
			welcome = new String(welcome.getBytes("ISO-8859-1"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return welcome + " " + name + " !";
	}
	
	@GetMapping(value = "/sayhi")
	public ModelAndView sayhi() {
		ModelAndView mav = new ModelAndView();
//		mav.setViewName("/WEB-INF/view/sayhi.jsp");
		mav.setViewName("sayhi"); //於 springmvc-servlet.xml 中有定義 ViewResolver 標籤的寫法
		mav.addObject("username","John");
		return mav;
	}
	
	@GetMapping(value = "/sayhi2")
	public String sayhi2(Model Model) {
		Model.addAttribute("username","Mary");
		return "sayhi";
	}
}
