package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path = "/hello-world", produces = { "text/plain" })
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping(path = "/hello-world-internationalized", produces = { "text/plain" })
	public String helloWorldInternationalized() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}

	@GetMapping(path = "/hello-world-bean", produces = { "text/plain" })
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}

	@GetMapping(path = "/hello-world/path-variable/{name}", produces = { "text/plain" })
	public HelloWorldBean helloWorldBean(@PathVariable("name") String name) {
		return new HelloWorldBean(String.format("Hello, %s", name));
	}
}
