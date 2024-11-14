package com.persianrug.entity;

import com.persianrug.utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends GameObject {
    private double velocityX = 0;
    private double velocityY = 0;
    private boolean onGround = true;  // 시작할 때 true로 설정
    private Image characterImage;
    private boolean isFacingRight = true;

    public Player(double x, double y) {
        super(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        loadCharacterImage();
        System.out.println("Player created at: " + x + ", " + y); // 디버깅용
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

        // 중력 적용
        velocityY += Constants.GRAVITY;

        // 마찰력 적용
        velocityX *= Constants.FRICTION;

        // 위치 업데이트
        x += velocityX;
        y += velocityY;

        // 이동 방향에 따라 캐릭터 방향 설정
        if (velocityX > 0) {
            isFacingRight = true;
        } else if (velocityX < 0) {
            isFacingRight = false;
        }

        // 맵 경계 체크
        if (x < 0) {
            x = 0;
            velocityX = 0;
        }
        if (x > Constants.LEVEL_WIDTH - width) {
            x = Constants.LEVEL_WIDTH - width;
            velocityX = 0;
        }

        // 바닥 체크
        if (y > Constants.LEVEL_HEIGHT - height - 20) {
            y = Constants.LEVEL_HEIGHT - height - 20;
            velocityY = 0;
            onGround = true;
            System.out.println("On ground!"); // 디버깅용
        }

        // 디버깅 정보 출력
        System.out.println("Position: " + x + ", " + y +
                " Velocity: " + velocityX + ", " + velocityY +
                " OnGround: " + onGround);
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

    public void jump() {
        if (onGround) {
            velocityY = Constants.JUMP_FORCE;
            onGround = false;
            System.out.println("Jump initiated! Velocity Y: " + velocityY); // 디버깅용
        } else {
            System.out.println("Cannot jump - not on ground"); // 디버깅용
        }
    }

    public void handlePlatformCollision(Platform platform) {
        double platformTop = platform.getY();
        double platformLeft = platform.getX();
        double platformRight = platform.getX() + platform.getWidth();

        // 착지 체크
        if (velocityY > 0 &&
                previousY + height <= platformTop &&
                y + height >= platformTop &&
                x + width > platformLeft &&
                x < platformRight) {

            y = platformTop - height;
            velocityY = 0;
            onGround = true;
            System.out.println("Landing on platform!"); // 디버깅용
        }
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}