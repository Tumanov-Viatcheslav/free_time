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

    private final BooleanProperty lost = new SimpleBooleanProperty();

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
        direction = BallDirection.values()[(BallDirection.values().length + direction.ordinal() - 1) % BallDirection.values().length];
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

    public void hasStarted() {
        lost.setValue(false);
    }

    public void hasLost() {
        lost.setValue(true);
    }

    public BooleanProperty lostProperty() {
        return lost;
    }

    private double getNewX(double oldX) {
        double newX = 0;
        switch (direction) {
            case UP_LEFT:
            case DOWN_LEFT:
                newX = oldX - speed;
                break;
            case UP_RIGHT:
            case DOWN_RIGHT:
                newX = oldX + speed;
        }
        return newX;
    }

    private double getNewY(double oldY) {
        double newY = 0;
        switch (direction) {
            case UP_RIGHT:
            case UP_LEFT:
                newY = oldY - speed;
                break;
            case DOWN_LEFT:
            case DOWN_RIGHT:
                newY = oldY + speed;
        }
        return newY;
    }

    /**
     * returns  true if collided with border
     *          false if not collided with border
     */
    private void handleBorderCollision() {
        double oldX = getCenterX(), oldY = getCenterY(), newX = getNewX(oldX), newY = getNewY(oldY);

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

        // Check collisions in corner
        // -2 Upper border collision first
        // -1 Left border collision first
        // 1 Right border collision first
        // 2 Bottom border collision first
        int borderIndicator;

        if (collidedUpperBorder && collidedLeftBorder) {
            borderIndicator = newY - border.getY() < newX - border.getX() ? 2 : -1;
            if (borderIndicator == 2) {
                moveTo(getCenterX() - (getCenterY() - border.getY() - getRadius()), border.getY() + getRadius());
                turnLeft();
            }
            if (borderIndicator == -1) {
                moveTo(border.getX() + getRadius(), getCenterY() - (getCenterX() - border.getX() - getRadius()));
                turnRight();
            }
        }
        if (collidedUpperBorder && collidedRightBorder) {
            borderIndicator = newY - border.getY() < newX - border.getX() - border.getWidth() ? 2 : 1;
            if (borderIndicator == 2) {
                moveTo(getCenterX() + (getCenterY() - border.getY() - getRadius()), border.getY() + getRadius());
                turnRight();
            }
            if (borderIndicator == 1) {
                moveTo(border.getWidth() - getRadius(), getCenterY() - (border.getWidth() - getCenterX() - getRadius()));
                turnLeft();
            }
        }
        if (collidedBottomBorder && collidedLeftBorder) {
            borderIndicator = newY - border.getY() < newX - border.getX() ? -2 : -1;
            if (borderIndicator == -2) {
                moveTo(getCenterX() - (border.getHeight() - getCenterY() - getRadius()), border.getHeight() - getRadius());
                turnRight();
                stopAnimation();
                hasLost();
            }
            if (borderIndicator == -1) {
                moveTo(border.getX()  + getRadius(), getCenterY() + (getCenterX() - border.getX() - getRadius()));
                turnLeft();
            }
        }
        if (collidedBottomBorder && collidedRightBorder){
            borderIndicator = newY - border.getY() < newX - border.getX() ? -2 : 1;
            if (borderIndicator == -2) {
                moveTo(getCenterX() + (border.getHeight() - getCenterY() - getRadius()), border.getHeight() - getRadius());
                turnLeft();
            }
            if (borderIndicator == 1) {
                moveTo(border.getWidth() - getRadius(), getCenterY() + (border.getWidth() - getCenterX() - getRadius()));
                turnRight();
                stopAnimation();
                hasLost();
            }
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
        }
    }

    /**
     * returns  true if collided with border
     *          false if not collided with border
     */
    private void handlePlatformCollision() {
        // TODO handle collision
        double oldX = getCenterX(), oldY = getCenterY(), newX = getNewX(oldX), newY = getNewY(oldY), max = -1,
                k = (newY -oldY) / (newX - oldX), c = oldY - k * oldX,
                platformUpBorder = platform.getY(), platformLeftBorder = platform.getX(), platformRightBorder = platform.getX() + platform.getWidth();

        //Upper platform border
        double xIntersectionCoordinate = (platformUpBorder - c) / k;
        if ((xIntersectionCoordinate >= platform.getX() - getRadius() && xIntersectionCoordinate <= platform.getX() + platform.getWidth() + getRadius()) &&
                (newY > platformUpBorder - getRadius())
        ) {
            moveTo((platformUpBorder - getRadius() - c) / k, platformUpBorder - getRadius());
            if (k > 0)
                turnLeft();
            else turnRight();
        }
    }

    /**
     * returns  true if collided with border
     *          false if not collided with border
     */
    private void handleBricksCollision(int brickIndex) {
        // TODO handle collision
    }

    /**
     * @return -1 if there is no collision
     *          d - distance to border if the is collision on movement execution
     */
    private double getBorderCollisionDistance() {
        double oldX = getCenterX(), oldY = getCenterY(), newX = getNewX(oldX), newY = getNewY(oldY), max = -1;

        if (newY < border.getY() + getRadius())
            max = Math.max(max, oldY - border.getY() - getRadius());
        if (newX < border.getX() + getRadius())
            max = Math.max(max, oldX - border.getX() - getRadius());
        if (newY > border.getHeight() - getRadius())
            max = Math.max(max, border.getHeight() - getRadius() - oldY);
        if (newX > border.getWidth() - getRadius())
            max = Math.max(max, border.getWidth() - getRadius() - oldX);

        return max;
    }

    /**
     * @return -1 if there is no collision
     *          d - distance to platform if the is collision on movement execution
     */
    private double getPlatformCollisionDistance() {
        // TODO calculate distance
        double oldX = getCenterX(), oldY = getCenterY(), newX = getNewX(oldX), newY = getNewY(oldY), max = -1,
                k = (newY - oldY) / (newX - oldX), c = oldY - k * oldX,
                platformUpBorder = platform.getY(), platformLeftBorder = platform.getX(), platformRightBorder = platform.getX() + platform.getWidth();

        //Upper platform border
        double xIntersectionCoordinate = (platformUpBorder - getRadius() - c) / k;
        if ((xIntersectionCoordinate >= platform.getX() - getRadius() && xIntersectionCoordinate <= platform.getX() + platform.getWidth() + getRadius()) &&
                (newY > platformUpBorder - getRadius())
        )
            return platformUpBorder - oldY - getRadius();
        //Left platform border
        //Right platform border


        return max;
    }

    /**
     * @return -1 if there is no collision
     *          nearestBrickIndex if there is collision
     */
    private int getNearestBrickIndex() {
        // TODO bricks
        return -1;
    }

    /**
     * @return -1 if there is no collision
     *          d - distance to brick if the is collision on movement execution
     */
    private double getBrickCollisionDistance(int brickIndex) {
        return -1;
    }

    /**
     * @return true if there is collision
     *         false if there is no collision
     */
    private boolean collided() {
        boolean collidedBorder = false,
                collidedPlatform = false,
                collidedBrick = false;
        int brickIndex = getNearestBrickIndex();
        double distanceToBorder = getBorderCollisionDistance(),
                distanceToPlatform = getPlatformCollisionDistance(),
                distanceToNearestBrick = getBrickCollisionDistance(brickIndex);

        if (distanceToBorder != -1)
            collidedBorder = true;
        if (distanceToPlatform != -1)
            collidedPlatform = true;
        if (distanceToNearestBrick != -1)
            collidedBrick = true;

        distanceToBorder = distanceToBorder == -1 ? Double.MAX_VALUE : distanceToBorder;
        distanceToPlatform = distanceToPlatform == -1 ? Double.MAX_VALUE : distanceToPlatform;
        distanceToNearestBrick = distanceToNearestBrick == -1 ? Double.MAX_VALUE : distanceToNearestBrick;

        if (distanceToBorder < distanceToPlatform && distanceToBorder < distanceToNearestBrick)
            handleBorderCollision();
        else if (distanceToPlatform < distanceToNearestBrick)
            handlePlatformCollision();
        else handleBricksCollision(brickIndex);

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
