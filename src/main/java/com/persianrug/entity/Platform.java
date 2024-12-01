package com.persianrug.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Objects;

/**
 * Represents a platform within the game.
 * Platforms can either be ground-level platforms or floating platforms with an associated image.
 * @author Homayoun Khoshi
 * @version 2024
 */
public class Platform extends GameObject {
    private Image platformImage; // Image representation of the platform
    private final boolean isGroundPlatform; // Indicates if the platform is at ground level
    private final int platform = 7480; // Y-coordinate considered as ground level

    /**
     * Constructs a Platform object.
     *
     * @param x      the x-coordinate of the platform.
     * @param y      the y-coordinate of the platform.
     * @param width  the width of the platform.
     * @param height the height of the platform.
     */
    public Platform(final double x, final double y, final double width, final double height) {
        super(x, y, width, height);
        this.isGroundPlatform = (y == platform);
        if (!isGroundPlatform) {
            loadImage();
        }
    }

    /**
     * Loads the image resource for the platform if it is not a ground-level platform.
     */
    private void loadImage() {
        URL resourceUrl = getClass().getResource("/images/platform.png");
        if (resourceUrl == null) {
            System.err.println("Platform image loading failed: "
                    + "Resource not found at /images/platform.png");
            return;
        }
        try {
            platformImage = new Image(resourceUrl.toString());
        } catch (Exception e) {
            System.err.println("Platform image loading failed: " + e.getMessage());
        }
    }

    /**
     * Updates the platform state.
     * Currently, the platform is static and does not require state updates.
     */
    @Override
    public void update() {
    }

    /**
     * Renders the platform on the given {@link GraphicsContext}.
     * Ground-level platforms are rendered as a solid color, while floating platforms use an image.
     *
     * @param gc the {@link GraphicsContext} to render the platform on.
     */
    @Override
    public void render(final GraphicsContext gc) {
        if (isGroundPlatform) {
            gc.setFill(Color.SLATEGRAY);
            gc.fillRect(x, y, width, height);
        } else if (platformImage != null) {
            gc.drawImage(platformImage, x, y, width, height);
        }
    }

    /**
     * Checks for equality between this platform and another object.
     *
     * @param object the object to compare against.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
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
        Platform platform1 = (Platform) object;
        return isGroundPlatform == platform1.isGroundPlatform
                && Objects.equals(platformImage, platform1.platformImage);
    }

    /**
     * Computes the hash code for this platform.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), platformImage, isGroundPlatform, platform);
    }

    /**
     * Returns a string representation of the platform.
     *
     * @return a string describing the platform.
     */
    @Override
    public String toString() {
        return "Platform{"
                + "platformImage=" + platformImage
                + ", isGroundPlatform=" + isGroundPlatform
                + ", platform=" + platform
                + '}';
    }
}
