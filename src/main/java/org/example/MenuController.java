package org.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;

public class MenuController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void exit(ActionEvent e) {
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }
}
