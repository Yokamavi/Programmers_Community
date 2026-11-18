package com.programmers.programmers_community.util;

// Helper class to check the username and the password
// The methods return the error message, or null if everything is correct
public class Validator {
	// The username must have between 5 and 20 characters and only letters, numbers, _ and .
	public static String validateUsername(String user) {

		if (user.length() < 5) {
			return "Minimum 5 characters";
		}
		if (user.length() > 20) {
			return "Maximum 20 characters";
		}

		// Regular expression: only the characters inside the brackets are allowed
		if (!user.matches("[A-Za-z0-9_.]+")) {
			return "Letters, numbers, _ and . only";
		}
		return null;
	}

	// The password must have at least 8 characters, one lowercase letter and one uppercase letter
	public static String validatePassword(String password) {

		if (password.length() < 8) {
			return "Minimum 8 characters";
		}

		// ".*[a-z].*" means that there is at least one lowercase letter somewhere in the text
		if (!password.matches(".*[a-z].*")) {
			return "Must contain a lowercase letter";
		}

		if (!password.matches(".*[A-Z].*")) {
			return "Must contain an uppercase letter";
		}
		return null;
	}
}