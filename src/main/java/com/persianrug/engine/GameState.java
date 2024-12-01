package com.persianrug.engine;

/**
 * Represents the various states the game can be in.
 * This enum helps in controlling the game's flow and state transitions.
 *
 * @author Homayoun Khoshi
 * @version 2024
 */
public enum GameState {
    /**
     * Represents the main menu state.
     */
    MENU,

    /**
     * Represents the state where the game is actively being played.
     */
    PLAYING,

    /**
     * Represents the state where the game is paused.
     */
    PAUSED,

    /**
     * Represents the state where the game is saving progress.
     */
    SAVE_GAME
}
