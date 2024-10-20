package org.example.arkanoid.source;

import javafx.animation.AnimationTimer;

public class GameAnimation extends AnimationTimer {
    Arkanoid game;
    int fps;
    private long previousFrameTime = 0;

    public GameAnimation(Arkanoid game, int fps) {
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
        game.movePlatform();
    }
}
