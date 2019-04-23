package com.chanct.web.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration 
public class FilterBeanConfig {

//	@Bean  注入一个过滤器
	public FilterRegistrationBean logFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new LogFilter());
		List<String> list = new ArrayList<String>();
		list.add("*");
		filterRegistrationBean.setUrlPatterns(list);
		return filterRegistrationBean;
	}
	
	
}
