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
     * returns  true if collided with platform
     *          false if not collided with platform
     */
    private boolean checkPlatformCollision() {
        double newX = 0, newY = 0, oldX = getCenterX(), oldY = getCenterY();
        // TODO extract method to get newX and newY
        switch (direction) {
            case UP_RIGHT:
                newX = oldX + speed;
                newY = oldY - speed;
                break;
            case UP_LEFT:
                newX = oldX - speed;
                newY = oldY - speed;
                break;
            case DOWN_LEFT:
                newX = oldX - speed;
                newY = oldY + speed;
                break;
            case DOWN_RIGHT:
                newX = oldX + speed;
                newY = oldY + speed;
        }
        return false;
    }

    /**
     * returns  true if collided with border
     *          false if not collided with border
     */
    private boolean checkBorderCollision() {
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

        if (newY < border.getY() + getRadius())
            collidedUpperBorder = true;
        if (newX < border.getX() + getRadius())
            collidedLeftBorder = true;
        if (newY > border.getHeight() - getRadius())
            collidedBottomBorder = true;
        if (newX > border.getWidth() - getRadius())
            collidedRightBorder = true;

        int returnValue;

        // TODO check border.getY and border.getHeight here is bug
        // Check collisions in corner
        // -2 Upper border collision first
        // -1 Left border collision first
        // 1 Right border collision first
        // 2 Bottom border collision first
        if (collidedUpperBorder && collidedLeftBorder) {
            returnValue = newY - border.getY() < newX - border.getX() ? 2 : -1;
            if (returnValue == 2) {
                moveTo(getCenterX() - (getCenterY() - border.getY()), border.getY());
                turnLeft();
            }
            if (returnValue == -1) {
                moveTo(border.getX(), getCenterY() - (getCenterX() - border.getX()));
                turnRight();
            }
            return true;
        }
        if (collidedUpperBorder && collidedRightBorder) {
            returnValue = newY - border.getY() < newX - border.getX() - border.getWidth() ? 2 : 1;
            if (returnValue == 2) {
                moveTo(getCenterX() + (getCenterY() - border.getY()), border.getY());
                turnRight();
            }
            if (returnValue == 1) {
                moveTo(border.getWidth(), getCenterY() - (border.getWidth() - getCenterX()));
                turnLeft();
            }
            return true;
        }
        if (collidedBottomBorder && collidedLeftBorder) {
            returnValue = newY - border.getY() < newX - border.getX() ? -2 : -1;
            if (returnValue == -2) {
                moveTo(getCenterX() - (border.getHeight() - getCenterY()), border.getHeight());
                turnRight();
                stopAnimation();
                hasLost();
            }
            if (returnValue == -1) {
                moveTo(border.getX(), getCenterY() + (getCenterX() - border.getX()));
                turnLeft();
            }
            return true;
        }
        if (collidedBottomBorder && collidedRightBorder){
            returnValue = newY - border.getY() < newX - border.getX() ? -2 : 1;
            if (returnValue == -2) {
                moveTo(getCenterX() + (border.getHeight() - getCenterY()), border.getHeight());
                turnLeft();
            }
            if (returnValue == 1) {
                moveTo(border.getWidth(), getCenterY() + (border.getWidth() - getCenterX()));
                turnRight();
                stopAnimation();
                hasLost();
            }
            return true;
        }

        // Check single border collision
        if (collidedUpperBorder) {
            if (direction == BallDirection.UP_LEFT) {
                moveTo(getCenterX() - (getCenterY() - (border.getY() + getRadius())), border.getY() + getRadius());
                turnLeft();
            }
            if (direction == BallDirection.UP_RIGHT) {
                moveTo(getCenterX() + (getCenterY() - (border.getY() + getRadius())), border.getY() + getRadius());
                turnRight();
            }
            // TODO add exceptions on different directions
            return true;
        }
        if (collidedLeftBorder) {
            if (direction == BallDirection.UP_LEFT) {
                moveTo(border.getX() + getRadius(), getCenterY() - (getCenterX() - (border.getX() + getRadius())));
                turnRight();
            }
            if (direction == BallDirection.DOWN_LEFT) {
                moveTo(border.getX() + getRadius(), getCenterY() + (getCenterX() - (border.getX() + getRadius())));
                turnLeft();
            }
            // TODO add exceptions on different directions
            return true;
        }
        if (collidedBottomBorder) {
            if (direction == BallDirection.DOWN_RIGHT) {
                moveTo(getCenterX() + ((border.getHeight() - getRadius()) - getCenterY()), border.getHeight() - getRadius());
                turnLeft();
            }
            if (direction == BallDirection.DOWN_LEFT) {
                moveTo(getCenterX() - ((border.getHeight() - getRadius()) - getCenterY()), border.getHeight() - getRadius());
                turnRight();
            }
            // TODO add exceptions on different directions
            stopAnimation();
            hasLost();
            return true;
        }
        if (collidedRightBorder) {
            if (direction == BallDirection.UP_RIGHT) {
                moveTo(border.getWidth() - getRadius(), getCenterY() - ((border.getWidth() - getRadius()) - getCenterX()));
                turnLeft();
            }
            if (direction == BallDirection.DOWN_RIGHT) {
                moveTo(border.getWidth() - getRadius(), getCenterY() + ((border.getWidth() - getRadius()) - getCenterX()));
                turnRight();
            }
            // TODO add exceptions on different directions
            return true;
        }

        return false;
    }

    /**
     * returns true if there is collision
     *         false if there is no collision
     */
    private boolean collided() {
        //TODO make movement in case of collision. Return bool
        //TODO If bottom border
        boolean collidedBorder = checkBorderCollision(),
                collidedPlatform = false,
                collidedBrick = false;
        return collidedBorder | collidedPlatform | collidedBrick;
    }

    private void moveTo(double x, double y) {
        setCenterX(x);
        setCenterY(y);
    }

    public void move() {
        if (collided())
            return;
        switch (direction) {
            case UP_RIGHT:
                moveTo(getCenterX() + speed, getCenterY() - speed);
                break;
            case UP_LEFT:
                moveTo(getCenterX() - speed, getCenterY() - speed);
                break;
            case DOWN_LEFT:
                moveTo(getCenterX() - speed, getCenterY() + speed);
                break;
            case DOWN_RIGHT:
                moveTo(getCenterX() + speed, getCenterY() + speed);
        }
    }
}
