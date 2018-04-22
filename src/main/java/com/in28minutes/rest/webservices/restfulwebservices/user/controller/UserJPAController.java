package com.in28minutes.rest.webservices.restfulwebservices.user.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
import com.in28minutes.rest.webservices.restfulwebservices.user.UserService;

@RestController
public class UserJPAController implements UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserLinkProvider linkProvider;

	// retrieve all
	@GetMapping("/jpa/users")
	public List<Resource<User>> getAllUsers() {
		List<Resource<User>> userResources = userService.findAll().stream()
		        .map(u -> new Resource<>(u, new Link[]{ linkProvider.linkToSingleUser(u, this),
		        		linkProvider.linkToUserPosts(u, this)
		        }))
		        .collect(Collectors.toList());
		return userResources;
	}

	// retrieve user (id)
	@GetMapping("/jpa/users/{id}")
	public Resource<User> getById(@PathVariable("id") int id) {
		final User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return new Resource<User>(user, linkProvider.linkToAllUsers(this));
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User toAdd) {
		User added = userService.save(toAdd);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		        .path("/{id}").buildAndExpand(added.getId())
		        .toUri();

		return ResponseEntity.created(uri).body(added);
	}

	@DeleteMapping("/jpa/users/{id}")
	public List<Resource<User>> deleteUser(@PathVariable("id") int id) {
		userService.deleteById(id);
		return getAllUsers();
	}

	@Override
	@GetMapping("/jpa/users/{userId}/posts")
	public List<Post> getPosts(@PathVariable("userId") int userId) {
		return userService.findPostsByUserId(userId)
		        .orElseThrow(() -> new UserNotFoundException(userId));
	}

	@Override
	public ResponseEntity<Post> createPost(int userId, Post post) {
		User user = userService.findById(userId)
		        .orElseThrow(() -> new UserNotFoundException(userId));
		post.setUser(user);


		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		        .path("/{userId}/posts").buildAndExpand(userId)
		        .toUri();

		return ResponseEntity.created(uri).body(post);
	}

}
