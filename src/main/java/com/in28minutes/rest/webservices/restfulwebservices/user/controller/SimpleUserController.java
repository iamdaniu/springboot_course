package com.in28minutes.rest.webservices.restfulwebservices.user.controller;


import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.user.Post;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;
import com.in28minutes.rest.webservices.restfulwebservices.user.UserController;
import com.in28minutes.rest.webservices.restfulwebservices.user.UserLinkProvider;
import com.in28minutes.rest.webservices.restfulwebservices.user.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.user.service.SimpleUserService;

@RestController
public class SimpleUserController implements UserController {

	// @javax.annotation.Resource(name = SimpleUserService.ID)
	@Autowired
	private SimpleUserService userService;

	@Autowired
	private UserLinkProvider linkProvider;

	// retrieve all
	@Override
	@GetMapping("/users")
	public List<Resource<User>> getAllUsers() {
		List<Resource<User>> userResources = userService.findAll().stream()
		        .map(u -> new Resource<>(u, linkProvider.linkToSingleUser(u, this)))
		        .collect(Collectors.toList());
		return userResources;
	}

	// retrieve user (id)
	@Override
	@GetMapping("/users/{id}")
	public Resource<User> getById(@PathVariable("id") int id) {
		final User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return new Resource<User>(user, linkProvider.linkToAllUsers(this));
	}

	@Override
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User toAdd) {
		User added = userService.save(toAdd);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		        .path("/{id}").buildAndExpand(added.getId())
		        .toUri();

		return ResponseEntity.created(uri).body(added);
	}

	@Override
	@DeleteMapping("/users/{id}")
	public List<Resource<User>> deleteUser(@PathVariable("id") int id) {
		userService.deleteById(id);
		return getAllUsers();
	}

	@Override
	public List<Post> getPosts(int userId) {
		return Collections.emptyList();
	}

	@Override
	public ResponseEntity<Post> createPost(int userId, Post post) {
		// not implemented
		return null;
	}

}
