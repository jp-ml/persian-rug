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

    // Wave parameters for image
    private double waveAmplitude = 5;  // Amplitude of the wave (how much the image moves)
    private double waveSpeed = 0.1;    // Speed of the wave (controls the speed of the up-down motion)
    private double waveOffset = 0;     // Offset to animate the wave

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

        // Update gravity and velocity
        velocityY += Constants.GRAVITY;
        velocityX *= Constants.FRICTION;

        // Update the x and y position for normal gravity and movement (without wave effect)
        x += velocityX;
        y += velocityY;

        // Apply wave offset for continuous wave animation
        waveOffset += waveSpeed;

        if (velocityX > 0) {
            isFacingRight = true;
        } else if (velocityX < 0) {
            isFacingRight = false;
        }

        // Boundary check to prevent going off the screen
        if (x < 0) {
            x = 0;
            velocityX = 0;
        }
        if (x > Constants.LEVEL_WIDTH - width) {
            x = Constants.LEVEL_WIDTH - width;
            velocityX = 0;
        }

        // Handle landing on the ground
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
            // Calculate the wave effect for the image (not the character's position)
            double wave = Math.sin(waveOffset) * waveAmplitude; // Sine wave formula for smooth up and down movement

            // If the character is facing right, render the image normally
            if (isFacingRight) {
                gc.drawImage(characterImage, x, y + wave, width, height);  // Apply the wave only to the image's y-position
            } else {
                // If the character is facing left, flip the image horizontally and apply the wave to the y-position
                gc.save();
                gc.translate(x + width, y + wave);  // Translate to the end of the character and apply the wave to y
                gc.scale(-1, 1);  // Flip horizontally
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
