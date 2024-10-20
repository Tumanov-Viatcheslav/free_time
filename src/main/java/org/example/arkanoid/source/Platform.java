package org.example.arkanoid.source;

import org.example.arkanoid.source.baseShapes.BaseRectangle;

public class Platform extends BaseRectangle {
    private double speed;

    public Platform() {
        setWidth(50);
        setHeight(10);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

//    public void moveDirectionLeft() {
//        switch (direction) {
//            case LEFT:
//                System.out.println("Error: Impossible to move direction any more left");
//                break;
//            case ZERO:
//                direction = PlatformDirection.LEFT;
//                break;
//            case RIGHT:
//                direction = PlatformDirection.ZERO;
//                break;
//            default:
//                System.out.println("HOW??? (moveDirectionLeft)");
//        }
//    }
//
//    public void moveDirectionRight() {
//        switch (direction) {
//            case RIGHT:
//                System.out.println("Error: Impossible to move direction any more left");
//                break;
//            case ZERO:
//                direction = PlatformDirection.RIGHT;
//                break;
//            case LEFT:
//                direction = PlatformDirection.ZERO;
//                break;
//            default:
//                System.out.println("HOW??? (moveDirectionRight)");
//        }
//    }
}
