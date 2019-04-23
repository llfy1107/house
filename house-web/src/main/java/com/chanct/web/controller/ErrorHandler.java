package com.chanct.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice//conroller 辅助类 拦截异常
public class ErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
	
	@ExceptionHandler(value= {Exception.class,RuntimeException.class})
	public String error500(HttpServletRequest req,Exception e) {
		logger.error(e.getMessage(),e);
		logger.error(req.getRequestURL()+"encounter 500");
		return "error/500";
	}
	
}
