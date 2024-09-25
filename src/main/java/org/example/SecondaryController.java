package org.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class SecondaryController {
    private final int STEP_SIZE = 1;

    @FXML
    private Label label1;
    @FXML
    private Button menuButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Rectangle border;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    private void moveUp() {
        if (rectangle.getY() - STEP_SIZE > border.getY())
            rectangle.setY(rectangle.getY() - STEP_SIZE);
    }

    private void moveDown() {
        if (rectangle.getY() + rectangle.getHeight() + STEP_SIZE < border.getHeight())
            rectangle.setY(rectangle.getY() + STEP_SIZE);
    }

    private void moveLeft() {
        if (rectangle.getX() - STEP_SIZE > border.getX())
            rectangle.setX(rectangle.getX() - STEP_SIZE);
    }

    private void moveRight() {
        if (rectangle.getX() + rectangle.getWidth() + STEP_SIZE < border.getWidth())
            rectangle.setX(rectangle.getX() + STEP_SIZE);
    }

    @FXML
    private void handleOnKeyPressed(KeyEvent keyEvent) throws IOException {
        KeyCode key = keyEvent.getCode();
        switch (key) {
            case UP:
            case W:
                moveUp();
                break;
            case DOWN:
            case S:
                moveDown();
                break;
            case LEFT:
            case A:
                moveLeft();
                break;
            case RIGHT:
            case D:
                moveRight();
                break;
            case ESCAPE:
                switchToMenu();
                break;
        }
    }

    @FXML
    public void initialize() {
        border.widthProperty().bind(pane.widthProperty());
        border.heightProperty().bind(pane.heightProperty());
        menuButton.setFocusTraversable(false);
        rectangle.setFocusTraversable(true);
    }
}