package br.com.apiattornatus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.apiattornatus.interceptor.LoggerInterceptor;


@Configuration
public class LoggerConfig implements WebMvcConfigurer {

	// Logger para iterceptar requisições.
	@Autowired
	private LoggerInterceptor interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor).addPathPatterns("/**");
	}
}