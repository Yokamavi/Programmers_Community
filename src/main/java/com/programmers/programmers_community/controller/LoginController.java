package com.programmers.programmers_community.controller;

import com.programmers.programmers_community.model.User;
import com.programmers.programmers_community.service.UserService;
import com.programmers.programmers_community.util.Navigator;
import com.programmers.programmers_community.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

// Controller of the login screen (login-view.fxml)
public class LoginController {

	// The @FXML fields are linked with the elements of the FXML file that have the same fx:id
	@FXML private Label lblUserErrors, lblPasswordErrors;

	@FXML private TextField txtUserLogin;
	@FXML private PasswordField pfPasswordLogin;

	@FXML private Button btnSignUp, btnLogin;

	// The service is shared by all the instances of the controller
	private final static UserService us = new UserService();

	// Runs when the user clicks on the "Log In" button
	@FXML
	protected void onLogIn() {
		// First we check that the fields have a valid format
		if (validateFields()) {
			User user = us.login(txtUserLogin.getText(), pfPasswordLogin.getText());
			if (user != null) {
				// We save the logged user so the other screens can use it, and go to the home screen
				Navigator.setCurrentUser(user);
				Navigator.goTo(btnLogin, "home-view.fxml");
			}
			else {
				// We don't say if it was the username or the password that failed
				showError("Invalid username or password");
			}
		}
	}

	// Runs when the user clicks on the "Sign Up" button, it opens the register screen
	@FXML
	protected void onSignUp() {
		Navigator.goTo(btnSignUp, "register-view.fxml");
	}

	// TODO: when logging in we should only check that the fields are not empty, the format rules are for the register
	// Checks the username and the password, shows the error messages and marks the wrong fields in red
	private boolean validateFields() {
		String style = "";
		// The validator returns the error message, or null if everything is correct
		String userError = Validator.validateUsername(txtUserLogin.getText());
		String passwordError = Validator.validatePassword(pfPasswordLogin.getText());

		lblUserErrors.setText(userError);
		lblPasswordErrors.setText(passwordError);

		// TODO: use a CSS class instead of writing the style here, because this is repeated in other controllers
		// If the label has an error message we paint the field in red, if not we remove the style
		style = (lblUserErrors.getText() != null) ? "-fx-background-color: #ffcccc; -fx-border-color: red;" : "";
		txtUserLogin.setStyle(style);


		style = (lblPasswordErrors.getText() != null) ? "-fx-background-color: #ffcccc; -fx-border-color: red;" : "";
		pfPasswordLogin.setStyle(style);

		// It is valid only if there are no errors at all
		return (userError == null && passwordError == null);
	}

	// TODO: this alert code is repeated in other controllers, it could be moved to a util class
	// Shows an error pop-up with the message and waits until the user closes it
	private void showError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}