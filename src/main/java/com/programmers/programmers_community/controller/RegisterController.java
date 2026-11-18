package com.programmers.programmers_community.controller;

import com.programmers.programmers_community.service.UserService;
import com.programmers.programmers_community.util.Navigator;
import com.programmers.programmers_community.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

// Controller of the register screen (register-view.fxml)
public class RegisterController {

	private final static UserService us = new UserService();

	// Elements of the FXML file, they are linked by their fx:id
	@FXML private TextField txtUserRegister;
	@FXML private PasswordField pfPasswordRegister;
	// The ToggleGroup makes the three radio buttons work together, so only one can be selected
	@FXML private ToggleGroup tgGender;
	@FXML private ComboBox<String> cbLanguages;
	@FXML private Button btnReturn;
	@FXML private Label lblUserErrors, lblPasswordErrors;
	@FXML private RadioButton rbMan, rbWoman , rbOther;

	// It runs automatically when the screen is loaded. Here we fill the combo box with the languages
	@FXML
	private void initialize() {
		cbLanguages.getItems().addAll("Java", "Python", "JavaScript", "HTML");
	}

	// Runs when the user clicks on the "Sign Up" button
	// TODO: after a successful registration we could go back to the login screen
	@FXML
	protected void onSignUp() {
		if (validateFields()) {
			// registerUser returns true if the user was saved, false if the username already exists
			if (us.registerUser(txtUserRegister.getText(), pfPasswordRegister.getText(), getSelectedGender(), cbLanguages.getValue())) {
				showAlert(Alert.AlertType.INFORMATION, "Registration completed",
						"The user has been registered successfully.");
			}
			else {
				showAlert(Alert.AlertType.ERROR, "Could not register",
						"That username already exists.");
			}
		}

	}

	// Goes back to the login screen
	@FXML
	protected void onReturn() {
		Navigator.goTo(btnReturn, "login-view.fxml");
	}

	// Checks all the fields, shows the errors and marks the wrong ones in red
	// It returns true only if everything is correct
	private boolean validateFields() {
		String style = "";
		String userError = Validator.validateUsername(txtUserRegister.getText());
		String passwordError = Validator.validatePassword(pfPasswordRegister.getText());
		// The gender and the language are correct only if the user has selected something
		boolean genderOk = getSelectedGender() != null ;
		boolean languageOk = cbLanguages.getValue() != null;


		// TODO: use CSS classes instead of writing the styles here, the same code is repeated in other controllers
		// Username: if there is an error message we paint the field in red
		lblUserErrors.setText(userError);
		style = (lblUserErrors.getText() != null) ? "-fx-background-color: #ffcccc; -fx-border-color: red;" : "";
		txtUserRegister.setStyle(style);

		// Password: same as the username
		lblPasswordErrors.setText(passwordError);
		style = (lblPasswordErrors.getText() != null) ? "-fx-background-color: #ffcccc; -fx-border-color: red;" : "";
		pfPasswordRegister.setStyle(style);


		// Gender: if nothing is selected the text of the three radio buttons turns red
		style = !genderOk ? "-fx-text-fill: #B91C1C;" : "";

		rbMan.setStyle(style);
		rbWoman.setStyle(style);
		rbOther.setStyle(style);


		// Language: if nothing is selected the combo box is painted in red
		if (!languageOk) {
			cbLanguages.setStyle("-fx-background-color: #FEE2E2; -fx-border-color: #B91C1C;");
		}
		else {
			cbLanguages.setStyle("");
		}
		return (userError == null && passwordError == null && genderOk && languageOk);
	}

	// Returns the text of the selected radio button ("Man", "Woman" or "Other"), or null if none is selected
	// TODO: this method is repeated in EditProfileController, it could be moved to a util class
	private String getSelectedGender() {
		RadioButton gender = (RadioButton) tgGender.getSelectedToggle();
		return gender == null ? null : gender.getText();
	}

	// Shows a pop-up of the given type (information or error) and waits until the user closes it
	// TODO: this method is repeated in other controllers, it could be moved to a util class
	private void showAlert(Alert.AlertType type, String header, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Information");
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
}