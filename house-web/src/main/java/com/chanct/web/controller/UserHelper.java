package com.chanct.web.controller;

import org.apache.commons.lang3.StringUtils;

import com.chanct.common.model.User;
import com.chanct.common.result.ResultMsg;

public class UserHelper {
	public static ResultMsg validate(User account) {
		if(StringUtils.isBlank(account.getEmail()))//判断是否为 null "" " "
			return ResultMsg.errorMsg("email有误");
		if(StringUtils.isBlank(account.getName()))
			return ResultMsg.errorMsg("名字有误");
		if(StringUtils.isBlank(account.getPasswd()) || 
				StringUtils.isBlank(account.getConfirmPasswd()))
			return ResultMsg.errorMsg("密码或确认密码有误");
		if(! account.getPasswd().equals(account.getConfirmPasswd()))
			return ResultMsg.errorMsg("密码和确认密码不一致");
		if(account.getPasswd().length()<6)
			return ResultMsg.errorMsg("密码需要大于6位");
		return ResultMsg.successMsg("");
	}
}
