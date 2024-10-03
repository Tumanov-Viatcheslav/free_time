package org.example.arkanoid.source;

import javafx.scene.shape.Rectangle;
import org.example.BorderGame;

public class Platform extends Rectangle {
    private BorderGame border;

    public Platform() {
        super(50, 10);
    }

    public void setBorder(BorderGame border) {
        this.border = border;
    }

    public double getBorderX() {
        return border.getX();
    }

    public double getBorderWidth() {
        return border.getWidth();
    }

    public void moveLeft(double step) {
        if (getX() - step > getBorderX())
            setX(getX() - step);
        else setX(getBorderX());
    }

    public void moveRight(double step) {
        if (getX() + getWidth() + step < getBorderWidth())
            setX(getX() + step);
        else setX(getBorderWidth() - getWidth());
    }
}
