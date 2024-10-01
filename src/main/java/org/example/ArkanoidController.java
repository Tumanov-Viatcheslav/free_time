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
import org.example.arkanoid.source.Platform;
import org.example.arkanoid.view.PlatformView;

public class ArkanoidController {
    private final double DEFAULT_STEP_SIZE = 10.0,
            WINDOW_PROPORTION = 3.0 / 4.0;


    private DoubleProperty scale = new SimpleDoubleProperty();
    private double stepSize = DEFAULT_STEP_SIZE;

    @FXML
    private Label labelArkanoid;
    @FXML
    private HBox hBox;
    @FXML
    private Button menuButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private PlatformView platformView;
    @FXML
    private BorderGame border;
    @FXML
    private Platform platform;
    @FXML
    private BorderGameView borderView;

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
        borderView.widthProperty().bind(pane.widthProperty());
        borderView.heightProperty().bind(borderView.widthProperty().multiply(WINDOW_PROPORTION).subtract(borderView.getY()));
        scale.bind(borderView.widthProperty().divide(App.initialWidth));
        menuButton.setFocusTraversable(false);
        platformView.setFocusTraversable(true);
        platform.setBorder(border);
        platform.setY(border.getHeight() - 20.0);
        platform.setX((border.getWidth() - platform.getX()) / 2);
        bindPlatformView();
        labelArkanoid.setText(labelText);
        //TODO tinker conditions to make resize right
        pane.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (((double) newValue < borderView.getWidth()) ||
                    (borderView.getHeight() < pane.getHeight() - borderView.getY())) {
                borderView.widthProperty().bind(pane.widthProperty());
            }
            borderView.widthProperty().unbind();
        });
        pane.heightProperty().addListener((observable, oldValue, newValue) -> {
            borderView.widthProperty().bind(pane.widthProperty());
            if (((double) newValue < borderView.getWidth()) ||
                    (double) newValue - borderView.getY() < borderView.getHeight() ||
                    (borderView.getHeight() < (double) newValue - borderView.getY())) {
                borderView.widthProperty().unbind();
                borderView.setWidth((pane.getHeight()) / WINDOW_PROPORTION);
                if (pane.getWidth() < borderView.getWidth())
                    borderView.widthProperty().bind(pane.widthProperty());
            }
        });
    }

    private void bindPlatformView() {
        platformView.xProperty().bind(scale.multiply(platform.xProperty()));
        platformView.yProperty().bind(scale.multiply(platform.yProperty()));
        platformView.widthProperty().bind(scale.multiply(platform.widthProperty()));
        platformView.heightProperty().bind(scale.multiply(platform.heightProperty()));
    }
}