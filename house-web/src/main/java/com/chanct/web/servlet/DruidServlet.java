package com.chanct.web.servlet;

import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

@WebServlet(urlPatterns="/druid/*")
public class DruidServlet extends StatViewServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6456064341813398110L;

}
