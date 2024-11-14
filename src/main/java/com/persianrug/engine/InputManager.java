package com.persianrug.engine;

import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class InputManager {
    private Set<KeyCode> activeKeys;

    public InputManager() {
        activeKeys = new HashSet<>();
    }

    public void handleKeyPress(KeyCode code) {
        activeKeys.add(code);
    }

    public void handleKeyRelease(KeyCode code) {
        activeKeys.remove(code);
    }

    public boolean isKeyPressed(KeyCode code) {
        return activeKeys.contains(code);
    }
}