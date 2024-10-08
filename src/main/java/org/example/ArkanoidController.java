package org.example;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.example.arkanoid.source.Ball;
import org.example.arkanoid.source.Platform;
import org.example.arkanoid.view.BallView;
import org.example.arkanoid.view.PlatformView;

public class ArkanoidController {
    private final double DEFAULT_STEP_SIZE = 10.0,
            WINDOW_PROPORTION = 3.0 / 4.0,
            DEFAULT_BALL_SPEED = 250.0,
            PLATFORM_ELEVATION = 20.0;
    private final int DEFAULT_FPS = 60;


    private final DoubleProperty scale = new SimpleDoubleProperty();
    private final BooleanProperty lost = new SimpleBooleanProperty(false);
    private double stepSize = DEFAULT_STEP_SIZE;
    private boolean started = false;

    @FXML
    private AnchorPane pane;
    @FXML
    private BorderGameView borderView;
    @FXML
    private PlatformView platformView;
    @FXML
    private BallView ballView;
    @FXML
    public Label labelLost;

    private BorderGame border;
    private Platform platform;
    private Ball ball;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
        ball.stopAnimation();
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

    private void startGame() {
        ball.centerXProperty().unbind();
        ball.hasStarted();
        ball.startAnimation();
    }

    private void lostGame() {
        labelLost.setVisible(true);
        started = false;
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
            case SPACE:
                if (started || lost.get())
                    break;
                startGame();
                started = true;
                break;
            case ESCAPE:
                switchToMenu();
                break;
        }
    }

    @FXML
    public void initialize() {
        bindBorderView();
        border = new BorderGame();
        border.setY(borderView.getY());
        initPlatform();
        initBall();
        scale.bind(borderView.widthProperty().divide(App.initialWidth));
        bindPlatformView();
        bindBallView();
        addResizeListeners();
        lost.bind(ball.lostProperty());
        lost.addListener(((observable, oldValue, newValue) -> {if (newValue) lostGame();}));
        labelLost.translateXProperty().bind(borderView.widthProperty().subtract(labelLost.widthProperty()).divide(2));
        labelLost.translateYProperty().bind(borderView.heightProperty().subtract(borderView.yProperty()).subtract(labelLost.heightProperty()).divide(2));
    }

    private void addResizeListeners() {
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


    private void bindBorderView() {
        borderView.widthProperty().bind(pane.widthProperty());
        borderView.heightProperty().bind(borderView.widthProperty().multiply(WINDOW_PROPORTION).subtract(borderView.getY()));
    }

    private void initPlatform() {
        platform = new Platform();
        platform.setBorder(border);
        platform.setY(border.getHeight() - platform.getHeight() - PLATFORM_ELEVATION);
        platform.setX((border.getWidth() - platform.getWidth()) / 2);
    }
    private void initBall() {
        ball = new Ball();
        ball.centerXProperty().bind(platform.xProperty().add(platform.widthProperty().divide(2.0)));
        ball.setPlatform(platform);
        ball.setBorder(border);
        ball.setCenterY(platform.getY() - ball.getRadius());
        ball.setSpeed(DEFAULT_BALL_SPEED / DEFAULT_FPS);
        ball.setAnimation(DEFAULT_FPS);
    }

    private void bindPlatformView() {
        platformView.xProperty().bind(scale.multiply(platform.xProperty()));
        platformView.yProperty().bind(scale.multiply(platform.yProperty()));
        platformView.widthProperty().bind(scale.multiply(platform.widthProperty()));
        platformView.heightProperty().bind(scale.multiply(platform.heightProperty()));
    }

    private void bindBallView() {
        ballView.centerXProperty().bind(scale.multiply(ball.centerXProperty()));
        ballView.centerYProperty().bind(scale.multiply(ball.centerYProperty()));
        ballView.radiusProperty().bind(scale.multiply(ball.radiusProperty()));
    }
}