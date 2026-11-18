package com.programmers.programmers_community.util;

import com.programmers.programmers_community.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

// Helper class to change between screens and to remember which user is logged in
public class Navigator {

    // The user that is logged in. It is static, so all the controllers can access it
    // Lombok creates the static methods getCurrentUser() and setCurrentUser()
    @Getter
    @Setter
    private static User currentUser;

    // Folder where all the FXML files are
    private static final String VIEW_PATH = "/com/programmers/programmers_community/view/";

    // Changes the current window to another screen
    // anyNode can be any element of the current screen (for example a button), we use it to find the window
    public static void goTo(Node anyNode, String fxmlFile) {
        try {
            // We load the FXML file and create a new scene with it
            FXMLLoader fxmlLoader = new FXMLLoader(Navigator.class.getResource(VIEW_PATH + fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            // We get the window (Stage) where the node is and put the new scene in it
            Stage stage = (Stage) anyNode.getScene().getWindow();
            stage.setScene(scene);
            // We adjust the window to the size of the new screen and center it
            stage.sizeToScene();
            stage.centerOnScreen();
        } catch (IOException e) {
            // If the FXML file can't be loaded the app can't continue
            throw new RuntimeException(e);
        }
    }
}