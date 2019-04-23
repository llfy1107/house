package com.chanct.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chanct.common.constants.CommonConstants;
import com.chanct.common.model.User;

/**
 * 拦截器  HandlerInterceptor 是spring提供的拦截器接口
 * @author Administrator
 *
 */
@Component
public class AutoInterceptor implements HandlerInterceptor{

	//controller 执行之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String reqUri = request.getRequestURI();//获取uri
		//判定是是否访问 静态资源和 错误页面
		if (reqUri.startsWith("/static") || reqUri.startsWith("/error")) {
			return true;
		}
		HttpSession session = request.getSession(true);
		User user = (User)session.getAttribute(CommonConstants.USER_ATTRIBUTE);
		
		return false;
	}

	//controller 执行之后
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	//页面渲染之后执行
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
