package org.example.arkanoid.source;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.example.BorderGame;

public class Ball {
    // TODO extract a base class if need arises
    // Properties of a ball as moving object
    private BallDirection direction = BallDirection.UP_RIGHT;
    private double speed;

    // Properties of ball as circle
    private final DoubleProperty centerX = new SimpleDoubleProperty();
    private final DoubleProperty centerY = new SimpleDoubleProperty();
    private final DoubleProperty radius = new SimpleDoubleProperty();

    public Ball() {
        setRadiusProperty(10);
    }

    public BallDirection getDirection() {
        return direction;
    }

    public void setDirection(BallDirection direction) {
        this.direction = direction;
    }

    public void turnLeft() {
        direction = BallDirection.values()[(direction.ordinal() + 1) % BallDirection.values().length];
    }

    public void turnRight() {
        direction = BallDirection.values()[(BallDirection.values().length + direction.ordinal() - 1) % BallDirection.values().length];
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void moveTo(double x, double y) {
        setCenterX(x);
        setCenterY(y);
    }

    public double getCenterX() {
        return centerX.get();
    }

    public DoubleProperty centerXProperty() {
        return centerX;
    }

    public void setCenterX(double centerXProperty) {
        this.centerX.set(centerXProperty);
    }

    public double getCenterY() {
        return centerY.get();
    }

    public DoubleProperty centerYProperty() {
        return centerY;
    }

    public void setCenterY(double centerYProperty) {
        this.centerY.set(centerYProperty);
    }

    public double getRadius() {
        return radius.get();
    }

    public DoubleProperty radiusProperty() {
        return radius;
    }

    public void setRadiusProperty(double radiusProperty) {
        this.radius.set(radiusProperty);
    }
}
