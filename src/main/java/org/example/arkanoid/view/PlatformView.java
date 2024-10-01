package org.example.arkanoid.view;

import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import org.example.BorderGame;
import org.example.arkanoid.source.Platform;

public class PlatformView extends Rectangle {
    @FXML
    Platform platform;

    public PlatformView() {
        platform = new Platform();
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public BorderGame getBorder() {
        return platform.getBorder();
    }

    public double getBorderX() {
        return getBorder().getX();
    }

    public double getBorderWidth() {
        return getBorder().getWidth();
    }
}
