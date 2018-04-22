package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(int id) {
		super(String.format("Post with id %d does not exist", id));
	}

	public PostNotFoundException(int userId, int postId) {
		super(String.format("Post with id %d does not exist for user %d", postId, userId));
	}
}
