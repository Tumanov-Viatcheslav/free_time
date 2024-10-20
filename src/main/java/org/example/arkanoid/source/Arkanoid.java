package org.example.arkanoid.source;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.example.BorderGame;

import java.util.List;

public class Arkanoid {
    private AnimationTimer gameAnimation;
    private final BooleanProperty lost = new SimpleBooleanProperty();
    // TODO fix minor bug: two keys for left pressed, one is released then second pressed key is ignored (maybe should only leave one control key set)
    private final BooleanProperty leftPressed = new SimpleBooleanProperty();
    private final BooleanProperty rightPressed = new SimpleBooleanProperty();

    private BorderGame border;
    private Platform platform;
    private Ball ball;
    // TODO change sortable by distance with fast elements addition and search
    private List<Brick> bricks = null;

    private boolean ballAnimationStarted = false;

    //==================================================================================================================
    // Getters and setters for game components
    //==================================================================================================================

    public BorderGame getBorder() {
        return border;
    }

    public void setBorder(BorderGame border) {
        this.border = border;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    //==================================================================================================================
    // Move platform methods
    //==================================================================================================================

    public void movePlatformLeft(double step) {
        if (platform.getX() - step > border.getX())
            platform.setX(platform.getX() - step);
        else platform.setX(border.getX());
    }

    public void movePlatformRight(double step) {
        if (platform.getX() + platform.getWidth() + step < border.getWidth())
            platform.setX(platform.getX() + step);
        else platform.setX(border.getWidth() - platform.getWidth());
    }

    /**
     * keysPressed: [<-/A] - LEFT
     * [->/D] - RIGHT
     * [EMPTY] || [<-/A]+[->/D] - STILL
     */
    public void movePlatform() {
        if (isLeftPressed() == isRightPressed()) {
            return;
        }
        if (isLeftPressed()) {
            movePlatformLeft(platform.getSpeed());
        }
        else movePlatformRight(platform.getSpeed());
    }

    public boolean isLeftPressed() {
        return leftPressed.get();
    }

    public BooleanProperty leftPressedProperty() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed.get();
    }

    public BooleanProperty rightPressedProperty() {
        return rightPressed;
    }

    //==================================================================================================================
    // Ball moving and collision handle
    //==================================================================================================================

    private double getNewX(double oldX) {
        double newX = 0;
        double speed = ball.getSpeed();
        switch (ball.getDirection()) {
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
        double speed = ball.getSpeed();
        switch (ball.getDirection()) {
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
        double oldX = ball.getCenterX(),
                oldY = ball.getCenterY(),
                newX = getNewX(oldX),
                newY = getNewY(oldY),
                radius = ball.getRadius();

        boolean collidedUpperBorder = false,
                collidedLeftBorder = false,
                collidedBottomBorder = false,
                collidedRightBorder = false;

        BallDirection direction = ball.getDirection();

        if (newY < border.getY() + radius)
            collidedUpperBorder = true;
        if (newX < border.getX() + radius)
            collidedLeftBorder = true;
        if (newY > border.getHeight() - radius)
            collidedBottomBorder = true;
        if (newX > border.getWidth() - radius)
            collidedRightBorder = true;

        // TODO Extract methods for moving to border
        // Check collisions in corner
        // -2 Upper border collision first
        // -1 Left border collision first
        // 1 Right border collision first
        // 2 Bottom border collision first
        int borderIndicator;

        if (collidedUpperBorder && collidedLeftBorder) {
            borderIndicator = newY - border.getY() < newX - border.getX() ? 2 : -1;
            if (borderIndicator == 2) {
                ball.moveTo(oldX - (oldY - border.getY() - radius), border.getY() + radius);
                ball.turnLeft();
            }
            if (borderIndicator == -1) {
                ball.moveTo(border.getX() + radius, oldY - (oldX - border.getX() - radius));
                ball.turnRight();
            }
        }
        if (collidedUpperBorder && collidedRightBorder) {
            borderIndicator = newY - border.getY() < newX - border.getX() - border.getWidth() ? 2 : 1;
            if (borderIndicator == 2) {
                ball.moveTo(oldX + (oldY - border.getY() - radius), border.getY() + radius);
                ball.turnRight();
            }
            if (borderIndicator == 1) {
                ball.moveTo(border.getWidth() - radius, oldY - (border.getWidth() - oldX - radius));
                ball.turnLeft();
            }
        }
        if (collidedBottomBorder && collidedLeftBorder) {
            borderIndicator = newY - border.getY() < newX - border.getX() ? -2 : -1;
            if (borderIndicator == -2) {
                ball.moveTo(oldX - (border.getHeight() - oldY - radius), border.getHeight() - radius);
                ball.turnRight();
                stopAnimation();
                hasLost();
            }
            if (borderIndicator == -1) {
                ball.moveTo(border.getX()  + radius, oldY + (oldX - border.getX() - radius));
                ball.turnLeft();
            }
        }
        if (collidedBottomBorder && collidedRightBorder){
            borderIndicator = newY - border.getY() < newX - border.getX() ? -2 : 1;
            if (borderIndicator == -2) {
                ball.moveTo(oldX + (border.getHeight() - oldY - radius), border.getHeight() - radius);
                ball.turnLeft();
            }
            if (borderIndicator == 1) {
                ball.moveTo(border.getWidth() - radius, oldY + (border.getWidth() - oldX - radius));
                ball.turnRight();
                stopAnimation();
                hasLost();
            }
        }

        // Check single border collision
        if (collidedUpperBorder) {
            if (direction == BallDirection.UP_LEFT) {
                ball.moveTo(oldX - (oldY - (border.getY() + radius)), border.getY() + radius);
                ball.turnLeft();
            }
            if (direction == BallDirection.UP_RIGHT) {
                ball.moveTo(oldX + (oldY - (border.getY() + radius)), border.getY() + radius);
                ball.turnRight();
            }
        }
        if (collidedLeftBorder) {
            if (direction == BallDirection.UP_LEFT) {
                ball.moveTo(border.getX() + radius, oldY - (oldX - (border.getX() + radius)));
                ball.turnRight();
            }
            if (direction == BallDirection.DOWN_LEFT) {
                ball.moveTo(border.getX() + radius, oldY + (oldX - (border.getX() + radius)));
                ball.turnLeft();
            }
        }
        if (collidedBottomBorder) {
            if (direction == BallDirection.DOWN_RIGHT) {
                ball.moveTo(oldX + ((border.getHeight() - radius) - oldY), border.getHeight() - radius);
                ball.turnLeft();
            }
            if (direction == BallDirection.DOWN_LEFT) {
                ball.moveTo(oldX - ((border.getHeight() - radius) - oldY), border.getHeight() - radius);
                ball.turnRight();
            }
            stopAnimation();
            hasLost();
        }
        if (collidedRightBorder) {
            if (direction == BallDirection.UP_RIGHT) {
                ball.moveTo(border.getWidth() - radius, oldY - ((border.getWidth() - radius) - oldX));
                ball.turnLeft();
            }
            if (direction == BallDirection.DOWN_RIGHT) {
                ball.moveTo(border.getWidth() - radius, oldY + ((border.getWidth() - radius) - oldX));
                ball.turnRight();
            }
        }
    }

    /**
     * returns  true if collided with border
     *          false if not collided with border
     */
    private void handlePlatformCollision() {
        double oldX = ball.getCenterX(), oldY = ball.getCenterY(),
                newX = getNewX(oldX), newY = getNewY(oldY),
                radius = ball.getRadius(),
                k = (newY - oldY) / (newX - oldX), c = oldY - k * oldX,
                platformUpBorder = platform.getY(),
                platformLeftBorder = platform.getX(),
                platformRightBorder = platform.getX() + platform.getWidth();

        //Upper platform border
        double xIntersectionCoordinate = (platformUpBorder - c) / k;
        if ((xIntersectionCoordinate >= platform.getX() - radius && xIntersectionCoordinate <= platform.getX() + platform.getWidth() + radius) &&
                (newY > platformUpBorder - radius && oldY < platformUpBorder - radius)
        ) {
            ball.moveTo((platformUpBorder - radius - c) / k, platformUpBorder - radius);
            if (k > 0)
                ball.turnLeft();
            else ball.turnRight();
            return;
        }
        //Left platform border
        double yLeftIntersectionCoordinate = k * (platformLeftBorder - radius) + c;
        if ((yLeftIntersectionCoordinate >= platform.getY() - radius && yLeftIntersectionCoordinate <= platform.getY() + platform.getHeight() + radius) &&
                (newX >= platformLeftBorder - radius && oldX <= platformLeftBorder - radius)
        ) {
            ball.moveTo(platformLeftBorder - radius, k * (platformLeftBorder - radius) + c);
            if (k > 0)
                ball.turnRight();
            else ball.turnLeft();
            return;
        }
        //Right platform border
        double yRightIntersectionCoordinate = k * (platformRightBorder + radius) + c;
        if ((yRightIntersectionCoordinate >= platform.getY() - radius && yRightIntersectionCoordinate <= platform.getY() + platform.getHeight() + radius) &&
                (newX < platformRightBorder + radius && oldX > platformRightBorder + radius)
        ) {
            ball.moveTo(platformRightBorder + radius, k * (platformRightBorder + radius) + c);
            if (k > 0)
                ball.turnRight();
            else ball.turnLeft();
            return;
        }
    }

    /**
     * returns  true if collided with brick
     *          false if not collided with brick
     */
    private void handleBricksCollision(int brickIndex) {
        // TODO handle collision
    }

    /**
     * @return -1 if there is no collision
     *          d - distance to border if the is collision on movement execution
     */
    private double getBorderCollisionDistance() {
        double oldX = ball.getCenterX(), oldY = ball.getCenterY(), newX = getNewX(oldX), newY = getNewY(oldY),
                radius = ball.getRadius(),
                max = -1;

        if (newY < border.getY() + radius)
            max = Math.max(max, oldY - border.getY() - radius);
        if (newX < border.getX() + radius)
            max = Math.max(max, oldX - border.getX() - radius);
        if (newY > border.getHeight() - radius)
            max = Math.max(max, border.getHeight() - radius - oldY);
        if (newX > border.getWidth() - radius)
            max = Math.max(max, border.getWidth() - radius - oldX);

        return max;
    }

    /**
     * @return -1 if there is no collision
     *          d - distance to platform if the is collision on movement execution
     */
    private double getPlatformCollisionDistance() {
        double oldX = ball.getCenterX(), oldY = ball.getCenterY(), newX = getNewX(oldX), newY = getNewY(oldY),
                radius = ball.getRadius(),
                max = -1,
                k = (newY - oldY) / (newX - oldX), c = oldY - k * oldX,
                platformUpBorder = platform.getY(), platformLeftBorder = platform.getX(), platformRightBorder = platform.getX() + platform.getWidth();

        //Upper platform border
        double xIntersectionCoordinate = (platformUpBorder - radius - c) / k;
        if ((xIntersectionCoordinate >= platform.getX() - radius && xIntersectionCoordinate <= platform.getX() + platform.getWidth() + radius) &&
                (newY > platformUpBorder - radius && oldY < platformUpBorder - radius)
        )
            return platformUpBorder - oldY - radius;
        //Left platform border
        double yLeftIntersectionCoordinate = k * (platformLeftBorder - radius) + c;
        if ((yLeftIntersectionCoordinate >= platform.getY() - radius && yLeftIntersectionCoordinate <= platform.getY() + platform.getHeight() + radius) &&
                (newX > platformLeftBorder - radius && oldX < platformLeftBorder - radius)
        )
            return platformLeftBorder - oldX - radius;
        //Right platform border
        double yRightIntersectionCoordinate = k * (platformRightBorder + radius) + c;
        if ((yRightIntersectionCoordinate >= platform.getY() - radius && yRightIntersectionCoordinate <= platform.getY() + platform.getHeight() + radius) &&
                (newX < platformRightBorder + radius && oldX > platformRightBorder + radius)
        )
            return oldX - platformRightBorder - radius;


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

    public void moveBall() {
        if (!ballAnimationStarted)
            return;
        double centerX = ball.getCenterX(), centerY = ball.getCenterY(), speed = ball.getSpeed();
        if (collided())
            return;
        switch (ball.getDirection()) {
            case UP_RIGHT:
                ball.moveTo(centerX + speed, centerY - speed);
                break;
            case UP_LEFT:
                ball.moveTo(centerX - speed, centerY - speed);
                break;
            case DOWN_LEFT:
                ball.moveTo(centerX - speed, centerY + speed);
                break;
            case DOWN_RIGHT:
                ball.moveTo(centerX + speed, centerY + speed);
        }
    }

    //==================================================================================================================
    // Animation related methods
    //==================================================================================================================
    public void startAnimation() {
        gameAnimation.start();
    }

    public void stopAnimation() {
        gameAnimation.stop();
    }

    public void setAnimation(int fps) {
        this.gameAnimation = new GameAnimation(this, fps);
    }

    public void startBallAnimation() {
        lost.setValue(false);
        ballAnimationStarted = true;
    }

    public void hasLost() {
        lost.setValue(true);
        ballAnimationStarted = false;
    }

    public BooleanProperty lostProperty() {
        return lost;
    }
}
