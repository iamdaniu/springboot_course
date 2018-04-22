package com.in28minutes.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {
	// Unterscheidung durch URL
	@GetMapping("v1/person")
	public PersonV1 uriMappedPersonV1() {
		return new PersonV1("Name");
	}

	@GetMapping("v2/person")
	public PersonV2 uriMappedPersonV2() {
		return new PersonV2(new Name("First", "Last"));
	}

	// Angefordert durch URL-Parameter
	@GetMapping(value = "person/param", params = "version=1")
	public PersonV1 paramMappedPersonV1() {
		return new PersonV1("Name");
	}

	@GetMapping(value = "person/param", params = "version=2")
	public PersonV2 paramMappedPersonV2() {
		return new PersonV2(new Name("First", "Last"));
	}

	// Angefordert durch Header-Parameter (Caching nicht moeglich)
	@GetMapping(value = "person/header", headers = "X-API-VERSION=1")
	public PersonV1 headerMappedPersonV1() {
		return new PersonV1("Name");
	}

	@GetMapping(value = "person/header", headers = "X-API-VERSION=2")
	public PersonV2 headerMappedPersonV2() {
		return new PersonV2(new Name("First", "Last"));
	}

	// Angefordert durch Header-Accept-Parameter (Caching nicht moeglich)
	@GetMapping(value = "person/produces", produces = "application/joern-v1+json")
	public PersonV1 producesMappedPersonV1() {
		return new PersonV1("Name");
	}

	@GetMapping(value = "person/produces", produces = "application/joern-v2+json")
	public PersonV2 producesMappedPersonV2() {
		return new PersonV2(new Name("First", "Last"));
	}
}
