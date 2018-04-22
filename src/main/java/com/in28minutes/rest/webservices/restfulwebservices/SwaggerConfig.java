package com.in28minutes.rest.webservices.restfulwebservices;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final Contact DEFAULT_CONTACT = new Contact("Jörn Schumacher", "", "iamdaniu@sourceforge.net");
	private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
	        "Training API", "An API designed while studying REST with Spring Boot", ApiInfo.DEFAULT.getVersion(),
	        ApiInfo.DEFAULT.getTermsOfServiceUrl(), DEFAULT_CONTACT,
	        ApiInfo.DEFAULT.getLicense(), ApiInfo.DEFAULT.getLicenseUrl(), Collections.emptyList());
	private static final Set<String> DEFAULT_FORMATS = new HashSet<>(
	        Arrays.asList("application/json", "application/xml"));

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .apiInfo(DEFAULT_API_INFO)
		        .consumes(DEFAULT_FORMATS)
		        .produces(DEFAULT_FORMATS);
	}
}
