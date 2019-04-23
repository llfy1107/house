package com.chanct.web.interceptor;

import com.chanct.common.model.User;

public class UserContext {
	//根据线程获取对象  不同的线程获取不同的对象
	private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();
	
	public static void setUser(User user) {
		USER_HOLDER.set(user);
	}
	
	public static void remove() {
		USER_HOLDER.remove();
	}
	
	public static User getUser() {
		return USER_HOLDER.get();
	}
}
