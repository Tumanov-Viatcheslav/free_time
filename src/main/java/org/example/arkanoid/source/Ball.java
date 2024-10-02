package org.example.arkanoid.source;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    private AnimationTimer animationBall;
    private BallDirection direction = BallDirection.UP_RIGHT;
    private double speed;

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
        direction = BallDirection.values()[(direction.ordinal() - 1) % BallDirection.values().length];
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void startAnimation() {
        animationBall.start();
    }

    public void stopAnimation() {
        animationBall.stop();
    }

    public void setAnimation(int fps) {
        this.animationBall = new BallAnimation(this, fps);
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
