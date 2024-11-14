package com.persianrug.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import com.persianrug.utils.Constants;

public class Menu {
    private String[] options = {"Play", "Settings", "Exit"};
    private int selectedOption = 0;

    public void render(GraphicsContext gc) {

        gc.setFill(new Color(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Persian Rug", Constants.WINDOW_WIDTH / 2, 150);

        gc.setFont(new Font("Arial", 24));
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.YELLOW);
            } else {
                gc.setFill(Color.WHITE);
            }
            gc.fillText(options[i], Constants.WINDOW_WIDTH / 2, 300 + i * 50);
        }

        gc.setFont(new Font("Arial", 20));
        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Use UP/DOWN arrows to navigate", Constants.WINDOW_WIDTH / 2, 500);
        gc.fillText("Press ENTER to select", Constants.WINDOW_WIDTH / 2, 530);
    }

    public void moveUp() {
        selectedOption--;
        if (selectedOption < 0) {
            selectedOption = options.length - 1;
        }
    }

    public void moveDown() {
        selectedOption++;
        if (selectedOption >= options.length) {
            selectedOption = 0;
        }
    }

    public int getSelectedOption() {
        return selectedOption;
    }
}