package com.persianrug.engine;

import com.persianrug.entity.*;
import com.persianrug.utils.Constants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {
    @Test
    void testQuizAnswerValidation() {
        Quiz quiz = new Quiz("Test?", new String[]{"A", "B", "C", "D"}, 0);
        assertTrue(quiz.checkAnswer(0));
        assertFalse(quiz.checkAnswer(1));
    }

    @Test
    void testPlayerMovement() {
        Player player = new Player(100, 100);
        double initialX = player.getX();
        player.moveRight();
        assertTrue(player.getX() <= initialX);
    }

    @Test
    void testPlatformCollision() {
        GameObject obj1 = new Platform(0, 0, 50, 50);
        GameObject obj2 = new Platform(25, 25, 50, 50);
        assertTrue(obj1.intersects(obj2));
    }

    @Test
    void testCameraFollow() {
        Camera camera = new Camera();
        Player player = new Player(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        camera.update(player);
        assertEquals(player.getX() - (double) Constants.WINDOW_WIDTH /2 + player.getWidth()/2, camera.getX());
    }

    @Test
    void testPlayerJump() {
        Player player = new Player(100, 100);
        double initialY = player.getY();
        player.jump();
        assertTrue(player.getY() >= initialY);
    }

    @Test
    void testMenuNavigation() {
        Menu menu = new Menu();
        assertEquals(0, menu.getSelectedOption());
        menu.moveDown();
        assertEquals(1, menu.getSelectedOption());
    }

    @Test
    void testInputManager() {
        InputManager inputManager = new InputManager();
        assertFalse(inputManager.isKeyPressed(javafx.scene.input.KeyCode.SPACE));
        inputManager.handleKeyPress(javafx.scene.input.KeyCode.SPACE);
        assertTrue(inputManager.isKeyPressed(javafx.scene.input.KeyCode.SPACE));
    }

}