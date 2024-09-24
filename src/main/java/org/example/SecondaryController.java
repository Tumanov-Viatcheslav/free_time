package org.example;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SecondaryController {
    @FXML
    private Label label1;
    @FXML
    private Rectangle rectangle;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("menu");
    }

    private void moveDown() {
        if (rectangle.getY() + 10 < rectangle.getLayoutY())
            rectangle.setY(rectangle.getY() + 10);
    }

    private void moveLeft() {
        if (rectangle.getX() - 10 > 0)
            rectangle.setY(rectangle.getX() - 10);
    }

    @FXML
    private void handleOnKeyPressed(KeyEvent keyEvent) {
        KeyCode key = keyEvent.getCode();
        switch (key) {
            case DOWN:
            case S:
                moveDown();
                break;
            case LEFT:
            case A:
                moveLeft();
        }
    }

    @FXML
    private void handleOnKeyReleased(KeyEvent keyEvent) {
    }

    @FXML
    public void initialize() {
    }
}