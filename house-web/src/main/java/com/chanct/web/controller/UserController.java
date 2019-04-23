 package com.chanct.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chanct.biz.service.UserService;
import com.chanct.common.constants.CommonConstants;
import com.chanct.common.model.User;
import com.chanct.common.result.ResultMsg;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user")
	@ResponseBody
	public List<User> getUsers(){
		 return userService.getUsers(); 
	}
	
	/**
	 * 注册提交 : 1 注册验证, 2 发送邮件, 3验证失败重定向到注册页面
	 * 获取注册页面: 根据account对象为空 判断是注册页面获取还是注册页面提交  
	 * @param account
	 * @param map
	 * @return
	 */
	@RequestMapping("/accounts/register")
	public String accountsRegister(User account,ModelMap map) {
		System.out.println(account);
		if(account == null || account.getName() == null)
			return "/user/accounts/register";//注册页获取
		//用户验证
		ResultMsg resultMsg = UserHelper.validate(account);
		if(resultMsg.isSuccess() && userService.addAccount(account)) {
			map.put("email", account.getEmail());
			return "/user/accounts/registerSubmit";//提交数据验证无误并且插入数据成功,返回注册成功页面
		} else
			return "redirect:/accounts/register?"+resultMsg.asUrlParams(); //如果出现问题,重定向的注册页获取,并且传回错误结果
	}
	
	//注册之后的激活请求
	@RequestMapping("/accounts/verify")
	public String verify(String key) {
		boolean result = userService.enable(key);
		if(result) {//激活成功 跳转首页
			return "redirect:/index?"+ ResultMsg.successMsg("激活成功").asUrlParams();
		} else {// 激活失败, 返回注册页重新填写信息
			return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认连接是否过期").asUrlParams();
		}
	}
	
	//-------------------------登陆流程-------------------------------------
	
	/**
	 * 登陆接口   
	 * 1. 登录页的请求
	 * 2. 登陆提交的请求
	 */
	@RequestMapping("/accounts/signin")
	public String signin(HttpServletRequest req) {
		String username = req.getParameter("username");
		String passwd = req.getParameter("password");
		String target = req.getParameter("target"); //跳转的页面
		
		if (username == null || passwd == null) { //用来判断是否是登陆页请求
			req.setAttribute("target", target);
			return "/user/accounts/signin";
		}
		User user = userService.auth(username, passwd);
		if(user == null) {//验证不通过
			return "redirect:/accounts/signin?" + "target=" + target + "&username=" 
					+ username + "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
		}else {//验证通过
			HttpSession session = req.getSession(true);// true 强制创建
			session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
			session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
			return StringUtils.isNoneBlank(target)? "redirect:" + target : "redirect:/index";
		}
	}
	
	/**
	 * 登出操作
	 */
	@RequestMapping("/accounts/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		session.invalidate();
		return "redirect:/index";
	}
	
	
	
	
}
