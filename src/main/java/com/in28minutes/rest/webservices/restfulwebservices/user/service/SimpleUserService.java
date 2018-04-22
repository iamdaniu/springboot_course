package com.in28minutes.rest.webservices.restfulwebservices.user.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.in28minutes.rest.webservices.restfulwebservices.user.Post;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;
import com.in28minutes.rest.webservices.restfulwebservices.user.UserService;

@Component
public class SimpleUserService implements UserService {
	public static final String ID = "SimpleService";

	private static List<User> users = new ArrayList<>();
	private static AtomicInteger nextUserId = new AtomicInteger(1);

	static {
		for (String s : new String[] { "Adam", "Eve", "Jack" }) {
			users.add(new User(nextUserId.getAndIncrement(), s, LocalDate.now()));
		}
	}

	/* (non-Javadoc)
	 * @see com.in28minutes.rest.webservices.restfulwebservices.user.UserService#findAll()
	 */
	@Override
	public List<User> findAll() {
		return Collections.unmodifiableList(users);
	}

	/* (non-Javadoc)
	 * @see com.in28minutes.rest.webservices.restfulwebservices.user.UserService#add(com.in28minutes.rest.webservices.restfulwebservices.user.User)
	 */
	@Override
	public User save(User newUser) {
		if (newUser.getId() == 0) {
			newUser.setId(nextUserId.getAndIncrement());
		}
		users.add(newUser);
		return newUser;
	}

	/* (non-Javadoc)
	 * @see com.in28minutes.rest.webservices.restfulwebservices.user.UserService#findById(int)
	 */
	@Override
	public Optional<User> findById(int id) {
		return users.stream().filter(u -> u.getId() == id).findFirst();
	}

	/* (non-Javadoc)
	 * @see com.in28minutes.rest.webservices.restfulwebservices.user.UserService#deleteById(int)
	 */
	@Override
	public void deleteById(int id) {
		findById(id).ifPresent(users::remove);
	}

	// @Override
	// public Optional<List<Post>> findPostsByUser(User user) {
	// return Optional.of(Collections.emptyList());
	// }

	@Override
	public void save(Post post) {
		// not implemented
	}
}
