package com.persianrug.entity;

import com.persianrug.utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;

public class Player extends GameObject {
    private double velocityX = 0;
    private double velocityY = 0;
    private boolean onGround = false;
    private Image characterImage;
    private boolean isFacingRight = true;

    public Player(double x, double y) {
        super(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        loadCharacterImage();
    }

    private void loadCharacterImage() {
        try {
            String imagePath = Objects.requireNonNull(
                    getClass().getResource("/images/character.png")).toString();
            characterImage = new Image(imagePath);

            this.width = characterImage.getWidth();
            this.height = characterImage.getHeight();

            System.out.println("Character image loaded successfully");
        } catch (Exception e) {
            System.err.println("Character image loading failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        velocityY += Constants.GRAVITY;

        velocityX *= Constants.FRICTION;

        x += velocityX;
        y += velocityY;

        if (velocityX > 0) {
            isFacingRight = true;
        } else if (velocityX < 0) {
            isFacingRight = false;
        }

        if (y > Constants.WINDOW_HEIGHT - height) {
            y = Constants.WINDOW_HEIGHT - height;
            velocityY = 0;
            onGround = true;
        } else {
            onGround = false;
        }

        if (x < 0) {
            x = 0;
            velocityX = 0;
        }
        if (x > Constants.WINDOW_WIDTH - width) {
            x = Constants.WINDOW_WIDTH - width;
            velocityX = 0;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (characterImage != null) {
            if (isFacingRight) {
                gc.drawImage(characterImage, x, y, width, height);
            } else {
                gc.save();
                gc.translate(x + width, y);
                gc.scale(-1, 1);
                gc.drawImage(characterImage, 0, 0, width, height);
                gc.restore();
            }
        } else {
            gc.setFill(javafx.scene.paint.Color.BLUE);
            gc.fillRect(x, y, width, height);
        }
    }

    public void moveLeft() {
        velocityX = -Constants.PLAYER_SPEED;
    }

    public void moveRight() {
        velocityX = Constants.PLAYER_SPEED;
    }

    public void jump() {
        if (onGround) {
            velocityY = Constants.JUMP_FORCE;
            onGround = false;
        }
    }

    public boolean canJump() {
        return onGround;
    }
}