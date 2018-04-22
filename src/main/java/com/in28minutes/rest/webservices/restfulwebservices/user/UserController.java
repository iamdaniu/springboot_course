package com.in28minutes.rest.webservices.restfulwebservices.user;

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

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserLinkProvider linkProvider;

	// retrieve all
	@GetMapping("/users")
	public List<Resource<User>> getAllUsers() {
		List<Resource<User>> userResources = userService.findAll().stream()
		        .map(u -> new Resource<>(u, new Link[]{ linkProvider.linkToSingleUser(u, this),
		        		linkProvider.linkToUserPosts(u, this)
		        }))
		        .collect(Collectors.toList());
		return userResources;
	}

	// retrieve user (id)
	@GetMapping("/users/{id}")
	public Resource<User> getById(@PathVariable("id") int id) {
		final User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return new Resource<User>(user, linkProvider.linkToAllUsers(this));
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User toAdd) {
		User added = userService.save(toAdd);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		        .path("/{id}").buildAndExpand(added.getId())
		        .toUri();

		return ResponseEntity.created(uri).body(added);
	}

	@DeleteMapping("/users/{id}")
	public List<Resource<User>> deleteUser(@PathVariable("id") int id) {
		userService.deleteById(id);
		return getAllUsers();
	}

	@GetMapping("/users/{userId}/posts/{postId}")
	public Resource<Post> getPost(@PathVariable("userId") int userId, @PathVariable("postId") int postId) {
		User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		Post post = user.getPosts().stream()
		        .findFirst()
		        .orElseThrow(() -> new PostNotFoundException(userId, postId));

		Resource<Post> result = new Resource<Post>(post, linkProvider.linkToSingleUser(user, this));

		return result;
	}

	@GetMapping("/users/{userId}/posts")
	public List<Post> getPosts(@PathVariable("userId") int userId) {
		return userService.findById(userId).map(User::getPosts)
		        .orElseThrow(() -> new UserNotFoundException(userId));
	}

	@PostMapping("/users/{userId}/posts")
	public ResponseEntity<Post> createPost(@PathVariable("userId") int userId, @RequestBody Post post) {
		User user = userService.findById(userId)
		        .orElseThrow(() -> new UserNotFoundException(userId));

		post.setUser(user);
		userService.save(post);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		        .path("/{id}").buildAndExpand(post.getId())
		        .toUri();

		return ResponseEntity.created(uri).build();
	}

}
