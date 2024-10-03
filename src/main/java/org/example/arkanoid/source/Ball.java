package org.example.arkanoid.source;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.Circle;
import org.example.BorderGame;

public class Ball extends Circle {
    private AnimationTimer animationBall;
    private BallDirection direction = BallDirection.UP_RIGHT;
    private double speed;
    private BorderGame border;
    private Platform platform;

    private final BooleanProperty lost = new SimpleBooleanProperty(false);

    public Ball() {
        super(10);
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
        direction = BallDirection.values()[(direction.ordinal() - 1) % BallDirection.values().length];
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setBorder(BorderGame border) {
        this.border = border;
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

    public void hasLost() {
        lost.setValue(true);
    }

    /**
     * returns  -2 if bottom border collision
     *          -1 if left border collision
     *          0 if there is no collision
     *          1 if right border collision
     *          2 if upper border collision
     */
    private int checkBorderCollision() {
        //TODO make movement in case of collision. Return bool
        double newX = 0, newY = 0;
        switch (direction) {
            case UP_RIGHT:
                newX = getCenterX() + speed;
                newY = getCenterY() - speed;
                break;
            case UP_LEFT:
                newX = getCenterX() - speed;
                newY = getCenterY() - speed;
                break;
            case DOWN_LEFT:
                newX = getCenterX() - speed;
                newY = getCenterY() + speed;
                break;
            case DOWN_RIGHT:
                newX = getCenterX() + speed;
                newY = getCenterY() + speed;
        }

        boolean collidedUpperBorder = false,
                collidedLeftBorder = false,
                collidedBottomBorder = false,
                collidedRightBorder = false;

        if (newY < border.getY())
            collidedUpperBorder = true;
        if (newX < border.getX())
            collidedLeftBorder = true;
        if (newY > border.getY() + border.getHeight())
            collidedBottomBorder = true;
        if (newX > border.getX() + border.getWidth())
            collidedRightBorder = true;

        if (collidedUpperBorder && collidedLeftBorder)
            return newY - border.getY() < newX - border.getX() ? 2 : -1;
        if (collidedUpperBorder && collidedRightBorder)
            return newY - border.getY() < newX - border.getX() - border.getWidth() ? 2 : 1;
        if (collidedBottomBorder && collidedLeftBorder)
            return newY - border.getY() < newX - border.getX() ? -2 : -1;
        if (collidedBottomBorder && collidedRightBorder)
            return newY - border.getY() < newX - border.getX() ? -2 : 1;

        return 0;
    }

    /**
     * returns -1 if ball need to turn left
     *          0 if there is no collision
     *          1 if ball need to turn right
     */
    private int collisionHandler() {
        //TODO make movement in case of collision. Return bool
        //TODO If bottom border
        if (true) {
            stopAnimation();
            hasLost();
            return -2;
        }
        return 0;
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
