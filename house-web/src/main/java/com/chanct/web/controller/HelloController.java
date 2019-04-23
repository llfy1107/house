package com.chanct.web.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.chanct.biz.service.UserService;
import com.chanct.common.model.User;


@Controller
public class HelloController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/hello")
	public String hello(ModelMap modelMap) {
		User user = userService.getUsers().get(0);
		modelMap.put("user", user);
		if(user != null)
			throw new IllegalArgumentException();
		return "hello";
	}
	
	@GetMapping("/index")
	public String index(ModelMap map) {
		map.addAttribute("recomHouses", new ArrayList<Object>());
		return "homepage/index";
	}
	
}
