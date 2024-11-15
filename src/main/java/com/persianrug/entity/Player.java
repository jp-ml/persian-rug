package com.persianrug.entity;

import com.persianrug.utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends GameObject {
    private double velocityX = 0;
    private double velocityY = 0;
    private boolean onGround = false;
    private boolean canDoubleJump = false;
    private Image characterImage;
    private boolean isFacingRight = true;
    private boolean isDoubleJumping = false;

    public Player(double x, double y) {
        super(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        loadCharacterImage();
    }

    private void loadCharacterImage() {
        try {
            String imagePath = "/images/character.png";
            characterImage = new Image(getClass().getResource(imagePath).toString());
        } catch (Exception e) {
            System.err.println("Character image loading failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        updatePreviousPosition();

        velocityY += Constants.GRAVITY;
        velocityX *= Constants.FRICTION;

        x += velocityX;
        y += velocityY;

        if (velocityX > 0) {
            isFacingRight = true;
        } else if (velocityX < 0) {
            isFacingRight = false;
        }

        if (x < 0) {
            x = 0;
            velocityX = 0;
        }
        if (x > Constants.LEVEL_WIDTH - width) {
            x = Constants.LEVEL_WIDTH - width;
            velocityX = 0;
        }
        if (y > Constants.LEVEL_HEIGHT - height) {
            y = Constants.LEVEL_HEIGHT - height;
            velocityY = 0;
            onGround = true;
            canDoubleJump = false;
            isDoubleJumping = false;
        }

        System.out.println("onGround: " + onGround + ", canDoubleJump: " + canDoubleJump + ", isDoubleJumping: " + isDoubleJumping);
    }

    public void jump() {
        if (onGround) {
            velocityY = Constants.JUMP_FORCE;
            onGround = false;
            canDoubleJump = true;
            isDoubleJumping = false;
            System.out.println("First Jump!");
        } else if (canDoubleJump && !isDoubleJumping) {
            velocityY = Constants.DOUBLE_JUMP_FORCE;
            canDoubleJump = false;
            isDoubleJumping = true;
            System.out.println("Double Jump!");
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
        }
    }

    public void moveLeft() {
        velocityX = -Constants.PLAYER_SPEED;
    }

    public void moveRight() {
        velocityX = Constants.PLAYER_SPEED;
    }

    public void handlePlatformCollision(Platform platform) {

        double platformTop = platform.getY();
        double platformLeft = platform.getX();
        double platformRight = platform.getX() + platform.getWidth();

        if (velocityY > 0 &&
                previousY + height <= platformTop &&
                y + height >= platformTop &&
                x + width > platformLeft &&
                x < platformRight) {

            y = platformTop - height;
            velocityY = 0;
            onGround = true;
            canDoubleJump = false;
            isDoubleJumping = false;
            System.out.println("Landing on platform!");
        }
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
        if (onGround) {
            canDoubleJump = false;
            isDoubleJumping = false;
        }
    }
}