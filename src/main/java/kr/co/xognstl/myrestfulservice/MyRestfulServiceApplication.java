package kr.co.xognstl.myrestfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class MyRestfulServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRestfulServiceApplication.class, args);

//		ApplicationContext ac = SpringApplication.run(MyRestfulServiceApplication.class, args);
//
//		// 스프링에 등록된 빈 조회
//		String[] allBeanNames = ac.getBeanDefinitionNames();
//		for (String beanName : allBeanNames) {
//			System.out.println("beanName = " + beanName);
//		}
	}

	// 메세지 소스라는 빈을 가져올 것이다.
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);	// Default 값
		return localeResolver;
	}

}
