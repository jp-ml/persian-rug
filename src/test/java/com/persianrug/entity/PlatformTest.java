package com.persianrug.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlatformTest {
    private Platform platform;

    @BeforeEach
    public void setUp() {
        // Initialize the Platform object with test values
        platform = new Platform(100.0, 200.0, 50.0, 10.0);
    }

    @Test
    public void testInitialState() {
        // Verify initial position and dimensions
        assertEquals(100.0, platform.getX(), "Platform X-coordinate should be initialized correctly.");
        assertEquals(200.0, platform.getY(), "Platform Y-coordinate should be initialized correctly.");
        assertEquals(50.0, platform.getWidth(), "Platform width should be initialized correctly.");
        assertEquals(10.0, platform.getHeight(), "Platform height should be initialized correctly.");
    }

    @Test
    public void testUpdateDoesNotChangeState() {
        // Call update and verify that it doesn't alter the platform's state
        double initialX = platform.getX();
        double initialY = platform.getY();
        double initialWidth = platform.getWidth();
        double initialHeight = platform.getHeight();

        platform.update();

        assertEquals(initialX, platform.getX(), "Platform X-coordinate should not change after update.");
        assertEquals(initialY, platform.getY(), "Platform Y-coordinate should not change after update.");
        assertEquals(initialWidth, platform.getWidth(), "Platform width should not change after update.");
        assertEquals(initialHeight, platform.getHeight(), "Platform height should not change after update.");
    }

}
