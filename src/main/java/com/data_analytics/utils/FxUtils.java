package com.data_analytics.utils;

import com.data_analytics.MyApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class FxUtils {

    /**
     * Displays an alert dialog with the given message.
     *
     * @param message the message to display in the alert dialog
     */
    public static void alert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation dialog with the given message.
     *
     * @param message the message to display in the confirmation dialog
     * @return true if the user confirms, false otherwise
     */
    public static boolean confirm(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Shows an FXML dialog with the given FXML file path, title, width, and height.
     *
     * @param fxmlPath the path to the FXML file
     * @param title the title of the dialog
     * @param width the width of the dialog
     * @param height the height of the dialog
     * @param <T> the type of the dialog content
     * @return the dialog content
     */
    public static <T> T showFxmlDialog(String fxmlPath, String title, int width, int height) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            T controller = fxmlLoader.getController();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}