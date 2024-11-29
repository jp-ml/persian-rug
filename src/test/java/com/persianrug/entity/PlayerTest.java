package com.persianrug.entity;

import com.persianrug.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
    private Platform platform;

    @BeforeEach
    public void setUp() {
        // Initialize the Player object at a specific position
        player = new Player(100.0, 200.0);

        // Mock Platform object for collision tests
        platform = new Platform(50.0, 250.0, 100.0, 10.0);
    }

    @Test
    public void testInitialState() {
        // Verify initial position and dimensions
        assertEquals(100.0, player.getX(), "Player X-coordinate should be initialized correctly.");
        assertEquals(200.0, player.getY(), "Player Y-coordinate should be initialized correctly.");
        assertEquals(Constants.PLAYER_WIDTH, player.getWidth(), "Player width should be initialized correctly.");
        assertEquals(Constants.PLAYER_HEIGHT, player.getHeight(), "Player height should be initialized correctly.");
    }

    @Test
    public void testMoveLeft() {
        // Move the player left and verify velocity
        player.moveLeft();
        assertEquals(-Constants.PLAYER_SPEED, player.getVelocityX(), "Player velocityX should be negative when moving left.");
    }

    @Test
    public void testMoveRight() {
        // Move the player right and verify velocity
        player.moveRight();
        assertEquals(Constants.PLAYER_SPEED, player.getVelocityX(), "Player velocityX should be positive when moving right.");
    }

    @Test
    public void testJump() {
        // Simulate jumping from the ground
        player.setOnGround(true);
        player.jump();

        assertFalse(player.isOnGround(), "Player should not be on the ground after jumping.");
        assertEquals(Constants.JUMP_FORCE, player.getVelocityY(), "Player velocityY should be set to JUMP_FORCE after jumping.");
        assertTrue(player.canDoubleJump(), "Player should be able to double jump after the first jump.");
    }


    @Test
    public void testCannotDoubleJumpWhenAlreadyDoubleJumping() {
        player.setOnGround(false);
        player.jump(); // First jump
        player.jump(); // Double jump
        double previousVelocityY = player.getVelocityY();

        // Attempt another jump
        player.jump();

        assertEquals(previousVelocityY, player.getVelocityY(), "Player velocityY should not change when already double jumping.");
    }

    @Test
    public void testUpdatePosition() {
        // Simulate movement with velocity
        player.moveRight();
        player.update();

        assertTrue(player.getX() > 100.0, "Player X-coordinate should increase after moving right and updating.");
    }

    @Test
    public void testPlatformCollision() {
        // Simulate falling onto the platform
        player.setY(platform.getY() - player.getHeight()); // Position above the platform
        player.setVelocityY(10.0); // Simulate downward velocity
        player.handlePlatformCollision(platform);

        assertEquals(platform.getY() - player.getHeight(), player.getY(), "Player Y-coordinate should align with the top of the platform after collision.");
        assertEquals(0.0, player.getVelocityY(), "Player velocityY should be 0 after landing on the platform.");
        assertTrue(player.isOnGround(), "Player should be on the ground after landing on the platform.");
    }

    @Test
    public void testBoundaryLeft() {
        // Simulate moving left out of bounds
        player.setX(-10.0);
        player.update();

        assertEquals(0.0, player.getX(), "Player X-coordinate should be 0 when moving out of bounds to the left.");
        assertEquals(0.0, player.getVelocityX(), "Player velocityX should reset to 0 when hitting the left boundary.");
    }

    @Test
    public void testBoundaryRight() {
        // Simulate moving right out of bounds
        player.setX(Constants.LEVEL_WIDTH + 10.0);
        player.update();

        assertEquals(Constants.LEVEL_WIDTH - player.getWidth(), player.getX(), "Player X-coordinate should not exceed the level's width.");
        assertEquals(0.0, player.getVelocityX(), "Player velocityX should reset to 0 when hitting the right boundary.");
    }

    @Test
    public void testGravityEffect() {
        // Simulate falling due to gravity
        double initialY = player.getY();
        player.update();

        assertTrue(player.getY() > initialY, "Player Y-coordinate should increase due to gravity.");
        assertTrue(player.getVelocityY() > 0, "Player velocityY should increase due to gravity.");
    }

}
