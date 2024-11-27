package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected double x, y;
    protected double width, height;
    public double previousX;
    public double previousY;  // 이전 위치 저장용

    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.previousX = x;
        this.previousY = y;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);

    public boolean intersects(GameObject other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

    public void updatePreviousPosition() {
        previousX = x;
        previousY = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}