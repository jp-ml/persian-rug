package com.persianrug.engine;

import com.persianrug.entity.Player;
import com.persianrug.entity.Platform;
import com.persianrug.entity.Item;
import com.persianrug.entity.Quiz;
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
    private List<Item> items;
    private Image backgroundImage;
    private GameState gameState;
    private Menu menu;
    private boolean jumpPressed = false;

    // Quiz related fields
    private Item currentQuizItem = null;
    private boolean showingQuiz = false;
    private int selectedOption = 0;
    private boolean showingFailScreen = false;
    private long failStartTime = 0;
    private static final long FAIL_SCREEN_DURATION = 5000;
    private int totalQuestions = 7;
    private int correctAnswers = 0;
    private boolean gameCompleted = false;

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

        // Initialize platforms and items
        platforms = new ArrayList<>();
        items = new ArrayList<>();
        initializePlatforms();
        initializeItems();

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

    private void initializePlatforms() {
        platforms = new ArrayList<>();

        platforms.add(new Platform(0, 7480, Constants.LEVEL_WIDTH, 20));

        platforms.add(new Platform(300, 7000, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(600, 6700, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(900, 6400, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));

        for (int i = 0; i < 6; i++) {
            platforms.add(new Platform(
                    1200 + (i * 400),
                    6200 - (i * 200),
                    Constants.PLATFORM_WIDTH - 30,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 2 == 0) {
                platforms.add(new Platform(
                        1200 + (i * 400) + 200,
                        6200 - (i * 200) + 100,
                        Constants.PLATFORM_WIDTH - 80,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        for (int i = 0; i < 5; i++) {
            platforms.add(new Platform(
                    3600 - (i * 350),
                    5200 - (i * 250),
                    Constants.PLATFORM_WIDTH - 20,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 2 == 0) {
                platforms.add(new Platform(
                        3600 - (i * 350) - 175,
                        5200 - (i * 250) + 125,
                        Constants.PLATFORM_WIDTH - 70,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        for (int i = 0; i < 6; i++) {
            platforms.add(new Platform(
                    2000 + (i * 400),
                    4000 - (i * 200),
                    Constants.PLATFORM_WIDTH - 40,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 2 == 1) {
                platforms.add(new Platform(
                        2000 + (i * 400) + 200,
                        4000 - (i * 200) - 100,
                        Constants.PLATFORM_WIDTH - 90,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        for (int i = 0; i < 5; i++) {
            platforms.add(new Platform(
                    4400 - (i * 380),
                    3000 - (i * 220),
                    Constants.PLATFORM_WIDTH - 35,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 2 == 0) {
                platforms.add(new Platform(
                        4400 - (i * 380) - 190,
                        3000 - (i * 220) + 110,
                        Constants.PLATFORM_WIDTH - 85,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        for (int i = 0; i < 8; i++) {
            double xOffset = (i % 2 == 0) ? -200 : 200;
            platforms.add(new Platform(
                    2800 + xOffset + (i * 300),
                    2400 - (i * 180),
                    Constants.PLATFORM_WIDTH - 45,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 3 == 0) {
                platforms.add(new Platform(
                        2800 + xOffset + (i * 300) + 150,
                        2400 - (i * 180) - 90,
                        Constants.PLATFORM_WIDTH - 100,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        for (int i = 0; i < 6; i++) {
            platforms.add(new Platform(
                    4500 + (i * 350),
                    1800 - (i * 150),
                    Constants.PLATFORM_WIDTH - 50,
                    Constants.PLATFORM_HEIGHT
            ));
        }

        platforms.add(new Platform(6500, 1000, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(6800, 800, Constants.PLATFORM_WIDTH - 40, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(7100, 600, Constants.PLATFORM_WIDTH - 30, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(6800, 500, Constants.PLATFORM_WIDTH - 60, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(6400, 450, Constants.PLATFORM_WIDTH - 70, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(5900, 400, Constants.PLATFORM_WIDTH - 70, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(5300, 380, Constants.PLATFORM_WIDTH - 80, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(4700, 370, Constants.PLATFORM_WIDTH - 80, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(4100, 370, Constants.PLATFORM_WIDTH - 80, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(3500, 360, Constants.PLATFORM_WIDTH - 100, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(2900, 350, Constants.PLATFORM_WIDTH - 100, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(2300, 300, Constants.PLATFORM_WIDTH -100, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(1700, 300, Constants.PLATFORM_WIDTH -110, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(1100, 300, Constants.PLATFORM_WIDTH -110, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(200, 300, Constants.PLATFORM_WIDTH * 2, Constants.PLATFORM_HEIGHT));
    }
    private void initializeItems() {
        Quiz[] quizzes = {
                new Quiz("2 + 2 = ?",
                        new String[]{"3", "4", "5", "6"}, 1),
                new Quiz("What color is the sky?",
                        new String[]{"Red", "Green", "Blue", "Yellow"}, 2),
                new Quiz("Java was created in what year?",
                        new String[]{"1991", "1995", "1999", "2000"}, 1),
                new Quiz("What is the capital of Korea?",
                        new String[]{"Beijing", "Tokyo", "Seoul", "Bangkok"}, 2),
                new Quiz("What is 5 * 5?",
                        new String[]{"20", "25", "30", "35"}, 1),
                new Quiz("Which planet is closest to the Sun?",
                        new String[]{"Earth", "Venus", "Mercury", "Mars"}, 2),
                new Quiz("What is the largest ocean?",
                        new String[]{"Atlantic", "Indian", "Pacific", "Arctic"}, 2)
        };

        items.add(new Item(1300, 6000, "/images/symbol.png", quizzes[0]));
        items.add(new Item(3400, 5000, "/images/symbol.png", quizzes[1]));
        items.add(new Item(2200, 3800, "/images/symbol.png", quizzes[2]));
        items.add(new Item(4200, 2800, "/images/symbol.png", quizzes[3]));
        items.add(new Item(2500, 2000, "/images/symbol.png", quizzes[4]));
        items.add(new Item(3500, 1500, "/images/symbol.png", quizzes[5]));
        items.add(new Item(2500, 500, "/images/symbol.png", quizzes[6]));
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
                    resetGame();
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
        if (showingFailScreen) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - failStartTime >= FAIL_SCREEN_DURATION) {
                showingFailScreen = false;
                gameState = GameState.MENU;
                resetGame();
            }
            return;
        }

        if (!showingQuiz) {
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

            for (Item item : items) {
                if (!item.isCollected() && player.intersects(item)) {
                    currentQuizItem = item;
                    showingQuiz = true;
                    selectedOption = 0;
                    break;
                }
            }

            player.update();
            camera.update(player);
            checkCollisions();
        } else {
            if (inputManager.isKeyPressed(KeyCode.UP) && !jumpPressed) {
                selectedOption = (selectedOption - 1 + 4) % 4;
                jumpPressed = true;
            } else if (inputManager.isKeyPressed(KeyCode.DOWN) && !jumpPressed) {
                selectedOption = (selectedOption + 1) % 4;
                jumpPressed = true;
            } else if (inputManager.isKeyPressed(KeyCode.ENTER) && !jumpPressed) {
                if (currentQuizItem.getQuiz().checkAnswer(selectedOption)) {
                    currentQuizItem.collect();
                    showingQuiz = false;
                    currentQuizItem = null;
                    correctAnswers++;

                    if (correctAnswers == totalQuestions) {
                        gameCompleted = true;
                    }

                } else {
                    showingFailScreen = true;
                    failStartTime = System.currentTimeMillis();
                    showingQuiz = false;
                }
                jumpPressed = true;
            }

            if (!inputManager.isKeyPressed(KeyCode.UP) &&
                    !inputManager.isKeyPressed(KeyCode.DOWN) &&
                    !inputManager.isKeyPressed(KeyCode.SPACE)) {
                jumpPressed = false;
            }
        }
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

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial Bold", 24));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Score: " + correctAnswers + "/" + totalQuestions, 20, 40);

        gc.save();
        gc.translate(-camera.getX(), -camera.getY());

        for (Platform platform : platforms) {
            platform.render(gc);
        }

        for (Item item : items) {
            item.render(gc);
        }

        player.render(gc);

        gc.restore();

        if (showingQuiz && currentQuizItem != null) {
            renderQuiz();
        } else if (showingFailScreen) {
            renderFailScreen();
        }

        if (gameCompleted) {
            renderCompletionMessage();
        }

        if (showingQuiz && currentQuizItem != null) {
            renderQuiz();
        } else if (showingFailScreen) {
            renderFailScreen();
        }
    }

    private void renderQuiz() {
        gc.setFill(new Color(0, 0, 0, 0.7));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 24));
        gc.setTextAlign(TextAlignment.CENTER);

        Quiz quiz = currentQuizItem.getQuiz();
        gc.fillText(quiz.getQuestion(),
                Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2 - 100);

        String[] options = quiz.getOptions();
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.BLACK);
            } else {
                gc.setFill(Color.WHITE);
            }
            gc.fillText((i + 1) + ". " + options[i],
                    Constants.WINDOW_WIDTH/2,
                    Constants.WINDOW_HEIGHT/2 + i * 40);
        }

        gc.setFill(Color.LIGHTGRAY);
        gc.setFont(new Font("Arial", 16));
        gc.fillText("Use UP/DOWN to select, SPACE to answer",
                Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2 + 200);
    }

    private void renderFailScreen() {
        gc.setFill(new Color(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 65));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Failed!", Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);

        // 남은 시간 표시
        gc.setFill(Color.WHITESMOKE);
        gc.setFont(new Font("Arial", 20));
        long remainingTime = (FAIL_SCREEN_DURATION - (System.currentTimeMillis() - failStartTime)) / 1000 + 1;
        gc.fillText("Returning to menu in " + remainingTime + " seconds",
                Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2 + 50);
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

    private void renderCompletionMessage() {
        gc.setFill(new Color(0, 0, 0, 0.7));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setTextAlign(TextAlignment.CENTER);

        gc.setFill(Color.GOLD);
        gc.setFont(new Font("Arial Bold", 72));
        gc.fillText("Congratulations!", Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2 - 40);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 36));
        gc.fillText("You completed all quizzes!", Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2 + 40);

        gc.setFont(new Font("Arial", 24));
        gc.fillText("Press ESC to return to menu", Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2 + 100);
    }

    private void resetGame() {
        showingQuiz = false;
        showingFailScreen = false;
        currentQuizItem = null;
        selectedOption = 0;

        player = new Player(300, Constants.LEVEL_HEIGHT - Constants.PLAYER_HEIGHT - 20);

        items.clear();
        initializeItems();
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }
}