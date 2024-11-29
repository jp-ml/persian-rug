package com.persianrug.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import com.persianrug.utils.Constants;

public class Menu {
    private String[] options;
    private int selectedOption = 0;
    private boolean isMainMenu = true;

    public Menu() {
        setMainMenuOptions();
    }

    public void setMainMenuOptions() {
        options = new String[]{"New Game", "Load Game", "Exit"};
        isMainMenu = true;
    }

    public void setPauseMenuOptions() {
        options = new String[]{"Resume", "Save Game", "Back to Menu"};
        isMainMenu = false;
    }

    public void moveUp() {
        selectedOption = (selectedOption - 1 + options.length) % options.length;
    }

    public void moveDown() {
        selectedOption = (selectedOption + 1) % options.length;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(new Color(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial Bold", 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(isMainMenu ? "Persian Rug" : "Paused",
                (double) Constants.WINDOW_WIDTH / 2, (double) Constants.WINDOW_HEIGHT / 3);

        gc.setFont(new Font("Arial", 24));
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.YELLOW);
            } else {
                gc.setFill(Color.WHITE);
            }
            gc.fillText(options[i],
                    (double) Constants.WINDOW_WIDTH / 2,
                    (double) Constants.WINDOW_HEIGHT / 2 + i * 50);
        }

        gc.setFill(Color.LIGHTGRAY);
        gc.setFont(new Font("Arial", 16));
        gc.fillText("Use UP/DOWN arrows to select and ENTER to confirm",
                (double) Constants.WINDOW_WIDTH / 2, (double) (Constants.WINDOW_HEIGHT * 3) / 4);
    }
}