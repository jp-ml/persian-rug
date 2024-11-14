package com.persianrug.engine;

import com.persianrug.entity.Player;
import com.persianrug.utils.Constants;

public class Camera {
    private double x;
    private double y;

    public Camera() {
        this.x = 0;
        this.y = 0;
    }

    public void update(Player player) {
        x = player.getX() - (double)Constants.WINDOW_WIDTH / 2 + player.getWidth() / 2;

        if (x < 0) {
            x = 0;
        }

        if (x > Constants.LEVEL_WIDTH - Constants.WINDOW_WIDTH) {
            x = Constants.LEVEL_WIDTH - Constants.WINDOW_WIDTH;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}