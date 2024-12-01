package com.persianrug.utils;

/**
 * A utility class that holds constant values used throughout the game.
 * These constants include window dimensions, level dimensions, physics parameters,
 * player attributes, and platform dimensions.
 *
 * @author Juhyun Park
 * @version 2024
 */
public final class Constants {

    /**
     * The width of the game window in pixels.
     */
    public static final int WINDOW_WIDTH = 800;

    /**
     * The height of the game window in pixels.
     */
    public static final int WINDOW_HEIGHT = 600;

    /**
     * The width of the game level in pixels.
     */
    public static final double LEVEL_WIDTH = 7500;

    /**
     * The height of the game level in pixels.
     */
    public static final double LEVEL_HEIGHT = 7500;

    /**
     * The horizontal movement speed of the player in pixels per frame.
     */
    public static final double PLAYER_SPEED = 6.5;

    /**
     * The gravitational acceleration applied to the player in pixels per frame squared.
     */
    public static final double GRAVITY = 0.6;

    /**
     * The initial upward force applied when the player jumps, in pixels per frame.
     */
    public static final double JUMP_FORCE = -18.5;

    /**
     * The initial upward force applied when the player performs a double jump, in pixels per frame.
     */
    public static final double DOUBLE_JUMP_FORCE = -17.5;

    /**
     * The friction coefficient applied to the player's horizontal movement.
     */
    public static final double FRICTION = 0.8;

    /**
     * The width of platforms in the game, in pixels.
     */
    public static final double PLATFORM_WIDTH = 180;

    /**
     * The height of platforms in the game, in pixels.
     */
    public static final double PLATFORM_HEIGHT = 25;

    /**
     * The width of the player character in pixels.
     */
    public static final double PLAYER_WIDTH = 40;

    /**
     * The height of the player character in pixels.
     */
    public static final double PLAYER_HEIGHT = 40;
}
