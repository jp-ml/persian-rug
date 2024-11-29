package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class Item extends GameObject {
    private Image itemImage;
    private boolean collected = false;
    private final Quiz quiz;

    public Item() {
        super(0, 0, 50, 50);
        this.quiz = null;
    }

    public Item(double x, double y, String imagePath, Quiz quiz) {
        super(x, y, 50, 50);
        this.quiz = quiz;
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        URL resourceUrl = getClass().getResource(imagePath);
        if (resourceUrl == null) {
            throw new RuntimeException("Resource not found: " + imagePath);
        }
        itemImage = new Image(resourceUrl.toString());
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!collected && itemImage != null) {
            gc.drawImage(itemImage, x, y, width, height);
        }
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
        System.out.println("Item collected!");
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}