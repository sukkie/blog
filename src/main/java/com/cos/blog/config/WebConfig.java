package com.cos.blog.config;

import com.cos.blog.interceptor.ExecuteTimerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//	@Autowired
//	ExecuteTimerInterceptor executeTimerInterceptor;
	@Bean
	public ExecuteTimerInterceptor getExecuteTimerInterceptor() {
		return new ExecuteTimerInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry){

		registry.addInterceptor(getExecuteTimerInterceptor()).addPathPatterns("/**");
	}
}
