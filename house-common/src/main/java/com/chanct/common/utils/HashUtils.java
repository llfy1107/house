package com.chanct.common.utils;

import java.nio.charset.Charset;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class HashUtils {
	//创建支持MD5函数的function  google 的 guava工具类
	private static final HashFunction FUNCTION = Hashing.md5(); 
	
	//编写一个盐, 将用户密码和一个字符串同时加密,防止暴力破解
	private static final String SALT = "com.chanct";
	
	//给密码md5加密
	public static String encryPasswd(String passwd) {
		HashCode hashCode = FUNCTION.hashString(passwd+SALT, Charset.forName("UTF-8"));
		return hashCode.toString();
	}
}
