package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;

public class Platform extends GameObject {
    private Image platformImage;
    private final boolean isGroundPlatform;

    public Platform(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.isGroundPlatform = (y == 7480);
        if (!isGroundPlatform) {
            loadImage();
        }
    }

    private void loadImage() {
        URL resourceUrl = getClass().getResource("/images/platform.png");
        if (resourceUrl == null) {
            System.err.println("Platform image loading failed: Resource not found at /images/platform.png");
            return;
        }
        try {
            platformImage = new Image(resourceUrl.toString());
        } catch (Exception e) {
            System.err.println("Platform image loading failed: " + e.getMessage());
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isGroundPlatform) {
            gc.setFill(Color.SLATEGRAY);
            gc.fillRect(x, y, width, height);
        } else if (platformImage != null) {
            gc.drawImage(platformImage, x, y, width, height);
        }
    }
}