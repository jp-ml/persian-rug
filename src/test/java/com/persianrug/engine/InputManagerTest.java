package com.persianrug.engine;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputManagerTest {
    private InputManager inputManager;

    @BeforeEach
    void setUp() {
        inputManager = new InputManager();
    }

    @Test
    void testHandleKeyPress() {
        // Simulate pressing the LEFT key
        inputManager.handleKeyPress(KeyCode.LEFT);
        assertTrue(inputManager.isKeyPressed(KeyCode.LEFT), "LEFT key should be active after pressing");

        // Simulate pressing the RIGHT key
        inputManager.handleKeyPress(KeyCode.RIGHT);
        assertTrue(inputManager.isKeyPressed(KeyCode.RIGHT), "RIGHT key should be active after pressing");
    }

    @Test
    void testHandleKeyRelease() {
        // Simulate pressing and releasing the UP key
        inputManager.handleKeyPress(KeyCode.UP);
        assertTrue(inputManager.isKeyPressed(KeyCode.UP), "UP key should be active after pressing");

        inputManager.handleKeyRelease(KeyCode.UP);
        assertFalse(inputManager.isKeyPressed(KeyCode.UP), "UP key should not be active after releasing");
    }

    @Test
    void testMultipleKeys() {
        // Simulate pressing multiple keys
        inputManager.handleKeyPress(KeyCode.W);
        inputManager.handleKeyPress(KeyCode.A);

        assertTrue(inputManager.isKeyPressed(KeyCode.W), "W key should be active");
        assertTrue(inputManager.isKeyPressed(KeyCode.A), "A key should be active");

        // Simulate releasing one of the keys
        inputManager.handleKeyRelease(KeyCode.W);
        assertFalse(inputManager.isKeyPressed(KeyCode.W), "W key should not be active after releasing");
        assertTrue(inputManager.isKeyPressed(KeyCode.A), "A key should still be active");
    }

    @Test
    void testNoKeyPressedInitially() {
        // No keys pressed initially
        assertFalse(inputManager.isKeyPressed(KeyCode.SPACE), "No key should be active initially");
        assertFalse(inputManager.isKeyPressed(KeyCode.ESCAPE), "No key should be active initially");
    }
}
