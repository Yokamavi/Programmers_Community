package com.programmers.programmers_community.controller;

import com.programmers.programmers_community.model.User;
import com.programmers.programmers_community.service.UserService;
import com.programmers.programmers_community.util.Navigator;
import com.programmers.programmers_community.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;


// Controller of the edit profile screen (edit-profile-view.fxml)
// The user can change the password, the gender and the language. The username can't be changed
public class EditProfileController {
    private final static UserService us = new UserService();

    // Elements of the FXML file, they are linked by their fx:id
    @FXML private Button btnReturn;
    @FXML private PasswordField pfPassword;
    @FXML private ComboBox<String> cbLanguages;
    @FXML private RadioButton rbMan, rbWoman, rbOther;
    @FXML private ToggleGroup tgGender;
    @FXML private Label lblPasswordErrors;

    // Goes back to the home screen without saving anything
    @FXML
    private void onReturn() {
        Navigator.goTo(btnReturn, "home-view.fxml");
    }

    // Runs when the user clicks on "Confirm Changes"
    // TODO: show an error alert when modifyUser returns false
    @FXML
    private void onConfirm() {
        if (validateFields()) {
            // We create a user with the same username as the current one and the new data
            User modifiedUser = new User(Navigator.getCurrentUser().getUsername(), pfPassword.getText(), getSelectedGender(), cbLanguages.getValue());
            if (us.modifyUser(modifiedUser)) {
                // We update the current user too, so the app doesn't keep working with the old data
                Navigator.setCurrentUser(modifiedUser);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Successfully updated your profile");
            }
        }
    }

    // It runs automatically when the screen is loaded. Here we fill the combo box with the languages
    // TODO: fill the form with the current data of the user
    @FXML
    private void initialize() {
        cbLanguages.getItems().addAll("Java", "Python", "JavaScript", "HTML");
    }

    // Checks the fields, shows the errors and marks the wrong ones in red
    // It returns true only if everything is correct
    private boolean validateFields() {
        String style = "";
        String passwordError = Validator.validatePassword(pfPassword.getText());
        // The gender and the language are correct only if the user has selected something
        boolean genderOk = getSelectedGender() != null ;
        boolean languageOk = cbLanguages.getValue() != null;


        // TODO: use CSS classes instead of writing the styles here, the same code is repeated in other controllers
        // Password: if there is an error message we paint the field in red
        lblPasswordErrors.setText(passwordError);
        style = (lblPasswordErrors.getText() != null) ? "-fx-background-color: #ffcccc; -fx-border-color: red;" : "";
        pfPassword.setStyle(style);


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
        return (passwordError == null && genderOk && languageOk);
    }
    // Returns the text of the selected radio button ("Man", "Woman" or "Other"), or null if none is selected
    // TODO: this method is repeated in RegisterController, it could be moved to a util class
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