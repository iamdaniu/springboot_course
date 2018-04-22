package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter(DynamicallyFilteredBean.FILTER_STRING_3)
public class DynamicallyFilteredBean {
	public static final String FILTER_STRING_3 = "filterField3";

	private String string1;
	private String string2;
	private String string3;

	public DynamicallyFilteredBean(String string, String string2, String string3) {
		this.string1 = string;
		this.string2 = string2;
		this.string3 = string3;
	}

	public String getString1() {
		return string1;
	}

	public void setString1(String string) {
		this.string1 = string;
	}

	public String getString2() {
		return string2;
	}

	public void setString2(String string2) {
		this.string2 = string2;
	}

	public String getString3() {
		return string3;
	}

	public void setString3(String string3) {
		this.string3 = string3;
	}
}
