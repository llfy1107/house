package com.chanct.biz.service;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.chanct.biz.mapper.UserMapper;
import com.chanct.common.model.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

@Service
public class MailService {
	
	//用guava创建一个本地缓存
	/**
	 * cache.getcause() 获取当前key清除的原因
	 *   由于"延迟删除"的实现, 对于自动清除的key 总是等待下次的读写操作来触发, 这样避免启动一个线程去维护这个缓存
	 *   有的key 虽然到时间了 但是没有执行清除代码,知道下一次的读写才执行,但是此时是找不到该key
	 * EXPLICIT 手动清除
	 * EXPIRED  定时自动清除
	 */
		private final Cache<String,String> registerCache = CacheBuilder.newBuilder()//构建一个新的缓存
				.maximumSize(100)//最多储存100个
				.expireAfterAccess(15, TimeUnit.MINUTES)//过期时间15分钟  没有被读写访问   expireAfterWrite (没有被写访问)
				.removalListener(new RemovalListener<String,String>(){ //过期的时候触发操作
					@Override
					public void onRemoval(RemovalNotification<String, String> notification) {
						//这里控制删除操作只有在非手动删除key的情况才执行
						//notification.getCause() 返回一个枚举类型 也就是  RemovalCause.EXPLICIT
						if(!notification.getCause().equals(RemovalCause.EXPLICIT))
							userMapper.delete(notification.getValue());
					}
				}).build();//生成
	@Autowired
	private UserMapper userMapper;
		
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Value("${domain.name}")
	private String domainName;
	
	public void sendMail(String title,String url, String email ) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);//本机配置的email
		message.setTo(email);//发送至的email
		message.setText(url);//email内容
		mailSender.send(message);
	}
	
	/**
	 * 1.缓存key-email的关系
	 * 2.借助spring email 发送邮件
	 * 3.借助异步框架进行异步操作
	 * @param email
	 * @throws InterruptedException 
	 */
	//注意 这个异步方法不能和调用她的方法在一个类中  否则不会生成异步线程运行 (经过测试,没问题)
	@Async //spring的异步框架注解  spring会调用一个线程池 将任务扔进去
	public void registerNotify(String email) {
		//随机生成10位的字符串
		String randomKey = RandomStringUtils.randomAlphabetic(10);
		//将key 和 eamil 绑定
		registerCache.put(randomKey, email);
		String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
		sendMail("房产平台激活邮件",url,email);
	}

	//验证缓存中的key 是否存在, 如果存在找到对应Email激活, enable = 1
	public boolean enable(String key) {
		String email = registerCache.getIfPresent(key);
		if(StringUtils.isBlank(email)) {
			return false;
		}
		User updateUser = new User();
		updateUser.setEmail(email);
		updateUser.setEnable(1);
		userMapper.update(updateUser);
		registerCache.invalidate(key);//激活成功之后, 使之失效
		return true;
	}
}
