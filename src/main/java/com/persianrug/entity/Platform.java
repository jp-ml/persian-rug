package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Platform extends GameObject {
    public Platform(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.IVORY);
        gc.fillRect(x, y, width, height);

        gc.setStroke(Color.IVORY);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, width, height);
    }
}