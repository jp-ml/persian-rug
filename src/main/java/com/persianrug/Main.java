package com.persianrug;

import com.persianrug.engine.GameEngine;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entry point for the JavaFX application.
 * This class is final to prevent unintended extension.
 *
 * @author Juhyun Park
 * @version 2024
 */
public final class Main extends Application {

    private GameEngine gameEngine;

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for this application, must not be null
     */
    @Override
    public void start(final Stage primaryStage) {
        gameEngine = new GameEngine(primaryStage);
        gameEngine.start();
        primaryStage.show();
    }

    /**
     * Stops the JavaFX application and cleans up resources.
     */
    @Override
    public void stop() {
        if (gameEngine != null) {
            gameEngine.stop();
        }
    }

    /**
     * The main method serves as the entry point for the application.
     *
     * @param args command-line arguments passed to the application, must not be null
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
