package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * Represents an item in the game that can be collected by the player.
 * Each item is associated with a quiz and has an image representation.
 * @author Juhyun Park
 * @version 2024
 */
public class Item extends GameObject {
    private static final int ITEM_SIZE = 50;
    private Image itemImage;
    private boolean collected = false;
    private final Quiz quiz;

    /**
     * Constructs an Item object with specified position, image, and associated quiz.
     *
     * @param x         the x-coordinate of the item's position.
     * @param y         the y-coordinate of the item's position.
     * @param imagePath the relative path to the image representing the item.
     * @param quiz      the quiz associated with the item.
     */
    public Item(final double x, final double y, final String imagePath, final Quiz quiz) {
        super(x, y, ITEM_SIZE, ITEM_SIZE);
        this.quiz = quiz;
        loadImage(imagePath);
    }

    /**
     * Loads the image for the item from the specified path.
     *
     * @param imagePath the relative path to the image file.
     * @throws RuntimeException if the image resource cannot be found.
     */
    private void loadImage(final String imagePath) {
        URL resourceUrl = getClass().getResource(imagePath);
        if (resourceUrl == null) {
            throw new RuntimeException("Resource not found: " + imagePath);
        }
        itemImage = new Image(resourceUrl.toString());
    }

    /**
     * Updates the item's state. Currently, this method is a placeholder.
     */
    @Override
    public void update() {
    }

    /**
     * Renders the item on the given GraphicsContext if it has not been collected.
     *
     * @param gc the GraphicsContext to render the item on.
     */
    @Override
    public void render(final GraphicsContext gc) {
        if (!collected && itemImage != null) {
            gc.drawImage(itemImage, x, y, width, height);
        }
    }

    /**
     * Checks if the item has been collected.
     *
     * @return {@code true} if the item has been collected, {@code false} otherwise.
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Marks the item as collected and logs the collection event.
     */
    public void collect() {
        this.collected = true;
        System.out.println("Item collected!");
    }

    /**
     * Retrieves the quiz associated with this item.
     *
     * @return the associated Quiz object.
     */
    public Quiz getQuiz() {
        return quiz;
    }
}
