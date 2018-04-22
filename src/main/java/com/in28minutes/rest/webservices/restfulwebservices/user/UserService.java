package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

	List<User> findAll();

	User save(User newUser);

	Optional<User> findById(int id);

	void deleteById(int id);

	// Optional<List<Post>> findPostsByUserId(int userId);

	void save(Post post);
}