package com.example.backend.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins(
				"http://localhost:3000","https://k11a608.p.ssafy.io"
			)
			.allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
			.allowedHeaders("*")
			.exposedHeaders("Content-Type")
			.exposedHeaders("Authorization")
			.allowCredentials(true)
			.maxAge(3600);

		WebMvcConfigurer.super.addCorsMappings(registry);
	}
}
