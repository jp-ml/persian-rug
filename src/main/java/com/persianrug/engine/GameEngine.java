package com.persianrug.engine;

import com.persianrug.entity.Player;
import com.persianrug.utils.Constants;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameEngine {
    private Canvas canvas;
    private GraphicsContext gc;
    private Player player;
    private InputManager inputManager;
    private AnimationTimer gameLoop;

    public GameEngine(Stage stage) {
        initializeGame(stage);
    }

    private void initializeGame(Stage stage) {
        // Create canvas
        canvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Create player
        player = new Player((double) Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - Constants.PLAYER_HEIGHT);

        // Initialize input manager
        inputManager = new InputManager();

        // Create scene and set up input handling
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> inputManager.handleKeyPress(e.getCode()));
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

    private void update() {
        // Handle input
        if (inputManager.isKeyPressed(KeyCode.A)) {
            player.moveLeft();
        }
        if (inputManager.isKeyPressed(KeyCode.D)) {
            player.moveRight();
        }
        if (inputManager.isKeyPressed(KeyCode.W)) {
            player.jump();
        }

        // Update player
        player.update();
    }

    private void render() {
        // Clear canvas
        gc.clearRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        // Render player
        player.render(gc);
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }
}