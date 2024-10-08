package org.example.arkanoid.source;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.example.BorderGame;

public class Platform {
    private BorderGame border;

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private final DoubleProperty width = new SimpleDoubleProperty();
    private final DoubleProperty height = new SimpleDoubleProperty();

    public Platform() {
        setWidth(50);
        setHeight(10);
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

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setHeight(double height) {
        this.height.set(height);
    }
}
