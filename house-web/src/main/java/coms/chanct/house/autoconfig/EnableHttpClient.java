package coms.chanct.house.autoconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)//目标
@Retention(RetentionPolicy.RUNTIME)
@Import(HttpClientAutoConfiguration.class)
/*
 * 在该注解中引入某个 自动配置类  然后在springboot的启动程序中引入该注解,就会启动该自动配置类
 */
public @interface EnableHttpClient {

}
