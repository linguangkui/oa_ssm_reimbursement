package com.web.oa.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.oa.pojo.ActiveUser;

@Controller
public class IndexController {
	@RequestMapping("/index")
	public String main(ModelMap model) {
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("activeUser", activeUser);
		return "index";
	}
}
