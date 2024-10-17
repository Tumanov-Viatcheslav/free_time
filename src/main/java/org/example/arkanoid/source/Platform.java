package org.example.arkanoid.source;

import org.example.arkanoid.source.baseShapes.BaseRectangle;

public class Platform extends BaseRectangle {
    // TODO try add speed parameter (-speed, 0, +speed for direction) and move it in animation
    // maybe final SPEED and enum PlatformDirection {LEFT, ZERO, RIGHT}
    // [<-/A] - LEFT
    // [->/D] - RIGHT
    // [EMPTY] || [<-/A]+[->/D] - ZERO

    public Platform() {
        setWidth(50);
        setHeight(10);
    }
}
