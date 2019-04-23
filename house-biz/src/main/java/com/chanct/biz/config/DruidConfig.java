package com.chanct.biz.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;

@Configuration
public class DruidConfig {
	
	//开启druid
	@ConfigurationProperties(prefix="spring.druid")
	@Bean(initMethod="init",destroyMethod="close")
	public DruidDataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
		return dataSource;
	}
	
	@Bean
	public Filter statFilter() {
		StatFilter statFilter = new StatFilter();
		/*设置多上时间的sql属于慢sql 毫秒*/
		statFilter.setSlowSqlMillis(1);
		/*是否要将日志打印出来*/
		statFilter.setLogSlowSql(true);
		/*是否要将日志合并起来*/
		statFilter.setMergeSql(true);
		return statFilter;
	}
	
	// @WebServlet(urlPatterns="/druid/*") 和这个一样都能开启
//	@Bean //开启servlet监听   提供连接池监听页面 http://localhost:8090/druid/index.html
//	public ServletRegistrationBean servletRegistrationBean() {
//		return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
//	}
	
	public static void main(String[] args) throws ParseException {
		String date = "2018-06-20 12:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 24*60*60*1000;
		long now = sdf.parse(date).getTime();
		for(int i=0;i<60;i++) {
			System.out.println(i+" :" + sdf.format(new Date(now-i*day)) + " : " + sdf.parse(sdf.format(new Date(now-i*day))).getTime() );
		}
		long etime = now - 30*day;
		System.out.println(etime + ":" + sdf.format(new Date(etime)));
		System.out.println(etime + ":" + sdf.format(new Date(etime)));
		System.out.println(etime + ":" + sdf.format(new Date(etime-30*day)));
		
	}
	
}
