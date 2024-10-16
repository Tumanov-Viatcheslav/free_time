package org.example.arkanoid.source;

import javafx.animation.AnimationTimer;

public class BallAnimation extends AnimationTimer {
    // TODO Think if this can be moved somehow
    // TODO Try adding platform animation for smooth movement
    Arkanoid game;
    int fps;
    private long previousFrameTime = 0;

    public BallAnimation(Arkanoid game, int fps) {
        this.game = game;
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
        game.moveBall();
    }
}
