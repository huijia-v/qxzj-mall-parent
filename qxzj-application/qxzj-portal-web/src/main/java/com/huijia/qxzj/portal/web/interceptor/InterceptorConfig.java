package com.huijia.qxzj.portal.web.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/22 10:27 下午
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        		registry.addInterceptor(authInterceptor())
//				.addPathPatterns("/**")
//				.excludePathPatterns("/user-member/register")
//				.excludePathPatterns("/user-member/login")
//				.excludePathPatterns("/jcptcha/**")
//                .excludePathPatterns("/code/**");
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
}
