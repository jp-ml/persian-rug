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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
    private GameState gameState;
    private Menu menu;
    private boolean jumpPressed = false;

    public GameEngine(Stage stage) {
        initializeGame(stage);
    }

    private void initializeGame(Stage stage) {
        // Create canvas
        canvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Create player
        player = new Player(
                300,
                Constants.LEVEL_HEIGHT - Constants.PLAYER_HEIGHT - 20
        );

        // Initialize components
        inputManager = new InputManager();
        camera = new Camera();
        menu = new Menu();
        gameState = GameState.MENU;

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

        scene.setOnKeyPressed(e -> {
            inputManager.handleKeyPress(e.getCode());
            handleKeyPress(e.getCode());
            if (e.getCode() == KeyCode.SPACE && gameState == GameState.PLAYING) {
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

    private void handleKeyPress(KeyCode code) {
        switch (gameState) {
            case MENU:
                handleMenuInput(code);
                break;
            case PLAYING:
                if (code == KeyCode.ESCAPE) {
                    gameState = GameState.PAUSED;
                }
                break;
            case PAUSED:
                if (code == KeyCode.ESCAPE) {
                    gameState = GameState.PLAYING;
                } else if (code == KeyCode.M) {
                    gameState = GameState.MENU;
                }
                break;
        }
    }

    private void handleMenuInput(KeyCode code) {
        switch (code) {
            case UP:
                menu.moveUp();
                break;
            case DOWN:
                menu.moveDown();
                break;
            case ENTER:
                if (menu.getSelectedOption() == 0) {
                    gameState = GameState.PLAYING;
                } else if (menu.getSelectedOption() == 2) {
                    javafx.application.Platform.exit();
                }
                break;
        }
    }

    private void initializePlatforms() {
        platforms = new ArrayList<>();

        platforms.add(new Platform(0, 7480, Constants.LEVEL_WIDTH, 20));

        platforms.add(new Platform(300, 7000, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(600, 6700, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(900, 6400, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));

        double currentX = 1300;
        for(int i = 0; i < 5; i++) {
            platforms.add(new Platform(
                    currentX + (i * 400),
                    6100 - (i * 200),
                    Constants.PLATFORM_WIDTH - 30,
                    Constants.PLATFORM_HEIGHT
            ));
        }

        currentX = 3300;
        for(int i = 0; i < 6; i++) {
            platforms.add(new Platform(
                    currentX - (i * 350),
                    5100 - (i * 250),
                    Constants.PLATFORM_WIDTH - 20,
                    Constants.PLATFORM_HEIGHT
            ));

            if(i % 2 == 0) {
                platforms.add(new Platform(
                        currentX - (i * 350) - 175,
                        5100 - (i * 250) + 125,
                        Constants.PLATFORM_WIDTH - 80,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        for(int i = 0; i < 4; i++) {
            platforms.add(new Platform(
                    1500 + (i * 500),
                    3800 - (i * 200),
                    Constants.PLATFORM_WIDTH - 50,
                    Constants.PLATFORM_HEIGHT
            ));
        }

        for(int i = 0; i < 5; i++) {
            platforms.add(new Platform(
                    3500 + (i * 450),
                    3000 - (i * 180),
                    Constants.PLATFORM_WIDTH - 40,
                    Constants.PLATFORM_HEIGHT
            ));

            if(i % 2 == 1) {
                platforms.add(new Platform(
                        3500 + (i * 450) + 225,
                        3000 - (i * 180) - 90,
                        Constants.PLATFORM_WIDTH - 90, // 매우 작은 플랫폼
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        for(int i = 0; i < 6; i++) {
            double xOffset = (i % 2 == 0) ? 0 : 300;
            platforms.add(new Platform(
                    5800 + xOffset + (i * 400),
                    2200 - (i * 150),
                    Constants.PLATFORM_WIDTH - 60,
                    Constants.PLATFORM_HEIGHT
            ));
        }

        platforms.add(new Platform(8000, 1500, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(8500, 1300, Constants.PLATFORM_WIDTH - 40, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(9000, 1100, Constants.PLATFORM_WIDTH - 30, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(9400, 1000, Constants.PLATFORM_WIDTH * 2, Constants.PLATFORM_HEIGHT));

        addRecoveryPlatforms();
    }

    private void addRecoveryPlatforms() {
        double[][] recoveryPoints = {
                {2000, 5500}, {4000, 4500}, {6000, 3500},
                {3000, 4000}, {5000, 2500}, {7000, 2000},
                {2500, 5000}, {4500, 3500}, {6500, 2500}
        };

        for(double[] point : recoveryPoints) {
            if(Math.random() < 0.7) {
                platforms.add(new Platform(
                        point[0] + (Math.random() * 200 - 100),
                        point[1] + (Math.random() * 200 - 100),
                        Constants.PLATFORM_WIDTH - 70,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }
    }

    private void update() {
        switch (gameState) {
            case PLAYING:
                updateGame();
                break;
            case MENU:
            case PAUSED:
                break;
        }
    }

    private void updateGame() {
        if (inputManager.isKeyPressed(KeyCode.LEFT)) {
            player.moveLeft();
        }
        if (inputManager.isKeyPressed(KeyCode.RIGHT)) {
            player.moveRight();
        }

        if (inputManager.isKeyPressed(KeyCode.UP) && !jumpPressed) {
            player.jump();
            jumpPressed = true;
        }
        if (!inputManager.isKeyPressed(KeyCode.UP)) {
            jumpPressed = false;
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
        gc.clearRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        switch (gameState) {
            case MENU:
                menu.render(gc);
                break;
            case PLAYING:
                renderGame();
                break;
            case PAUSED:
                renderGame();
                renderPauseScreen();
                break;
        }
    }

    private void renderGame() {
        if (backgroundImage != null) {
            double bgX = -camera.getX() * 0.5;
            double bgY = -camera.getY() * 0.3;

            for (double y = bgY % backgroundImage.getHeight();
                 y < Constants.WINDOW_HEIGHT;
                 y += backgroundImage.getHeight()) {

                for (double x = bgX % backgroundImage.getWidth();
                     x < Constants.WINDOW_WIDTH;
                     x += backgroundImage.getWidth()) {

                    gc.drawImage(backgroundImage, x, y,
                            backgroundImage.getWidth(), backgroundImage.getHeight());
                }
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

    private void renderPauseScreen() {
        gc.setFill(new Color(0, 0, 0, 0.5));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 36));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("PAUSED", Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2);
        gc.setFont(new Font("Arial", 18));
        gc.fillText("Press ESC to resume", Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2 + 40);
        gc.fillText("Press M for menu", Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2 + 70);
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }
}