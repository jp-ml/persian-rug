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

/**
 * Main game engine class that manages game state, rendering, and game loop.
 * Handles player movement, quiz interactions, and game progression.
 *
 * @author Homayoun Khoshi, Juhyun Park
 * @version 2024
 */
public class GameEngine {
    private static final int INITIAL_PLAYER_X = 300;
    private static final int INITIAL_PLAYER_Y_OFFSET = 20;
    private static final String GAME_TITLE = "Persian Rug";
    private static final String BACKGROUND_IMAGE_PATH = "/images/background.png";
    private static final int MENU_NEW_GAME = 0;
    private static final int MENU_LOAD_GAME = 1;
    private static final int MENU_EXIT = 2;
    private static final int PAUSE_RESUME = 0;
    private static final int PAUSE_SAVE = 1;
    private static final int PAUSE_MENU = 2;
    private static final int QUIZ_OPTIONS_COUNT = 4;
    private static final int QUIZ_INITIAL_OPTION = 0;
    private static final double CLEAR_X = 0;
    private static final double CLEAR_Y = 0;
    private static final double PARALLAX_X = 0.5;
    private static final double PARALLAX_Y = 0.3;
    private static final double SCORE_BOX_X = 10;
    private static final double SCORE_BOX_Y = 10;
    private static final double SCORE_BOX_WIDTH = 150;
    private static final double SCORE_BOX_HEIGHT = 40;
    private static final double SCORE_TEXT_X = 20;
    private static final double SCORE_TEXT_Y = 40;
    private static final int SCORE_FONT_SIZE = 24;
    private static final double OVERLAY_OPACITY = 0.7;
    private static final double QUIZ_QUESTION_Y_OFFSET = 100;
    private static final double QUIZ_OPTION_Y_SPACING = 40;
    private static final double QUIZ_INSTRUCTION_Y_OFFSET = 200;
    private static final int QUIZ_FONT_SIZE = 24;
    private static final int INSTRUCTION_FONT_SIZE = 16;
    private static final double FAIL_OVERLAY_OPACITY = 0.8;
    private static final int FAIL_FONT_SIZE = 70;
    private static final int FAIL_MESSAGE_FONT_SIZE = 20;
    private static final double FAIL_MESSAGE_Y_OFFSET = 50;
    private static final int COMPLETION_TITLE_FONT_SIZE = 72;
    private static final int COMPLETION_TEXT_FONT_SIZE = 36;
    private static final double COMPLETION_TITLE_Y_OFFSET = 60;
    private static final double COMPLETION_TEXT_Y_OFFSET = 20;
    private static final double COMPLETION_SCORE_Y_OFFSET = 70;
    private static final double GROUND_OFFSET = 20.0;
    private static final int GROUND_PLATFORM_HEIGHT = 20;
    private static final int GROUND_PLATFORM_Y = 7480;
    private static final int INITIAL_PLATFORM_START_X = 300;
    private static final int INITIAL_PLATFORM_START_Y = 7000;
    private static final int INITIAL_PLATFORM_X_STEP = 300;
    private static final int INITIAL_PLATFORM_Y_STEP = 300;
    private static final int SECTION1_COUNT = 6;
    private static final int SECTION1_START_X = 1200;
    private static final int SECTION1_START_Y = 6200;
    private static final int SECTION1_X_SPACING = 400;
    private static final int SECTION1_Y_SPACING = 200;
    private static final int SECTION1_WIDTH_REDUCTION = 30;
    private static final int SECTION1_SUB_WIDTH_REDUCTION = 80;
    private static final int SECTION1_SUB_X_OFFSET = 200;
    private static final int SECTION1_SUB_Y_OFFSET = 100;
    private static final int SECTION2_COUNT = 5;
    private static final int SECTION2_START_X = 3600;
    private static final int SECTION2_START_Y = 5200;
    private static final int SECTION2_X_SPACING = 350;
    private static final int SECTION2_Y_SPACING = 250;
    private static final int SECTION2_WIDTH_REDUCTION = 20;
    private static final int SECTION2_SUB_WIDTH_REDUCTION = 70;
    private static final int SECTION2_SUB_X_OFFSET = 175;
    private static final int SECTION2_SUB_Y_OFFSET = 125;
    private static final int SECTION3_COUNT = 6;
    private static final int SECTION3_START_X = 2000;
    private static final int SECTION3_START_Y = 4000;
    private static final int SECTION3_X_SPACING = 400;
    private static final int SECTION3_Y_SPACING = 200;
    private static final int SECTION3_WIDTH_REDUCTION = 40;
    private static final int SECTION3_SUB_WIDTH_REDUCTION = 90;
    private static final int SECTION3_SUB_X_OFFSET = 200;
    private static final int SECTION3_SUB_Y_OFFSET = 100;
    private static final int SECTION4_COUNT = 5;
    private static final int SECTION4_START_X = 4400;
    private static final int SECTION4_START_Y = 3000;
    private static final int SECTION4_X_SPACING = 380;
    private static final int SECTION4_Y_SPACING = 220;
    private static final int SECTION4_WIDTH_REDUCTION = 35;
    private static final int SECTION4_SUB_WIDTH_REDUCTION = 85;
    private static final int SECTION4_SUB_X_OFFSET = 190;
    private static final int SECTION4_SUB_Y_OFFSET = 110;
    private static final int SECTION5_COUNT = 8;
    private static final int SECTION5_START_X = 2800;
    private static final int SECTION5_START_Y = 2400;
    private static final int SECTION5_X_SPACING = 300;
    private static final int SECTION5_Y_SPACING = 180;
    private static final int SECTION5_WIDTH_REDUCTION = 45;
    private static final int SECTION5_SUB_WIDTH_REDUCTION = 100;
    private static final int SECTION5_ALTERNATE_OFFSET = 200;
    private static final int SECTION5_SUB_X_OFFSET = 150;
    private static final int SECTION5_SUB_Y_OFFSET = 90;
    private static final int SECTION6_COUNT = 6;
    private static final int SECTION6_START_X = 4500;
    private static final int SECTION6_START_Y = 1800;
    private static final int SECTION6_X_SPACING = 350;
    private static final int SECTION6_Y_SPACING = 150;
    private static final int SECTION6_WIDTH_REDUCTION = 50;
    private static final int FINAL_PLATFORM1_X = 6500;
    private static final int FINAL_PLATFORM1_Y = 1000;
    private static final int FINAL_PLATFORM2_X = 6800;
    private static final int FINAL_PLATFORM2_Y = 800;
    private static final int FINAL_PLATFORM3_X = 7100;
    private static final int FINAL_PLATFORM3_Y = 600;
    private static final int FINAL_PLATFORM4_Y = 500;
    private static final int FINAL_PLATFORM5_X = 6400;
    private static final int FINAL_PLATFORM5_Y = 450;
    private static final int FINAL_PLATFORM6_X = 5900;
    private static final int FINAL_PLATFORM6_Y = 400;
    private static final int FINAL_PLATFORM7_X = 5300;
    private static final int FINAL_PLATFORM7_Y = 380;
    private static final int FINAL_PLATFORM8_X = 4700;
    private static final int FINAL_PLATFORM8_Y = 370;
    private static final int FINAL_PLATFORM9_X = 4100;
    private static final int FINAL_PLATFORM10_X = 3500;
    private static final int FINAL_PLATFORM10_Y = 360;
    private static final int FINAL_PLATFORM11_X = 2900;
    private static final int FINAL_PLATFORM11_Y = 350;
    private static final int FINAL_PLATFORM12_X = 2300;
    private static final int FINAL_PLATFORM12_Y = 300;
    private static final int FINAL_PLATFORM13_X = 1700;
    private static final int FINAL_PLATFORM14_X = 1100;
    private static final int FINAL_PLATFORM15_X = 200;
    private static final int FINAL_WIDTH_MULTIPLIER = 2;
    private static final int FINAL_OFFSET1_X = 30;
    private static final int FINAL_OFFSET2_X = 60;
    private static final int FINAL_OFFSET3_X = 80;
    private static final int FINAL_OFFSET4_X = 100;
    private static final int FINAL_OFFSET5_X = 110;

    /** Graphics context for rendering game elements. */
    private GraphicsContext gc;

    /** Player instance representing the game character. */
    private Player player;

    /** Manages keyboard input states. */
    private InputManager inputManager;

    /** Main game loop timer. */
    private AnimationTimer gameLoop;

    /** Camera that follows the player. */
    private Camera camera;

    /** List of all platforms in the game. */
    private List<Platform> platforms;

    /** List of collectible items. */
    private List<Item> items = new ArrayList<>();

    /** Background image for the game. */
    private Image backgroundImage;

    /** Current state of the game. */
    private GameState gameState;

    /** Menu system for the game. */
    private Menu menu;

    /** Tracks if jump button is currently pressed. */
    private boolean jumpPressed = false;

    // Quiz related fields
    /** Currently active quiz item. */
    private Item currentQuizItem = null;

    /** Indicates if a quiz is being shown. */
    private boolean showingQuiz = false;

    /** Currently selected quiz option. */
    private int selectedOption = 0;

    /** Indicates if failure screen is showing. */
    private boolean showingFailScreen = false;

    /** Time when failure screen started showing. */
    private long failStartTime = 0;

    /** Duration to show failure screen in milliseconds. */
    private static final long FAIL_SCREEN_DURATION = 5000;

    /** Total number of quiz questions in the game. */
    private int totalQuestions;

    /** Number of correctly answered questions. */
    private int correctAnswers = 0;

    /** Indicates if all quizzes are completed. */
    private boolean gameCompleted = false;

    /**
     * Constructs a new GameEngine instance.
     *
     * @param stage The JavaFX stage to render the game on
     */
    public GameEngine(Stage stage) {
        initializeGame(stage);
    }

    /**
     * Initializes the game components including canvas, player, input handling,
     * and game loop.
     *
     * @param stage The JavaFX stage to initialize the game on
     */
    private void initializeGame(Stage stage) {
        Canvas canvas;
        // Create canvas
        canvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Create player
        player = new Player(
                INITIAL_PLAYER_X,
                Constants.LEVEL_HEIGHT - Constants.PLAYER_HEIGHT - INITIAL_PLAYER_Y_OFFSET
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
        URL resourceUrl = getClass().getResource(BACKGROUND_IMAGE_PATH);
        if (resourceUrl == null) {
            throw new RuntimeException("Resource not found: " + BACKGROUND_IMAGE_PATH);
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
        stage.setTitle(GAME_TITLE);
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
    /**
     * Handles key press events based on current game state.
     * Routes input to appropriate handler based on whether game is in menu,
     * playing, or paused state.
     *
     * @param code The key code of the pressed key
     */
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

    /**
     * Handles input specifically for the main menu state.
     * Processes navigation and selection of menu options including
     * new game, load game, and exit.
     *
     * @param code The key code of the pressed key
     */
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
                    case MENU_NEW_GAME:  // New Game
                        gameState = GameState.PLAYING;
                        resetGame();
                        break;
                    case MENU_LOAD_GAME:  // Load Game
                        if (GameSaveManager.saveExists()) {
                            loadGame();
                            gameState = GameState.PLAYING;
                        }
                        break;
                    case MENU_EXIT:  // Exit
                        javafx.application.Platform.exit();
                        break;
                }
                break;
        }
    }

    /**
     * Handles input specifically for the pause menu state.
     * Processes navigation and selection of pause menu options including
     * resume game, save game, and return to main menu.
     *
     * @param code The key code of the pressed key
     */
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
                    case PAUSE_RESUME:  // Resume
                        gameState = GameState.PLAYING;
                        break;
                    case PAUSE_SAVE:  // Save Game
                        saveGame();
                        break;
                    case PAUSE_MENU:  // Back to Menu
                        gameState = GameState.MENU;
                        menu.setMainMenuOptions();
                        resetGame();
                        break;
                }
                break;
        }
    }

    /**
     * Saves the current game state including player position, score, and collected items.
     */
    private void saveGame() {
        GameSaveManager.GameSave save = new GameSaveManager.GameSave(
                player.getX(),
                player.getY(),
                correctAnswers,
                items
        );
        GameSaveManager.saveGame(save);
    }

    /**
     * Loads a previously saved game state and restores player position, score,
     * and collected items.
     */
    private void loadGame() {
        GameSaveManager.GameSave save = GameSaveManager.loadGame();
        if (save != null) {
            player = new Player(save.playerX, save.playerY);
            correctAnswers = save.correctAnswers;

            // Restore item collection states
            for (int i = 0; i < save.collectedItems.length && i < items.size(); i++) {
                if (save.collectedItems[i]) {
                    items.get(i).collect();
                }
            }
        }
    }

    /**
     * Updates game state based on current game mode.
     */
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

    /**
     * Updates game state during active gameplay.
     * Handles player movement, quiz interactions, and collision detection.
     */
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
            handleGameplayInput();
            checkItemCollisions();
            updateGameEntities();
        } else {
            handleQuizInput();
        }
    }

    /**
     * Handles player input during regular gameplay.
     */
    private void handleGameplayInput() {
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
    }

    /**
     * Checks for collisions between player and collectible items.
     */
    private void checkItemCollisions() {
        for (Item item : items) {
            if (!item.isCollected() && player.intersects(item)) {
                currentQuizItem = item;
                showingQuiz = true;
                selectedOption = QUIZ_INITIAL_OPTION;
                break;
            }
        }
    }

    /**
     * Updates positions and states of game entities.
     */
    private void updateGameEntities() {
        player.update();
        camera.update(player);
        checkCollisions();
    }

    /**
     * Handles player input during quiz interactions.
     */
    private void handleQuizInput() {
        if (inputManager.isKeyPressed(KeyCode.UP) && !jumpPressed) {
            selectedOption = (selectedOption - 1 + QUIZ_OPTIONS_COUNT) % QUIZ_OPTIONS_COUNT;
            jumpPressed = true;
        } else if (inputManager.isKeyPressed(KeyCode.DOWN) && !jumpPressed) {
            selectedOption = (selectedOption + 1) % QUIZ_OPTIONS_COUNT;
            jumpPressed = true;
        } else if (inputManager.isKeyPressed(KeyCode.ENTER) && !jumpPressed) {
            processQuizAnswer();
            jumpPressed = true;
        }

        if (!inputManager.isKeyPressed(KeyCode.UP) &&
                !inputManager.isKeyPressed(KeyCode.DOWN) &&
                !inputManager.isKeyPressed(KeyCode.ENTER)) {
            jumpPressed = false;
        }
    }

    /**
     * Processes the player's quiz answer and updates game state accordingly.
     */
    private void processQuizAnswer() {
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
    }

    /**
     * Renders the game based on current game state.
     */
    private void render() {
        gc.clearRect(CLEAR_X, CLEAR_Y, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

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

    /**
     * Renders the main game screen including background, score, platforms, items, and player.
     */
    private void renderGame() {
        if (backgroundImage != null) {
            double bgX = -camera.getX() * PARALLAX_X;
            double bgY = -camera.getY() * PARALLAX_Y;

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

        gc.setFill(new Color(0, 0, 0, OVERLAY_OPACITY));
        gc.fillRect(SCORE_BOX_X, SCORE_BOX_Y, SCORE_BOX_WIDTH, SCORE_BOX_HEIGHT);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial Bold", SCORE_FONT_SIZE));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Score: " + correctAnswers + "/" + totalQuestions, SCORE_TEXT_X, SCORE_TEXT_Y);

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

    /**
     * Renders the quiz interface including question and options.
     */
    private void renderQuiz() {
        gc.setFill(new Color(0, 0, 0, OVERLAY_OPACITY));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", QUIZ_FONT_SIZE));
        gc.setTextAlign(TextAlignment.CENTER);

        Quiz quiz = currentQuizItem.getQuiz();
        gc.fillText(quiz.getQuestion(),
                (double) Constants.WINDOW_WIDTH / 2, (double) Constants.WINDOW_HEIGHT / 2 - QUIZ_QUESTION_Y_OFFSET);

        String[] options = quiz.getOptions();
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.YELLOW);
            } else {
                gc.setFill(Color.WHITE);
            }
            gc.fillText((i + 1) + ". " + options[i],
                    (double) Constants.WINDOW_WIDTH / 2,
                    (double) Constants.WINDOW_HEIGHT / 2 + i * QUIZ_OPTION_Y_SPACING);
        }

        gc.setFill(Color.LIGHTGRAY);
        gc.setFont(new Font("Arial", INSTRUCTION_FONT_SIZE));
        gc.fillText("Use UP/DOWN to select, ENTER to answer",
                (double) Constants.WINDOW_WIDTH / 2, (double) Constants.WINDOW_HEIGHT / 2 + QUIZ_INSTRUCTION_Y_OFFSET);
    }

    /**
     * Renders the failure screen when a quiz is answered incorrectly.
     */
    private void renderFailScreen() {
        gc.setFill(new Color(0, 0, 0, FAIL_OVERLAY_OPACITY));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", FAIL_FONT_SIZE));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Failed!", (double) Constants.WINDOW_WIDTH / 2, (double) Constants.WINDOW_HEIGHT / 2);

        gc.setFill(Color.WHITESMOKE);
        gc.setFont(new Font("Arial", FAIL_MESSAGE_FONT_SIZE));
        long remainingTime = (FAIL_SCREEN_DURATION - (System.currentTimeMillis() - failStartTime)) / 1000 + 1;
        gc.fillText("Returning to menu in " + remainingTime + " seconds",
                (double) Constants.WINDOW_WIDTH / 2, (double) Constants.WINDOW_HEIGHT / 2 + FAIL_MESSAGE_Y_OFFSET);
    }

    /**
     * Renders the pause screen overlay.
     */
    private void renderPauseScreen() {
        gc.setFill(new Color(0, 0, 0, 0.5));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        menu.render(gc);
    }

    /**
     * Renders the completion message when all quizzes are answered correctly.
     */
    private void renderCompletionMessage() {
        gc.setFill(new Color(0, 0, 0, FAIL_OVERLAY_OPACITY));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.GOLD);
        gc.setFont(new Font("Arial Bold", COMPLETION_TITLE_FONT_SIZE));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Congratulations!", (double) Constants.WINDOW_WIDTH / 2,
                (double) Constants.WINDOW_HEIGHT / 2 - COMPLETION_TITLE_Y_OFFSET);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", COMPLETION_TEXT_FONT_SIZE));
        gc.fillText("You've mastered Java OOP!", (double) Constants.WINDOW_WIDTH / 2,
                (double) Constants.WINDOW_HEIGHT / 2 + COMPLETION_TEXT_Y_OFFSET);
        gc.fillText("20/20 Correct Answers", (double) Constants.WINDOW_WIDTH / 2,
                (double) Constants.WINDOW_HEIGHT / 2 + COMPLETION_SCORE_Y_OFFSET);
    }

    /**
     * Checks for collisions between the player and game objects.
     * This includes ground collision and platform collisions.
     */
    private void checkCollisions() {
        player.setOnGround(false);

        // Check ground collision
        if (player.getY() + player.getHeight() >= Constants.LEVEL_HEIGHT - GROUND_OFFSET) {
            player.setOnGround(true);
        }

        // Check platform collisions
        for (Platform platform : platforms) {
            if (player.intersects(platform)) {
                player.handlePlatformCollision(platform);
            }
        }
    }

    /**
     * Resets the game state to its initial conditions.
     * This includes:
     * - Resetting quiz-related flags and counters
     * - Creating a new player at the starting position
     * - Reinitializing collectible items
     */
    private void resetGame() {
        // Reset quiz state
        showingQuiz = false;
        showingFailScreen = false;
        currentQuizItem = null;
        selectedOption = 0;
        correctAnswers = 0;
        gameCompleted = false;

        // Reset player position
        player = new Player(
                INITIAL_PLAYER_X,
                Constants.LEVEL_HEIGHT - Constants.PLAYER_HEIGHT - GROUND_OFFSET
        );

        // Reset items
        items.clear();
        initializeItems();
    }

    /**
     * Initializes all platforms in the game world.
     * Creates a complex layout of platforms for the player to navigate,
     * arranged in multiple sections with varying patterns.
     */
    private void initializePlatforms() {
        platforms = new ArrayList<>();

        // Ground platform
        platforms.add(new Platform(0, GROUND_PLATFORM_Y, Constants.LEVEL_WIDTH, GROUND_PLATFORM_HEIGHT));

        // Initial ascending platforms
        for (int i = 0; i < 3; i++) {
            platforms.add(new Platform(
                    INITIAL_PLATFORM_START_X + (i * INITIAL_PLATFORM_X_STEP),
                    INITIAL_PLATFORM_START_Y - (i * INITIAL_PLATFORM_Y_STEP),
                    Constants.PLATFORM_WIDTH,
                    Constants.PLATFORM_HEIGHT
            ));
        }

        // Section 1: Ascending platforms with alternating sub-platforms
        for (int i = 0; i < SECTION1_COUNT; i++) {
            platforms.add(new Platform(
                    SECTION1_START_X + (i * SECTION1_X_SPACING),
                    SECTION1_START_Y - (i * SECTION1_Y_SPACING),
                    Constants.PLATFORM_WIDTH - SECTION1_WIDTH_REDUCTION,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 2 == 0) {
                platforms.add(new Platform(
                        SECTION1_START_X + (i * SECTION1_X_SPACING) + SECTION1_SUB_X_OFFSET,
                        SECTION1_START_Y - (i * SECTION1_Y_SPACING) + SECTION1_SUB_Y_OFFSET,
                        Constants.PLATFORM_WIDTH - SECTION1_SUB_WIDTH_REDUCTION,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        // Section 2: Descending platforms with alternating sub-platforms
        for (int i = 0; i < SECTION2_COUNT; i++) {
            platforms.add(new Platform(
                    SECTION2_START_X - (i * SECTION2_X_SPACING),
                    SECTION2_START_Y - (i * SECTION2_Y_SPACING),
                    Constants.PLATFORM_WIDTH - SECTION2_WIDTH_REDUCTION,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 2 == 0) {
                platforms.add(new Platform(
                        SECTION2_START_X - (i * SECTION2_X_SPACING) - SECTION2_SUB_X_OFFSET,
                        SECTION2_START_Y - (i * SECTION2_Y_SPACING) + SECTION2_SUB_Y_OFFSET,
                        Constants.PLATFORM_WIDTH - SECTION2_SUB_WIDTH_REDUCTION,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        // Section 3: Ascending platforms with alternating sub-platforms
        for (int i = 0; i < SECTION3_COUNT; i++) {
            platforms.add(new Platform(
                    SECTION3_START_X + (i * SECTION3_X_SPACING),
                    SECTION3_START_Y - (i * SECTION3_Y_SPACING),
                    Constants.PLATFORM_WIDTH - SECTION3_WIDTH_REDUCTION,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 2 == 1) {
                platforms.add(new Platform(
                        SECTION3_START_X + (i * SECTION3_X_SPACING) + SECTION3_SUB_X_OFFSET,
                        SECTION3_START_Y - (i * SECTION3_Y_SPACING) - SECTION3_SUB_Y_OFFSET,
                        Constants.PLATFORM_WIDTH - SECTION3_SUB_WIDTH_REDUCTION,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        // Section 4: Descending platforms with alternating sub-platforms
        for (int i = 0; i < SECTION4_COUNT; i++) {
            platforms.add(new Platform(
                    SECTION4_START_X - (i * SECTION4_X_SPACING),
                    SECTION4_START_Y - (i * SECTION4_Y_SPACING),
                    Constants.PLATFORM_WIDTH - SECTION4_WIDTH_REDUCTION,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 2 == 0) {
                platforms.add(new Platform(
                        SECTION4_START_X - (i * SECTION4_X_SPACING) - SECTION4_SUB_X_OFFSET,
                        SECTION4_START_Y - (i * SECTION4_Y_SPACING) + SECTION4_SUB_Y_OFFSET,
                        Constants.PLATFORM_WIDTH - SECTION4_SUB_WIDTH_REDUCTION,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        // Section 5: Zig-zag platforms with periodic sub-platforms
        for (int i = 0; i < SECTION5_COUNT; i++) {
            double xOffset = (i % 2 == 0) ? -SECTION5_ALTERNATE_OFFSET : SECTION5_ALTERNATE_OFFSET;
            platforms.add(new Platform(
                    SECTION5_START_X + xOffset + (i * SECTION5_X_SPACING),
                    SECTION5_START_Y - (i * SECTION5_Y_SPACING),
                    Constants.PLATFORM_WIDTH - SECTION5_WIDTH_REDUCTION,
                    Constants.PLATFORM_HEIGHT
            ));

            if (i % 3 == 0) {
                platforms.add(new Platform(
                        SECTION5_START_X + xOffset + (i * SECTION5_X_SPACING) + SECTION5_SUB_X_OFFSET,
                        SECTION5_START_Y - (i * SECTION5_Y_SPACING) - SECTION5_SUB_Y_OFFSET,
                        Constants.PLATFORM_WIDTH - SECTION5_SUB_WIDTH_REDUCTION,
                        Constants.PLATFORM_HEIGHT
                ));
            }
        }

        // Section 6: Final ascending platforms
        for (int i = 0; i < SECTION6_COUNT; i++) {
            platforms.add(new Platform(
                    SECTION6_START_X + (i * SECTION6_X_SPACING),
                    SECTION6_START_Y - (i * SECTION6_Y_SPACING),
                    Constants.PLATFORM_WIDTH - SECTION6_WIDTH_REDUCTION,
                    Constants.PLATFORM_HEIGHT
            ));
        }

        // Final platforms
        platforms.add(new Platform(FINAL_PLATFORM1_X, FINAL_PLATFORM1_Y,
                Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(FINAL_PLATFORM2_X, FINAL_PLATFORM2_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET1_X, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(FINAL_PLATFORM3_X, FINAL_PLATFORM3_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET1_X, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(FINAL_PLATFORM2_X, FINAL_PLATFORM4_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET2_X, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(FINAL_PLATFORM5_X, FINAL_PLATFORM5_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET2_X, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(FINAL_PLATFORM6_X, FINAL_PLATFORM6_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET2_X, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(FINAL_PLATFORM7_X, FINAL_PLATFORM7_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET3_X, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(FINAL_PLATFORM8_X, FINAL_PLATFORM8_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET3_X, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(FINAL_PLATFORM9_X, FINAL_PLATFORM8_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET3_X, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(FINAL_PLATFORM10_X, FINAL_PLATFORM10_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET3_X, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(FINAL_PLATFORM11_X, FINAL_PLATFORM11_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET4_X, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(FINAL_PLATFORM12_X, FINAL_PLATFORM12_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET4_X, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(FINAL_PLATFORM13_X, FINAL_PLATFORM12_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET5_X, Constants.PLATFORM_HEIGHT));
        platforms.add(new Platform(FINAL_PLATFORM14_X, FINAL_PLATFORM12_Y,
                Constants.PLATFORM_WIDTH - FINAL_OFFSET5_X, Constants.PLATFORM_HEIGHT));

        platforms.add(new Platform(FINAL_PLATFORM15_X, FINAL_PLATFORM12_Y,
                Constants.PLATFORM_WIDTH * FINAL_WIDTH_MULTIPLIER, Constants.PLATFORM_HEIGHT));
    }

    /**
     * Initializes the items with predefined positions, images, and quiz data.
     * Each item is associated with a quiz question and displayed at specific coordinates.
     */
    private void initializeItems() {
        // Total number of questions in the quiz
        totalQuestions = 20;

        // Define quiz questions with options and correct answers
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
                new Quiz("What concept allows treating a derived class object as base class object?",
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

        // Add items with positions, images, and corresponding quizzes
        final double[][] positions = {
                {1400.0, 6100.0}, {2000.0, 5600.0}, {2800.0, 5300.0}, {3400.0, 5100.0},
                {3000.0, 4550.0}, {2600.0, 4300.0}, {2000.0, 3950.0}, {2400.0, 3700.0},
                {2800.0, 3550.0}, {3200.0, 3350.0}, {6500.0, 950.0}, {5300.0, 330.0},
                {4700.0, 320.0}, {4100.0, 320.0}, {3500.0, 310.0}, {2900.0, 300.0},
                {2300.0, 250.0}, {1700.0, 250.0}, {1100.0, 250.0}, {200.0, 250.0}
        };

        for (int i = 0; i < quizzes.length; i++) {
            items.add(new Item(positions[i][0], positions[i][1], "/images/symbol.png", quizzes[i]));
        }
    }
    /**
     * Starts the game loop animation timer.
     * This begins the main game execution cycle.
     */
    public void start() {
        gameLoop.start();
    }

    /**
     * Stops the game loop animation timer.
     * This halts the game execution cycle.
     */
    public void stop() {
        gameLoop.stop();
    }
}
