package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {

    // implementation of GameObject for testing purposes
    static class TestGameObject extends GameObject {
        public TestGameObject(double x, double y, double width, double height) {
            super(x, y, width, height);
        }

        @Override
        public void update() {
            x += 1;
            y += 1;
        }

        @Override
        public void render(GraphicsContext gc) {
        }
    }

    // Test data
    private final TestGameObject gameObject1 = new TestGameObject(10, 20, 50, 50);
    private final TestGameObject overlappingObject = new TestGameObject(50, 70, 30, 30);

    // Constructor Tests
    @Test
    void testConstructorInitializesValues() {
        TestGameObject obj = new TestGameObject(5, 10, 20, 25);
        assertEquals(5, obj.getX());
        assertEquals(10, obj.getY());
        assertEquals(20, obj.getWidth());
        assertEquals(25, obj.getHeight());
        assertEquals(5, obj.getPreviousX());
        assertEquals(10, obj.getPreviousY());
    }

    // Getter Tests
    @Test
    void testGettersReturnCorrectValues() {
        assertEquals(10, gameObject1.getX());
        assertEquals(20, gameObject1.getY());
        assertEquals(50, gameObject1.getWidth());
        assertEquals(50, gameObject1.getHeight());
    }

    // Update Tests
    @Test
    void testUpdateMovesObject() {
        gameObject1.update();
        assertEquals(11, gameObject1.getX());
        assertEquals(21, gameObject1.getY());
    }

    @Test
    void testUpdatePreviousPositionUpdatesCorrectly() {
        gameObject1.update();
        gameObject1.updatePreviousPosition();
        assertEquals(11, gameObject1.getPreviousX());
        assertEquals(21, gameObject1.getPreviousY());
    }

    @Test
    void testIntersectsEdgeCaseNoOverlap() {
        TestGameObject edgeCaseObject = new TestGameObject(60, 80, 10, 10); // Just outside the range
        assertFalse(gameObject1.intersects(edgeCaseObject));
    }

    @Test
    void testIntersectsEdgeCaseJustTouching() {
        TestGameObject touchingObject = new TestGameObject(60, 70, 10, 10); // Exactly at the boundary
        assertTrue(overlappingObject.intersects(touchingObject));
    }


    @Test
    void testZeroWidthHeightDoesNotIntersect() {
        TestGameObject zeroDimensionObject = new TestGameObject(10, 20, 0, 0);
        assertFalse(gameObject1.intersects(zeroDimensionObject));
    }

    @Test
    void testIntersectsWithItself() {
        assertTrue(gameObject1.intersects(gameObject1));
    }
}
