package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	/**
	 * Gibt eine Bean mit einem statischen Filter zurueck (string3 nicht enthalten).
	 * 
	 * @return
	 */
	@GetMapping("/filtering")
	public StaticallyFilteredBean retrieveBean() {
		return new StaticallyFilteredBean("test1", "test1", "test1");
	}

	/**
	 * Gibt eine Liste von Beans mit statischem Filter zurueck.
	 * 
	 * @return
	 */
	@GetMapping("/filtering-list")
	public List<StaticallyFilteredBean> retrieveBeans() {
		return Arrays.asList(new StaticallyFilteredBean("test11", "test12", "test13"),
		        new StaticallyFilteredBean("test21", "test22", "test23"),
		        new StaticallyFilteredBean("test31", "test32", "test33"));
	}

	/**
	 * Gibt eine Bean mit einem statischen Filter zurueck (string3 nicht enthalten).
	 * 
	 * @return
	 */
	@GetMapping("/filtering-other")
	public OtherStaticallyFilteredBean retrieveOtherBean() {
		return new OtherStaticallyFilteredBean("test1", "test1", "test1");
	}

	/**
	 * Gibt eine Bean mit einem dynamischen Filter zurueck (string3 nicht
	 * enthalten). Filter mit {@link #filteredString3(Object)} wird zur Laufzeit
	 * erzeugt.
	 * 
	 * @return
	 */
	@GetMapping("/dynamic_filtering")
	public MappingJacksonValue retrieveFilteredBean() {
		final DynamicallyFilteredBean dynamicallyFilteredBean = new DynamicallyFilteredBean("test1", "test1", "test1");

		return filteredString3(dynamicallyFilteredBean);
	}

	/**
	 * Gibt eine Liste dynamisch gefilterter Beans zurueck.
	 * 
	 * @return
	 */
	@GetMapping("/dynamic_filtering-list")
	public MappingJacksonValue retrieveFilteredBeans() {
		List<StaticallyFilteredBean> beans = Arrays.asList(new StaticallyFilteredBean("test11", "test12", "test13"),
		        new StaticallyFilteredBean("test21", "test22", "test23"),
		        new StaticallyFilteredBean("test31", "test32", "test33"));

		return filteredString3(beans);
	}

	/**
	 * Erzeugt ein gefiltertes Mapping des gegebenen Objekts, das die
	 * string3-Property nicht enthaelt.
	 * 
	 * @param o
	 * @return
	 */
	private static MappingJacksonValue filteredString3(Object o) {
		MappingJacksonValue mapping = new MappingJacksonValue(o);
		mapping.setFilters(DYNAMIC_FILTER_PROVIDER);
		return mapping;
	}

	private static final SimpleBeanPropertyFilter LEAVE_FIELDS_ONE_AND_TWO = SimpleBeanPropertyFilter
	        .filterOutAllExcept("string1", "string2");
	private static FilterProvider DYNAMIC_FILTER_PROVIDER = new SimpleFilterProvider()
	        .addFilter(DynamicallyFilteredBean.FILTER_STRING_3, LEAVE_FIELDS_ONE_AND_TWO);
}
