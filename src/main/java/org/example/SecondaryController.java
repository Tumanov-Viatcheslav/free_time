package org.example;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.example.arkanoid.Platform;

public class SecondaryController {
    private final double DEFAULT_STEP_SIZE = 10.0;
    private DoubleProperty scale = new SimpleDoubleProperty(1.0);
    private double stepSize = DEFAULT_STEP_SIZE;

    @FXML
    private Label label1;
    @FXML
    private HBox hBox;
    @FXML
    private Button menuButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private Platform platform;
    @FXML
    private BorderGame border;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    private void moveUp() {
        if (platform.getY() - stepSize > border.getY())
            platform.setY(platform.getY() - stepSize);
    }

    private void moveDown() {
        if (platform.getY() + platform.getHeight() + stepSize < border.getHeight())
            platform.setY(platform.getY() + stepSize);
    }

    private void moveLeft() {
        platform.moveLeft(stepSize);
    }

    private void moveRight() {
        platform.moveRight(stepSize);
    }

    @FXML
    private void handleOnKeyPressed(KeyEvent keyEvent) throws IOException {
        //rectangle.setX(rectangle.xProperty().get());
        platform.xProperty().unbind();
        KeyCode key = keyEvent.getCode();
        switch (key) {
//            case UP:
//            case W:
//                moveUp();
//                break;
//            case DOWN:
//            case S:
//                moveDown();
//                break;
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
        String labelText = "ARKANOID";
        border.widthProperty().bind(pane.widthProperty());
        border.heightProperty().bind(pane.heightProperty().subtract(border.getY()));
        menuButton.setFocusTraversable(false);
        platform.setFocusTraversable(true);
        platform.setBorder(border);
        platform.yProperty().bind(border.heightProperty().add(10));
        //platform.xProperty().bind(border.widthProperty().divide(2).subtract(platform.widthProperty().divide(2)));
        platform.xProperty().bind(scale.multiply(platform.getX()).add(App.initialWidth / 2 - platform.getWidth() / 2));
        label1.setText(labelText);
        border.widthProperty().addListener((observable, oldValue, newValue) -> {
            if ((double)oldValue != 0.0) {
                double platformX = platform.getX(), platformWidth = platform.getWidth();
                scale.setValue((double) newValue / (double) oldValue);
                stepSize = DEFAULT_STEP_SIZE * scale.get();
                platform.xProperty().bind(scale.multiply(
                        (scale.get() * (platformX + platformWidth)) < (double) newValue ? platformX : (((double) newValue - platformWidth) / scale.get())
                ));
                System.out.println((double) newValue - platformWidth);
                System.out.println(platformX);
            }
        });
    }
}