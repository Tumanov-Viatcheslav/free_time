package org.example.arkanoid.source;

import javafx.scene.shape.Circle;

public class Ball extends Circle {
    BallDirection direction;
    double speed;

    public BallDirection getDirection() {
        return direction;
    }

    public void setDirection(BallDirection direction) {
        this.direction = direction;
    }

    public void turnLeft() {
        direction = BallDirection.valueOf(String.valueOf((direction.ordinal() + 1) % BallDirection.values().length));
    }

    public void turnRight() {
        direction = BallDirection.valueOf(String.valueOf((direction.ordinal() - 1) % BallDirection.values().length));
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void move() {
        //TODO collision handler
        switch (direction) {
            case UP_RIGHT:
                setCenterX(getCenterX() + speed);
                setCenterY(getCenterY() - speed);
                break;
            case UP_LEFT:
                setCenterX(getCenterX() - speed);
                setCenterY(getCenterY() - speed);
                break;
            case DOWN_LEFT:
                setCenterX(getCenterX() - speed);
                setCenterY(getCenterY() + speed);
                break;
            case DOWN_RIGHT:
                setCenterX(getCenterX() + speed);
                setCenterY(getCenterY() + speed);
        }
    }
}
