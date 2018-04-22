package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;

public interface UserController {

	// retrieve all
	List<Resource<User>> getAllUsers();

	// retrieve user (id)
	Resource<User> getById(int id);

	ResponseEntity<User> createUser(User toAdd);

	List<Resource<User>> deleteUser(int id);

	List<Post> getPosts(int userId);

	ResponseEntity<Post> createPost(int userId, Post post);
}