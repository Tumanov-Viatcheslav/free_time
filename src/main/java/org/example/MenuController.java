package org.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;

public class MenuController {
    @FXML
    private Button arkanoidButton;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("arkanoid");
    }

    @FXML
    private void exit(ActionEvent e) {
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void initialize() {
        final String arkanoidButtonText = "Arkanoid";
        arkanoidButton.setText(arkanoidButtonText);
    }
}
