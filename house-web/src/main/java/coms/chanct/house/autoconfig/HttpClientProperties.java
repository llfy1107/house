package coms.chanct.house.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * 这个注解用来提供给用户配置的 在application.properties文件配置
 * prefix 代表配置文件中获的前缀
 */
@ConfigurationProperties(prefix="spring.httpclient")
public class HttpClientProperties {
	
	//连接超时时间 ms
	private Integer connectTimeOut = 1000;
	
	private Integer socketTimeOut = 10000;
	

	private String agent = "agent";
	//每一个ip的最大链接数
	private Integer maxConnPerRoute = 10;
	//总的连接数
	private Integer maxConnTotal = 50;
	public Integer getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(Integer connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public Integer getSocketTimeOut() {
		return socketTimeOut;
	}
	public void setSocketTimeOut(Integer socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Integer getMaxConnPerRoute() {
		return maxConnPerRoute;
	}
	public void setMaxConnPerRoute(Integer maxConnPerRoute) {
		this.maxConnPerRoute = maxConnPerRoute;
	}
	public Integer getMaxConnTotal() {
		return maxConnTotal;
	}
	public void setMaxConnTotal(Integer maxConnTotal) {
		this.maxConnTotal = maxConnTotal;
	}
	
	
}
