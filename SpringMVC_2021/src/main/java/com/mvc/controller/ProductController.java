package com.mvc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mvc.entity.products.Product;
import com.mvc.service.ProductService;

@Controller
@RequestMapping(value = "/product")
@SessionAttributes(value = {"groups","products","action"})
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = { "/", "/input" })
	public String index(Model model) {
		model.addAttribute("product", new Product());
		System.out.println(model.addAttribute("groups", productService.groups.values()));
		model.addAttribute("products", productService.query());
		model.addAttribute("action", "save");

		return "product";
	}

	@PostMapping(value = "/save")
	public String save(@Valid Product product, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("groups", productService.groups.values());
			model.addAttribute("products", productService.query());
			model.addAttribute("action", "save");
			return "product";
		}
		productService.save(product);
		return "redirect:/mvc/product/";
	}
}
