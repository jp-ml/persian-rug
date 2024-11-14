package com.persianrug.engine;

import com.persianrug.entity.Player;
import com.persianrug.entity.Platform;
import com.persianrug.utils.Constants;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private Canvas canvas;
    private GraphicsContext gc;
    private Player player;
    private InputManager inputManager;
    private AnimationTimer gameLoop;
    private Camera camera;
    private List<Platform> platforms;
    private Image backgroundImage;

    public GameEngine(Stage stage) {
        initializeGame(stage);
    }

    private void initializeGame(Stage stage) {
        // Create canvas
        canvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Create player
        player = new Player((double) Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - Constants.PLAYER_HEIGHT);

        // Initialize input manager and camera
        inputManager = new InputManager();
        camera = new Camera();

        // Initialize platforms
        platforms = new ArrayList<>();
        initializePlatforms();

        // Load background image
        try {
            backgroundImage = new Image(getClass().getResource("/images/background.png").toString());
            System.out.println("Background image loaded successfully");
        } catch (Exception e) {
            System.err.println("Background image loading failed: " + e.getMessage());
            e.printStackTrace();
        }

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> inputManager.handleKeyPress(e.getCode()));
        scene.setOnKeyReleased(e -> inputManager.handleKeyRelease(e.getCode()));


        // 키 입력 처리 수정
        scene.setOnKeyPressed(e -> {
            inputManager.handleKeyPress(e.getCode());
            // 스페이스바 입력 확인용 디버그 출력
            if (e.getCode() == KeyCode.SPACE) {
                System.out.println("Space pressed!");
            }
        });
        scene.setOnKeyReleased(e -> inputManager.handleKeyRelease(e.getCode()));

        // Set up stage
        stage.setScene(scene);
        stage.setTitle("Persian Rug");
        stage.setResizable(false);

        // Create game loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
    }

    private void initializePlatforms() {
        platforms = new ArrayList<>();

        platforms.add(new Platform(0, Constants.LEVEL_HEIGHT - 20, Constants.LEVEL_WIDTH, 20));

        platforms.add(new Platform(600, 400, 150, 20));
        platforms.add(new Platform(900, 350, 200, 20));
        platforms.add(new Platform(1200, 350, 150, 20));
        platforms.add(new Platform(1500, 450, 200, 20));
        platforms.add(new Platform(1800, 400, 150, 20));
        platforms.add(new Platform(2100, 300, 200, 20));
        platforms.add(new Platform(2400, 450, 150, 20));
        platforms.add(new Platform(2700, 350, 200, 20));
    }

    private void update() {
        // Handle input
        if (inputManager.isKeyPressed(KeyCode.A)) {
            player.moveLeft();
        }
        if (inputManager.isKeyPressed(KeyCode.D)) {
            player.moveRight();
        }
        if (inputManager.isKeyPressed(KeyCode.SPACE)) {
            player.jump();
        }

        // space bar debugging
        if (inputManager.isKeyPressed(KeyCode.SPACE)) {
            System.out.println("Attempting to jump");
            player.jump();
        }

        player.update();
        camera.update(player);
        checkCollisions();
    }

    private void checkCollisions() {
        player.setOnGround(false);

        if (player.getY() + player.getHeight() >= Constants.LEVEL_HEIGHT - 20) {
            player.setOnGround(true);
        }

        for (Platform platform : platforms) {
            if (player.intersects(platform)) {
                player.handlePlatformCollision(platform);
            }
        }
    }

    private void render() {
        // Clear canvas
        gc.clearRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        // Render background
        if (backgroundImage != null) {
            // Parallax scrolling effect
            double bgX = -camera.getX() * 0.5;

            // Repeat background image
            double currentX = bgX % backgroundImage.getWidth();
            if (currentX > 0) currentX -= backgroundImage.getWidth();

            while (currentX < Constants.WINDOW_WIDTH) {
                gc.drawImage(backgroundImage, currentX, 0,
                        backgroundImage.getWidth(), Constants.WINDOW_HEIGHT);
                currentX += backgroundImage.getWidth();
            }
        }

        gc.save();
        gc.translate(-camera.getX(), -camera.getY());

        for (Platform platform : platforms) {
            platform.render(gc);
        }

        player.render(gc);

        gc.restore();
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }
}