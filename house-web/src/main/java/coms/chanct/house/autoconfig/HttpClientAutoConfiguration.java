package coms.chanct.house.autoconfig;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//生命成javaconfig
//只有包含的类存在的时候才会加载这个javaconfig
@ConditionalOnClass({HttpClient.class})
//将该配置类作为一个bean引入进来
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration { 
	 
	private final HttpClientProperties properties;
	
	//该类的构造方法, 将上面引入的bean注入本类 
	public HttpClientAutoConfiguration(HttpClientProperties properties) {
		this.properties = properties;
	}
	
	/*
	 * httpclient bean 的定义 
	 */
	@Bean
	//在用户为定义的情况加才加载该bean
	@ConditionalOnMissingBean(HttpClient.class)
	public HttpClient httpClinent() { 
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(properties.getConnectTimeOut())
				.setSocketTimeout(properties.getSocketTimeOut()).build();//构建requestConfig
		HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
				.setUserAgent(properties.getAgent())
				.setMaxConnPerRoute(properties.getMaxConnPerRoute()) //ip最大连接
				.setConnectionReuseStrategy(new NoConnectionReuseStrategy()) //不允许重新连接
				.build();
		return client;
	}
}
