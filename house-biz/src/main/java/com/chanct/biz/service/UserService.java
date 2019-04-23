package com.chanct.biz.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chanct.biz.mapper.UserMapper;
import com.chanct.common.model.User;
import com.chanct.common.utils.BeanHelper;
import com.chanct.common.utils.HashUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;


@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private MailService mailService;
	
	//nginx 访问文件路径
	@Value("${file.perfix}")
	private String imgPerfix;
	
	public List<User> getUsers(){
		return userMapper.selectUsers();
	}
	
	/**
	 * 1,插入数据库, 非激活, 密码加盐md5, 保存头像到本地
	 * 2.生成key 绑定email
	 * 3.发送邮件给用户
	 */
	//在另外一个类中调用addAccount才会生效注解,本类中调用不会生效
	@Transactional(rollbackFor=Exception.class)
	public boolean addAccount(User account) {
		account.setPasswd(HashUtils.encryPasswd(account.getPasswd()));//将用户的密码加盐加密
		//获取图像保存本地的路径列表, 这里使用的new ArrayList 的可变参数列表方法 
		List<String> imgList = fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
		if (!imgList.isEmpty()) {
			account.setAvatar(imgList.get(0));
		}
		BeanHelper.setDefaultProp(account, User.class);//给对象指定默认值
		BeanHelper.onInsert(account);//修改createTime
		account.setEnable(0);
		userMapper.insert(account);
		mailService.registerNotify(account.getEmail());
		return true;
	}

	//验证key 是否存在
	public boolean enable(String key) { 
		return mailService.enable(key);
	}

	//登陆验证 用户名密码验证
	public User auth(String username, String passwd) {
		User user = new User();
		user.setEmail(username);
		user.setPasswd(HashUtils.encryPasswd(passwd));
		user.setEnable(1);
		List<User> list = getUserQuery(user);
		if (!list.isEmpty()) {
			return list.get(0);
		}else {
			return null;
		}
	}

	public List<User> getUserQuery(User user) {
		List<User> list = userMapper.selectUsersByQuery(user);
		list.forEach(u ->{
			u.setAvatar(imgPerfix + u.getAvatar());//补全用户获取头像访问路径
		});
		return list;
	}
	
	
	
}
