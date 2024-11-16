package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item extends GameObject {
    private Image itemImage;
    private boolean collected = false;
    private Quiz quiz;

    public Item(double x, double y, String imagePath, Quiz quiz) {
        super(x, y, 50, 50);
        this.quiz = quiz;
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        try {
            itemImage = new Image(getClass().getResource(imagePath).toString());
            System.out.println("Item image loaded successfully: " + imagePath);
        } catch (Exception e) {
            System.err.println("Item image loading failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        // Items don't need updating
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

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}