package com.persianrug.engine;

import com.persianrug.entity.Player;
import com.persianrug.utils.Constants;

/**
 * Represents a camera in the game that follows the player.
 * Ensures the camera position stays within the bounds of the level.
 *
 * @author Homayoun Khoshi
 * @version 2024
 */
public class Camera {
    /**
     * The x-coordinate of the camera.
     */
    private double x;

    /**
     * The y-coordinate of the camera.
     */
    private double y;

    /**
     * Constructs a Camera with an initial position at (0, 0).
     */
    public Camera() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Updates the camera's position based on the player's location.
     * The camera ensures the player is centered within the visible window,
     * while keeping the camera's position within level boundaries.
     *
     * @param player the player whose position determines the camera's position
     */
    public void update(final Player player) {
        x = player.getX() - (double) Constants.WINDOW_WIDTH / 2 + player.getWidth() / 2;
        y = player.getY() - (double) Constants.WINDOW_HEIGHT / 2 + player.getHeight() / 2;

        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }

        if (x > Constants.LEVEL_WIDTH - Constants.WINDOW_WIDTH) {
            x = Constants.LEVEL_WIDTH - Constants.WINDOW_WIDTH;
        }
        if (y > Constants.LEVEL_HEIGHT - Constants.WINDOW_HEIGHT) {
            y = Constants.LEVEL_HEIGHT - Constants.WINDOW_HEIGHT;
        }
    }

    /**
     * Gets the x-coordinate of the camera.
     *
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the camera.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }
}
