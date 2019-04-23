package com.chanct.web.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chanct.common.model.User;

@Component
public class AuthActionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = UserContext.getUser();
		if (user == null) { //如果user为null 代表未登陆 
			String msg = URLEncoder.encode("请先登陆", "UTF-8");//提示语
			//原本的访问路径储存
			String target = URLEncoder.encode(request.getRequestURL().toString(), "utf-8");
			if ("GET".equalsIgnoreCase(request.getMethod())) {
				response.sendRedirect("/accounts/signin?errorMsg=" + msg + 
						"&target=" + target);
				
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
