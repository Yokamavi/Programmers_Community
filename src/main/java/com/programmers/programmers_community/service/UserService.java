package com.programmers.programmers_community.service;

import com.programmers.programmers_community.dao.UserDAO;
import com.programmers.programmers_community.model.User;

import java.util.List;

// Service layer: it sits between the controllers and the DAO.
// Here we put the logic of the app (checking passwords, checking if a user already exists...)
public class UserService {
	private final UserDAO userDAO = new UserDAO();

	// Returns the user if the username and password are correct, otherwise it returns null
	public User login (String username, String password) {
		// Usernames are saved in lowercase, so we lowercase it here too
		User user = userDAO.consultUser(username.toLowerCase());
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}


	// Registers a new user. Returns false if that username is already taken
	public boolean registerUser (String username, String password, String gender, String language) {
		String name = username.toLowerCase();
		// If the DAO finds a user with that name, we can't register it again
		if (userDAO.consultUser(name) != null) {
			return false;
		}

		User user = new User(name, password, gender, language);
		userDAO.registerUser(user);
		return true;
	}

	// Modifies a user. Returns false if the user doesn't exist, so there is nothing to modify
	public boolean modifyUser(User user) {
		if (userDAO.consultUser(user.getUsername()) == null) {
			return false;
		}
		userDAO.modifyUser(user);
		return true;
	}

	// Gets the users filtered by language (null means all the languages), without the current user
	public List<User> consultByLanguage(String language, User user) {
		return userDAO.consultByLanguage(language, user);
	}
}