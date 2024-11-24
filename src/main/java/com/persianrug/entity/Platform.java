package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Platform extends GameObject {
    private Image platformImage;

    public Platform(double x, double y, double width, double height) {
        super(x, y, width, height);
        loadImage();
    }

    private void loadImage() {
        try {
            platformImage = new Image(getClass().getResource("/images/platform.png").toString());
            System.out.println("Platform image loaded successfully");
        } catch (Exception e) {
            System.err.println("Platform image loading failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        if (platformImage != null) {
            gc.drawImage(platformImage, x, y, width, height);
        } else {
            // Fallback to rectangle if image fails to load
            gc.setFill(javafx.scene.paint.Color.GRAY);
            gc.fillRect(x, y, width, height);
        }
    }
}