package org.example.arkanoid.source;

import javafx.animation.AnimationTimer;

public class BallAnimation extends AnimationTimer {
    Ball ball;
    int fps;
    private long previousFrameTime = 0;

    public BallAnimation(Ball ball, int fps) {
        this.ball = ball;
        this.fps = fps;
    }

    public int getFPS() {
        return fps;
    }

    @Override
    public void handle(long now) {
        int passedFrames = (int)((now - previousFrameTime) / (1_000_000_000 / fps));
        if (previousFrameTime == 0) {
            doHandle();
            previousFrameTime = now;
            return;
        }
        if (passedFrames > 0) {
            for (int i = 0; i < passedFrames; i++) {
                doHandle();
            }
            previousFrameTime = now;
        }
    }

    private void doHandle() {
        ball.move();
    }
}
