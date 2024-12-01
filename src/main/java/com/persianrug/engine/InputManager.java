package com.persianrug.engine;

import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages keyboard input for the game.
 * Tracks currently active keys and provides methods to handle key press and release events.
 *
 * @author Juhyun Park
 * @version 2024
 */
public class InputManager {

    /**
     * A set of currently active keys.
     */
    private final Set<KeyCode> activeKeys;

    /**
     * Constructs an InputManager instance.
     */
    public InputManager() {
        activeKeys = new HashSet<>();
    }

    /**
     * Handles a key press event by adding the key to the active keys set.
     *
     * @param code the KeyCode of the key that was pressed
     */
    public void handleKeyPress(final KeyCode code) {
        activeKeys.add(code);
    }

    /**
     * Handles a key release event by removing the key from the active keys set.
     *
     * @param code the KeyCode of the key that was released
     */
    public void handleKeyRelease(final KeyCode code) {
        activeKeys.remove(code);
    }

    /**
     * Checks if a specific key is currently pressed.
     *
     * @param code the KeyCode to check
     * @return {@code true} if the key is currently pressed, otherwise false
     */
    public boolean isKeyPressed(final KeyCode code) {
        return activeKeys.contains(code);
    }
}
