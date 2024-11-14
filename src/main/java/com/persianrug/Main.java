package com.persianrug;

import com.persianrug.engine.GameEngine;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private GameEngine gameEngine;

    @Override
    public void start(Stage primaryStage) {
        gameEngine = new GameEngine(primaryStage);
        gameEngine.start();
        primaryStage.show();
    }

    @Override
    public void stop() {
        gameEngine.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}