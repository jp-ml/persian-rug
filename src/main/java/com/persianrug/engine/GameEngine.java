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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private GraphicsContext gc;
    private Player player;
    private InputManager inputManager;
    private AnimationTimer gameLoop;
    private Camera camera;
    private List<Platform> platforms;
    private List<Item> items = new ArrayList<>();
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
    private int totalQuestions;
    private int correctAnswers = 0;
    private boolean gameCompleted = false;

    public GameEngine(Stage stage) {
        initializeGame(stage);
    }

    private void initializeGame(Stage stage) {
        Canvas canvas;
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
        URL resourceUrl = getClass().getResource("/images/background.png");
        if (resourceUrl == null) {
            throw new RuntimeException("Resource not found: /images/background.png");
        }
        backgroundImage = new Image(resourceUrl.toString());


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
                    menu.setPauseMenuOptions();
                }
                break;
            case PAUSED:
                handlePauseMenuInput(code);
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
                switch(menu.getSelectedOption()) {
                    case 0:  // New Game
                        gameState = GameState.PLAYING;
                        resetGame();
                        break;
                    case 1:  // Load Game
                        if (GameSaveManager.saveExists()) {
                            loadGame();
                            gameState = GameState.PLAYING;
                        }
                        break;
                    case 2:  // Exit
                        javafx.application.Platform.exit();
                        break;
                }
                break;
        }
    }

    private void handlePauseMenuInput(KeyCode code) {
        switch (code) {
            case UP:
                menu.moveUp();
                break;
            case DOWN:
                menu.moveDown();
                break;
            case ENTER:
                switch(menu.getSelectedOption()) {
                    case 0:  // Resume
                        gameState = GameState.PLAYING;
                        break;
                    case 1:  // Save Game
                        saveGame();
                        break;
                    case 2:  // Back to Menu
                        gameState = GameState.MENU;
                        menu.setMainMenuOptions();
                        resetGame();
                        break;
                }
                break;
        }
    }

    private void saveGame() {
        GameSaveManager.GameSave save = new GameSaveManager.GameSave(
                player.getX(),
                player.getY(),
                correctAnswers,
                items
        );
        GameSaveManager.saveGame(save);
    }

    private void loadGame() {
        GameSaveManager.GameSave save = GameSaveManager.loadGame();
        if (save != null) {
            player = new Player(save.playerX, save.playerY);
            correctAnswers = save.correctAnswers;

            // 아이템 상태 복원
            for (int i = 0; i < save.collectedItems.length && i < items.size(); i++) {
                if (save.collectedItems[i]) {
                    items.get(i).collect();
                }
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

        if (showingFailScreen) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - failStartTime >= FAIL_SCREEN_DURATION) {
                showingFailScreen = false;
                gameState = GameState.MENU;
                menu.setMainMenuOptions();
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
                    !inputManager.isKeyPressed(KeyCode.ENTER)) {
                jumpPressed = false;
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

        gc.setFill(new Color(0, 0, 0, 0.7));
        gc.fillRect(10, 10, 150, 40);
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

        if (correctAnswers == totalQuestions) {
            renderCompletionMessage();
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
                (double) Constants.WINDOW_WIDTH /2, (double) Constants.WINDOW_HEIGHT/2 - 100);

        String[] options = quiz.getOptions();
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.YELLOW);
            } else {
                gc.setFill(Color.WHITE);
            }
            gc.fillText((i + 1) + ". " + options[i],
                    (double) Constants.WINDOW_WIDTH/2,
                    (double) Constants.WINDOW_HEIGHT/2 + i * 40);
        }

        gc.setFill(Color.LIGHTGRAY);
        gc.setFont(new Font("Arial", 16));
        gc.fillText("Use UP/DOWN to select, ENTER to answer",
                (double) Constants.WINDOW_WIDTH/2, (double) Constants.WINDOW_HEIGHT/2 + 200);
    }

    private void renderFailScreen() {
        gc.setFill(new Color(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 70));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Failed!", (double) Constants.WINDOW_WIDTH/2, (double) Constants.WINDOW_HEIGHT/2);

        gc.setFill(Color.WHITESMOKE);
        gc.setFont(new Font("Arial", 20));
        long remainingTime = (FAIL_SCREEN_DURATION - (System.currentTimeMillis() - failStartTime)) / 1000 + 1;
        gc.fillText("Returning to menu in " + remainingTime + " seconds",
                (double) Constants.WINDOW_WIDTH/2, (double) Constants.WINDOW_HEIGHT/2 + 50);
    }

    private void renderPauseScreen() {
        gc.setFill(new Color(0, 0, 0, 0.5));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        menu.render(gc);
    }

    private void renderCompletionMessage() {
        gc.setFill(new Color(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.GOLD);
        gc.setFont(new Font("Arial Bold", 72));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Congratulations!", (double) Constants.WINDOW_WIDTH/2, (double) Constants.WINDOW_HEIGHT/2 - 60);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 36));
        gc.fillText("You've mastered Java OOP!", (double) Constants.WINDOW_WIDTH/2, (double) Constants.WINDOW_HEIGHT/2 + 20);
        gc.fillText("20/20 Correct Answers", (double) Constants.WINDOW_WIDTH/2, (double) Constants.WINDOW_HEIGHT/2 + 70);
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

    private void resetGame() {
        showingQuiz = false;
        showingFailScreen = false;
        currentQuizItem = null;
        selectedOption = 0;
        correctAnswers = 0;
        gameCompleted = false;

        player = new Player(300, Constants.LEVEL_HEIGHT - Constants.PLAYER_HEIGHT - 20);

        items.clear();
        initializeItems();
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
        platforms.add(new Platform(3500, 360, Constants.PLATFORM_WIDTH - 80, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(2900, 350, Constants.PLATFORM_WIDTH - 100, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(2300, 300, Constants.PLATFORM_WIDTH -100, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(1700, 300, Constants.PLATFORM_WIDTH -110, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(1100, 300, Constants.PLATFORM_WIDTH -110, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(200, 300, Constants.PLATFORM_WIDTH * 2, Constants.PLATFORM_HEIGHT));
    }

    private void initializeItems() {
        totalQuestions = 20;

        Quiz[] quizzes = {
                new Quiz("Which keyword is used to inherit a class in Java?",
                        new String[]{"extends", "implements", "inherits", "using"}, 0),
                new Quiz("What type of inheritance is supported by Java?",
                        new String[]{"Multiple", "Single", "Hybrid", "All of above"}, 1),

                new Quiz("Which keyword is used to declare an abstract class?",
                        new String[]{"abstract", "virtual", "sealed", "extends"}, 0),
                new Quiz("Can abstract class have constructor in Java?",
                        new String[]{"Yes", "No", "Depends on JVM", "Only static"}, 0),

                new Quiz("Can an interface contain concrete methods?",
                        new String[]{"No", "Yes, it can", "Only static", "Only final"}, 1),
                new Quiz("Can static methods exist in non-static inner classes?",
                        new String[]{"Yes", "No", "Only if final", "Depends on JVM"}, 1),

                new Quiz("When calling overridden method on a subclass object, which is executed?",
                        new String[]{"Superclass version", "Subclass version", "Both versions", "Random version"}, 1),
                new Quiz("What concept allows to treat a derived class object as base class object?",
                        new String[]{"Inheritance", "Encapsulation", "Upcasting", "Abstraction"}, 2),

                new Quiz("What symbol is used for generic type parameter?",
                        new String[]{"<>", "{}", "[]", "()"}, 0),
                new Quiz("What is the time complexity of selection sort?",
                        new String[]{"O(n)", "O(n log n)", "O(n²)", "O(1)"}, 2),

                new Quiz("What type of Queue allows addition and removal from both ends?",
                        new String[]{"Priority Queue", "Circular Queue", "Deque", "Simple Queue"}, 2),
                new Quiz("Which interface is parent of Set interface?",
                        new String[]{"List", "Collection", "Map", "Queue"}, 1),

                new Quiz("What symbol is used for wildcards in generics?",
                        new String[]{"*", "?", "@", "$"}, 1),
                new Quiz("When can a local inner class access a local variable?",
                        new String[]{"If static", "If final/effectively final", "If private", "If public"}, 1),

                new Quiz("What is ArrayList's search complexity?",
                        new String[]{"O(1)", "O(n)", "O(log n)", "O(n²)"}, 1),
                new Quiz("QuickSort's average complexity is:",
                        new String[]{"O(n)", "O(n log n)", "O(n²)", "O(log n)"}, 1),

                new Quiz("Which collection type doesn't allow duplicates?",
                        new String[]{"ArrayList", "LinkedList", "HashSet", "Vector"}, 2),
                new Quiz("What is the purpose of generics in Java?",
                        new String[]{"Code reusability", "Type safety", "Better performance", "Memory management"}, 1),

                new Quiz("Which collection implementation automatically sorts its elements?",
                        new String[]{"ArrayList", "LinkedList", "PriorityQueue", "Vector"}, 2),
                new Quiz("Who teaches Fall COMP2522? in 2024",
                        new String[]{"Asif", "Brenda", "Alireza", "Chris"}, 3)
        };

        items.add(new Item(1400.0, 6100.0, "/images/symbol.png", quizzes[0]));
        items.add(new Item(2000.0, 5600.0, "/images/symbol.png", quizzes[1]));
        items.add(new Item(2800.0, 5300.0, "/images/symbol.png", quizzes[2]));

        items.add(new Item(3400.0, 5100.0, "/images/symbol.png", quizzes[3]));
        items.add(new Item(3000.0, 4550.0, "/images/symbol.png", quizzes[4]));
        items.add(new Item(2600.0, 4300.0, "/images/symbol.png", quizzes[5]));

        items.add(new Item(2000.0, 3950.0, "/images/symbol.png", quizzes[6]));
        items.add(new Item(2400.0, 3700.0, "/images/symbol.png", quizzes[7]));
        items.add(new Item(2800.0, 3550.0, "/images/symbol.png", quizzes[8]));
        items.add(new Item(3200.0, 3350.0, "/images/symbol.png", quizzes[9]));

        items.add(new Item(6500.0, 950.0, "/images/symbol.png", quizzes[10]));
        items.add(new Item(5300.0, 330.0, "/images/symbol.png", quizzes[11]));
        items.add(new Item(4700.0, 320.0, "/images/symbol.png", quizzes[12]));

        items.add(new Item(4100.0, 320.0, "/images/symbol.png", quizzes[13]));
        items.add(new Item(3500.0, 310.0, "/images/symbol.png", quizzes[14]));
        items.add(new Item(2900.0, 300.0, "/images/symbol.png", quizzes[15]));

        items.add(new Item(2300.0, 250.0, "/images/symbol.png", quizzes[16]));
        items.add(new Item(1700.0, 250.0, "/images/symbol.png", quizzes[17]));
        items.add(new Item(1100.0, 250.0, "/images/symbol.png", quizzes[18]));
        items.add(new Item(200.0, 250.0, "/images/symbol.png", quizzes[19]));
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }
}