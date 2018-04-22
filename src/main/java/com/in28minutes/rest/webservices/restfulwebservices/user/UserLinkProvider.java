package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;;

@Component
public class UserLinkProvider {
	public Link linkToAllUsers(UserController controller) {
		return linkTo(methodOn(controller.getClass()).getAllUsers()).withRel("all-users");
	}

	public Link linkToSingleUser(User user, UserController controller) {
		return linkTo(methodOn(controller.getClass()).getById(user.getId())).withRel("display-user");
	}

	public Link linkToUserPosts(User user, UserController controller) {
		return linkTo(methodOn(controller.getClass()).getPosts(user.getId())).withRel("user-posts");
	}
}
