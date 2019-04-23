package com.chanct.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
/*
 * urlPatterns 拦截路径
 */
@WebFilter(urlPatterns= {"*"}) //将过滤器注册成bean
@Order(1)//执行顺序  值越小越先执行
public class LogFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(LogFilter.class);
	
	@Override
	//容器启动时候执行
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	//方法拦截的时候执行
	/*
	 * 日志级别:
	 * error 40 
	 * warn 30 
	 * info 20 
	 * debug 10 
	 * trace 0
	 * 
	 * /favicon.ico  首次访问会有这个请求  游览器自动发送   获取这个文件最为浏览器标签图片
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
//		System.out.println(req.getMethod()); //GET POST
//		System.out.println(req.getRequestURI()); // /sss
//		System.out.println(req.getRequestURL()); // http://localhost:8080/sss
//		System.out.println(req.getCharacterEncoding()); //UTF-8
//		System.out.println(req.getContentType()); 
//		System.out.println(req.getContextPath());
//		System.out.println(req.getLocalAddr()); // 0:0:0:0:0:0:0:1
//		System.out.println(req.getLocalName()); // 0:0:0:0:0:0:0:1
//		System.out.println(req.getPathTranslated());
//		System.out.println(req.getProtocol()); // HTTP/1.1
//		System.out.println(req.getQueryString());
//		System.out.println(req.getRemoteAddr()); //0:0:0:0:0:0:0:1
//		System.out.println(req.getRemoteHost()); //0:0:0:0:0:0:0:1
//		System.out.println(req.getRemoteUser());
//		System.out.println(req.getServerName()); //localhost
//		System.out.println(req.getServletPath()); // /sss
//		logger.info("{} {} {}",req.getMethod(),req.getRequestURI(),req.getLocalAddr());
//		System.out.println(req.getSession().getId()); //session id
		chain.doFilter(request, response); 
	}

	@Override
	//容器销毁的时候执行
	public void destroy() {
	}

}
