package com.in28minutes.rest.webservices.restfulwebservices.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.in28minutes.rest.webservices.restfulwebservices.user.Post;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;
import com.in28minutes.rest.webservices.restfulwebservices.user.UserService;

@Component
@Primary
public class JpaUserService implements UserService {
	public static final String ID = "JpaService";

	@Autowired
	private JpaRepository<User, Integer> userRepository;

	@Autowired
	private JpaRepository<Post, Integer> postRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User newUser) {
		return userRepository.save(newUser);
	}

	@Override
	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public Optional<List<Post>> findPostsByUserId(int userId) {
		return userRepository.findById(userId)
		        .map(User::getPosts);
	}

	@Override
	public void save(Post post) {
		postRepository.save(post);
	}

}
