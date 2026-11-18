package com.programmers.programmers_community.controller;

import com.programmers.programmers_community.model.User;
import com.programmers.programmers_community.service.UserService;
import com.programmers.programmers_community.util.ExcelGenerator;
import com.programmers.programmers_community.util.Navigator;
import com.programmers.programmers_community.util.PdfGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;

// Controller of the home screen (home-view.fxml)
// It shows a table with the other users, which can be filtered by language and exported to Excel or PDF
public class HomeController {

	private final static UserService us = new UserService();

	// Elements of the FXML file, they are linked by their fx:id
	@FXML private Label lblWelcome;
	@FXML private TableView<User> tblUsers;
	@FXML private TableColumn<User, String> colUser, colGender, colLanguage;
	@FXML private ComboBox<String> cbLanguages;
	@FXML private Button btnLogOut, btnEditProfile;

	// The table shows this list. When we change the list, the table is updated automatically
	private final ObservableList<User> usersList = FXCollections.observableArrayList();

	// It runs automatically when the screen is loaded
	@FXML
	private void initialize() {
		// The filter starts with "All" selected
		cbLanguages.getItems().setAll("All", "Java", "Python", "JavaScript", "HTML");
		cbLanguages.setValue("All");
		// Each column takes its value from an attribute of User (it uses the getters, like getUsername())
		colUser.setCellValueFactory(new PropertyValueFactory<>("username"));
		colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		colLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));
		tblUsers.setItems(usersList);
		loadTable(cbLanguages.getValue());
		// Welcome message with the username starting with a capital letter
		String name = Navigator.getCurrentUser().getUsername().substring(0,1).toUpperCase() + Navigator.getCurrentUser().getUsername().substring(1);
		lblWelcome.setText("Welcome, " + name + "!");
	}

	// TODO: remove the current user when logging out
	@FXML
	private void onLogOut() {
		Navigator.goTo(btnLogOut, "login-view.fxml");
	}

	// Exports the users of the table to an Excel file
	// TODO: catch the IOException and show an alert, so the user knows if it fails
	@FXML
	private void onExcel() throws IOException {
		ExcelGenerator.generate(usersList);
	}

	// Exports the users of the table to a PDF file
	// TODO: catch the IOException and show an alert, so the user knows if it fails
	@FXML
	private void onPdf() throws IOException {
		PdfGenerator.generate(usersList);
	}

	@FXML
	private void onEditProfile() {
		Navigator.goTo(btnEditProfile, "edit-profile-view.fxml");
	}
	// Runs every time the user selects a language in the filter
	@FXML
	private void onLanguageSelection() {
		loadTable(cbLanguages.getValue());
	}

	// Fills the table with the users of the selected language (never including the current user)
	private void loadTable(String language) {
		List<User> result;
		if (language.equals("All")) {
			// null means "no language filter"
			result = us.consultByLanguage(null, Navigator.getCurrentUser());
		}
		else {
			result = us.consultByLanguage(language, Navigator.getCurrentUser());
		}

		usersList.setAll(result);
	}
}