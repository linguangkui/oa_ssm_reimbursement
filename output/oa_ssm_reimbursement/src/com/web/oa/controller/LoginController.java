package com.web.oa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public String login(HttpServletRequest request,Model model){
		String exceptionName = (String) request.getAttribute("shiroLoginFailure");
		if (exceptionName != null) {
			if (UnknownAccountException.class.getName().equals(exceptionName)) {
				model.addAttribute("errorMsg", "用户账号不存在");
			} else if (IncorrectCredentialsException.class.getName().equals(exceptionName)) {
				model.addAttribute("errorMsg", "密码不正确");
			} else if("randomcodeError".equals(exceptionName)) {
				model.addAttribute("codeErrorMsg", "验证码不正确");
			}
		}
		return "login";
	}
	
	@RequestMapping("/check")
	public void check(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 	ICaptcha captcha = CaptchaUtil.createCircleCaptcha(190, 80, 4, 4);
	        String randomCode = captcha.getCode();
	        captcha.write(response.getOutputStream());
	        HttpSession session = request.getSession();
	        session.setAttribute("randomCode",randomCode);
	        //Servlet的OutputStream记得自行关闭哦！
	        response.getOutputStream().close();
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		//清空Session
		session.invalidate();
		return "redirect:login.jsp";
	}
}
