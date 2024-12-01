package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

/**
 * Represents a base class for all game objects, providing common properties and behaviors
 * such as position, dimensions, and rendering.
 * @author Juhyun Park
 * @version 2024
 */
public abstract class GameObject {
    /**
     * The x-coordinate of the game object.
     */
    protected double x;

    /**
     * The y-coordinate of the game object.
     */
    protected double y;

    /**
     * The width of the game object.
     */
    protected double width;

    /**
     * The height of the game object.
     */
    protected double height;

    /**
     * The previous x-coordinate of the game object, used to track movement.
     */
    private double previousX;

    /**
     * The previous y-coordinate of the game object, used to track movement.
     */
    private double previousY;

    /**
     * Constructs a GameObject with specified position and dimensions.
     *
     * @param x      the initial x-coordinate of the game object.
     * @param y      the initial y-coordinate of the game object.
     * @param width  the width of the game object.
     * @param height the height of the game object.
     */
    public GameObject(final double x, final double y, final double width, final double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.previousX = x;
        this.previousY = y;
    }

    /**
     * Updates the state of the game object.
     * This method is abstract and must be implemented by subclasses.
     */
    public abstract void update();

    /**
     * Renders the game object on the specified GraphicsContext.
     * This method is abstract and must be implemented by subclasses.
     *
     * @param gc the GraphicsContext to render the game object on.
     */
    public abstract void render(GraphicsContext gc);

    /**
     * Determines if this game object intersects with another game object.
     *
     * @param other the other game object to check for intersection.
     * @return {@code true} if the objects intersect, {@code false} otherwise.
     */
    public boolean intersects(final GameObject other) {
        return x < other.x + other.width
                && x + width > other.x
                && y < other.y + other.height
                && y + height > other.y;
    }

    /**
     * Updates the previous position of the game object to the current position.
     */
    public void updatePreviousPosition() {
        previousX = x;
        previousY = y;
    }

    /**
     * Gets the current x-coordinate of the game object.
     *
     * @return the current x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the current y-coordinate of the game object.
     *
     * @return the current y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the width of the game object.
     *
     * @return the width.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the game object.
     *
     * @return the height.
     */
    public double getHeight() {
        return height;
    }
    /**
     * Gets the previous x-coordinate of the game object.
     *
     * @return the previous x-coordinate.
     */
    public double getPreviousX() {
        return previousX;
    }

    /**
     * Gets the previous y-coordinate of the game object.
     *
     * @return the previous y-coordinate.
     */
    public double getPreviousY() {
        return previousY;
    }
    /**
     * Checks if two account objects are equal based on the game.
     * @param object the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        GameObject that = (GameObject) object;
        return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0
                && Double.compare(width, that.width) == 0
                && Double.compare(height, that.height) == 0
                && Double.compare(previousX, that.previousX) == 0
                && Double.compare(previousY, that.previousY) == 0;
    }
    /**
     * Generates a hash code based on the game.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, previousX, previousY);
    }
    /**
     * Returns a String representation of the game object.
     * @return the String representation of the game object
     */
    @Override
    public String toString() {
        return "GameObject{"
                + "x=" + x
                + ", y=" + y
                + ", width=" + width
                + ", height=" + height
                + ", previousX=" + previousX
                + ", previousY=" + previousY
                + '}';
    }
}
