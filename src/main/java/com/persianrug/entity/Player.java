package com.persianrug.entity;

import com.persianrug.utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;

/**
 * Represents the player character in the game. The player can move, jump,
 * perform a double jump, and interact with platforms.
 * @author Homayoun Khoshi
 * @version 2024
 */
public class Player extends GameObject {
    private double velocityX = 0;
    private double velocityY = 0;
    private boolean onGround = false;
    private boolean canDoubleJump = false;
    private Image characterLeftImage;
    private Image characterRightImage;
    private boolean isFacingRight = true;
    private boolean isDoubleJumping = false;
    private final double waveAmp = 5;
    private double waveOffset = 0;
    private final double waveSpeed = 0.1;

    /**
     * Constructs a Player object with the specified initial position.
     *
     * @param x the initial x-coordinate of the player.
     * @param y the initial y-coordinate of the player.
     */
    public Player(final double x, final double y) {
        super(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        loadCharacterImages();
    }

    /**
     * Loads character images for left and right facing directions.
     * Throws a runtime exception if the images are not found.
     */
    private void loadCharacterImages() {
        try {
            URL leftImageUrl = getClass().getResource("/images/character_left.png");
            URL rightImageUrl = getClass().getResource("/images/character_right.png");

            if (leftImageUrl == null) {
                throw new RuntimeException("Resource not found: /images/character_left.png");
            }
            if (rightImageUrl == null) {
                throw new RuntimeException("Resource not found: /images/character_right.png");
            }

            characterLeftImage = new Image(leftImageUrl.toString());
            characterRightImage = new Image(rightImageUrl.toString());

            System.out.println("Character images loaded successfully");
        } catch (Exception e) {
            System.err.println("Character image loading failed: " + e.getMessage());
        }
    }

    /**
     * Updates the player's position, velocity, and state, including handling
     * gravity, friction, and boundary constraints.
     */
    @Override
    public void update() {
        updatePreviousPosition();

        // Update gravity and velocity
        velocityY += Constants.GRAVITY;
        velocityX *= Constants.FRICTION;

        // Update position
        x += velocityX;
        y += velocityY;

        // Apply wave offset for continuous animation
        waveOffset += waveSpeed;

        if (velocityX > 0) {
            isFacingRight = true;
        } else if (velocityX < 0) {
            isFacingRight = false;
        }

        // Boundary check
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

        System.out.println("onGround: " + onGround + ", canDoubleJump: "
                + canDoubleJump + ", isDoubleJumping: " + isDoubleJumping);
    }

    /**
     * Renders the player with a waving animation effect on the given GraphicsContext.
     *
     * @param gc the GraphicsContext to render the player on.
     */
    @Override
    public void render(final GraphicsContext gc) {
        Image currentImage;
        if (isFacingRight) {
            currentImage = characterRightImage;
        } else {
            currentImage = characterLeftImage;
        }

        if (currentImage != null) {
            double wave = Math.sin(waveOffset) * waveAmp;

            gc.drawImage(currentImage, x, y + wave, width, height);
        }
    }

    /**
     * Causes the player to jump or perform a double jump if possible.
     */
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

    /**
     * Moves the player to the left by setting a negative horizontal velocity.
     */
    public void moveLeft() {
        velocityX = -Constants.PLAYER_SPEED;
    }

    /**
     * Moves the player to the right by setting a positive horizontal velocity.
     */
    public void moveRight() {
        velocityX = Constants.PLAYER_SPEED;
    }

    /**
     * Handles collision between the player and a platform.
     *
     * @param platform the platform to check for collision.
     */
    public void handlePlatformCollision(final Platform platform) {
        double platformTop = platform.getY();
        double platformLeft = platform.getX();
        double platformRight = platform.getX() + platform.getWidth();

        if (velocityY > 0
                && getPreviousY() + height <= platformTop
                && y + height >= platformTop
                && x + width > platformLeft
                && x < platformRight) {
            y = platformTop - height;
            velocityY = 0;
            onGround = true;
            canDoubleJump = false;
            isDoubleJumping = false;
            System.out.println("Landing on platform!");
        }
    }

    /**
     * Sets whether the player is on the ground.
     *
     * @param onGround {@code true} if the player is on the ground, {@code false} otherwise.
     */
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
        if (onGround) {
            canDoubleJump = false;
            isDoubleJumping = false;
        }
    }

    /**
     * Gets the player's current horizontal velocity.
     *
     * @return the horizontal velocity.
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * Gets the player's current vertical velocity.
     *
     * @return the vertical velocity.
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * Checks if the player can perform a double jump.
     *
     * @return {@code true} if the player can double jump, {@code false} otherwise.
     */
    public boolean canDoubleJump() {
        return canDoubleJump;
    }

    /**
     * Checks if the player is on the ground.
     *
     * @return {@code true} if the player is on the ground, {@code false} otherwise.
     */
    public boolean isOnGround() {
        return onGround;
    }

    /**
     * Sets the player's y-coordinate.
     *
     * @param v the new y-coordinate.
     */
    public void setY(final double v) {
        y = v;
    }

    /**
     * Sets the player's vertical velocity.
     *
     * @param v the new vertical velocity.
     */
    public void setVelocityY(final double v) {
        velocityY = v;
    }

    /**
     * Sets the player's x-coordinate.
     *
     * @param v the new x-coordinate.
     */
    public void setX(final double v) {
        x = v;
    }
    /**
     * Checks if two player objects are equal based on the game.
     * @param object the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        Player player = (Player) object;
        return Double.compare(velocityX, player.velocityX) == 0
                && Double.compare(velocityY, player.velocityY) == 0
                && onGround == player.onGround && canDoubleJump == player.canDoubleJump
                && isFacingRight == player.isFacingRight
                && isDoubleJumping == player.isDoubleJumping
                && Double.compare(waveOffset, player.waveOffset) == 0
                && Objects.equals(characterLeftImage, player.characterLeftImage)
                && Objects.equals(characterRightImage, player.characterRightImage);
    }
    /**
     * Generates a hash code based on the player.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), velocityX, velocityY, onGround,
                canDoubleJump, characterLeftImage, characterRightImage, isFacingRight,
                isDoubleJumping, waveAmp, waveOffset, waveSpeed);
    }
    /**
     * Returns a String representation of the player.
     * @return the String representation of the player
     */
    @Override
    public String toString() {
        return "Player{"
                + "velocityX=" + velocityX
                + ", velocityY=" + velocityY
                + ", onGround=" + onGround
                + ", canDoubleJump=" + canDoubleJump
                + ", characterLeftImage=" + characterLeftImage
                + ", characterRightImage=" + characterRightImage
                + ", isFacingRight=" + isFacingRight
                + ", isDoubleJumping=" + isDoubleJumping
                + ", waveAmp=" + waveAmp
                + ", waveOffset=" + waveOffset
                + ", waveSpeed=" + waveSpeed
                + '}';
    }
}
