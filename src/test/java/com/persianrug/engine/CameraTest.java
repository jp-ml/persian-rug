package com.persianrug.engine;

import com.persianrug.entity.Player;
import com.persianrug.utils.Constants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CameraTest {
    private static final double DELTA = 0.001;
    private final Player player = new Player(300, 400);

    @Test
    void testInitialCameraPosition() {
        Camera camera = new Camera();
        assertEquals(0, camera.getX(), DELTA);
        assertEquals(0, camera.getY(), DELTA);
    }

    @Test
    void testCameraFollowsPlayer() {
        Camera camera = new Camera();
        camera.update(player);
        double expectedX = Math.max(0, Math.min(
                player.getX() - (double) Constants.WINDOW_WIDTH /2 + player.getWidth()/2,
                Constants.LEVEL_WIDTH - Constants.WINDOW_WIDTH
        ));

        double expectedY = Math.max(0, Math.min(
                player.getY() - (double) Constants.WINDOW_HEIGHT /2 + player.getHeight()/2,
                Constants.LEVEL_HEIGHT - Constants.WINDOW_HEIGHT
        ));

        assertEquals(expectedX, camera.getX(), DELTA);
        assertEquals(expectedY, camera.getY(), DELTA);
    }

    @Test
    void testCameraLeftBoundary() {
        Camera camera = new Camera();
        Player leftPlayer = new Player(-100, 400);
        camera.update(leftPlayer);
        assertEquals(0, camera.getX(), DELTA);
    }

    @Test
    void testCameraRightBoundary() {
        Camera camera = new Camera();
        Player rightPlayer = new Player(Constants.LEVEL_WIDTH + 100, 400);
        camera.update(rightPlayer);
        assertEquals(Constants.LEVEL_WIDTH - Constants.WINDOW_WIDTH, camera.getX(), DELTA);
    }

    @Test
    void testCameraTopBoundary() {
        Camera camera = new Camera();
        Player topPlayer = new Player(300, -100);
        camera.update(topPlayer);
        assertEquals(0, camera.getY(), DELTA);
    }

    @Test
    void testCameraBottomBoundary() {
        Camera camera = new Camera();
        Player bottomPlayer = new Player(300, Constants.LEVEL_HEIGHT + 100);
        camera.update(bottomPlayer);
        assertEquals(Constants.LEVEL_HEIGHT - Constants.WINDOW_HEIGHT, camera.getY(), DELTA);
    }
}