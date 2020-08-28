package com.web.oa.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.web.oa.pojo.ActiveUser;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String mycode = req.getParameter("mycode");
		String randomCode = (String) session.getAttribute("randomCode");
		
		if (mycode != null && randomCode != null && !mycode.equals(randomCode)) {
			request.setAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "randomcodeError");
			return true;
		}
		return super.onAccessDenied(request, response);
	}
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		WebUtils.getAndClearSavedRequest(request);
		WebUtils.redirectToSavedRequest(request, response, "/index");
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		//获取session
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		HttpSession session = httpServletRequest.getSession();
		//保存用户信息
		session.setAttribute("user", activeUser);
		return false;
	}
}
