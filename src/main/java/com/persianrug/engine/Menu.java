package com.persianrug.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import com.persianrug.utils.Constants;

/**
 * Represents the game's menu system.
 * Provides options for the main menu and pause menu, and renders them on the screen.
 *
 * @author Juhyun Park
 * @version 2024
 */
public class Menu {

    // Constants for rendering
    private static final double MENU_BACKGROUND_OPACITY = 0.8;
    private static final double TITLE_FONT_SIZE = 48.0;
    private static final double OPTION_FONT_SIZE = 24.0;
    private static final double INSTRUCTION_FONT_SIZE = 16.0;
    private static final double TITLE_Y_POSITION_RATIO = 1.0 / 3.0;
    private static final double OPTION_Y_POSITION_OFFSET = 50.0;
    private static final double INSTRUCTION_Y_POSITION_RATIO = 3.0 / 4.0;
    private static final String TITLE_MAIN_MENU = "Persian Rug";
    private static final String TITLE_PAUSE_MENU = "Paused";
    private static final String INSTRUCTION_TEXT =
            "Use UP/DOWN arrows to select and ENTER to confirm";

    /**
     * The options available in the current menu.
     */
    private String[] options;

    /**
     * The currently selected menu option index.
     */
    private int selectedOption = 0;

    /**
     * Indicates if the current menu is the main menu.
     */
    private boolean isMainMenu = true;

    /**
     * Constructs a {@code Menu} instance and initializes it with main menu options.
     */
    public Menu() {
        setMainMenuOptions();
    }

    /**
     * Sets the menu options to the main menu options.
     */
    public void setMainMenuOptions() {
        options = new String[]{"New Game", "Load Game", "Exit"};
        isMainMenu = true;
    }

    /**
     * Sets the menu options to the pause menu options.
     */
    public void setPauseMenuOptions() {
        options = new String[]{"Resume", "Save Game", "Back to Menu"};
        isMainMenu = false;
    }

    /**
     * Moves the selection up to the previous menu option.
     * If the current selection is the first option, wraps around to the last option.
     */
    public void moveUp() {
        selectedOption = (selectedOption - 1 + options.length) % options.length;
    }

    /**
     * Moves the selection down to the next menu option.
     * If the current selection is the last option, wraps around to the first option.
     */
    public void moveDown() {
        selectedOption = (selectedOption + 1) % options.length;
    }

    /**
     * Gets the currently selected menu option index.
     *
     * @return the index of the selected menu option
     */
    public int getSelectedOption() {
        return selectedOption;
    }

    /**
     * Renders the menu on the screen.
     *
     * @param gc the {@link GraphicsContext} used for rendering
     */
    public void render(final GraphicsContext gc) {
        // Draw semi-transparent background
        gc.setFill(new Color(0, 0, 0, MENU_BACKGROUND_OPACITY));
        gc.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        // Determine the title based on the menu type
        String title;
        if (isMainMenu) {
            title = TITLE_MAIN_MENU;
        } else {
            title = TITLE_PAUSE_MENU;
        }

        // Draw title
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial Bold", TITLE_FONT_SIZE));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(
                title,
                (double) Constants.WINDOW_WIDTH / 2,
                Constants.WINDOW_HEIGHT * TITLE_Y_POSITION_RATIO
        );

        // Draw menu options
        gc.setFont(new Font("Arial", OPTION_FONT_SIZE));
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.YELLOW);
            } else {
                gc.setFill(Color.WHITE);
            }
            gc.fillText(
                    options[i],
                    (double) Constants.WINDOW_WIDTH / 2,
                    (double) Constants.WINDOW_HEIGHT / 2 + i * OPTION_Y_POSITION_OFFSET
            );
        }

        // Draw instructions
        gc.setFill(Color.LIGHTGRAY);
        gc.setFont(new Font("Arial", INSTRUCTION_FONT_SIZE));
        gc.fillText(
                INSTRUCTION_TEXT,
                (double) Constants.WINDOW_WIDTH / 2,
                Constants.WINDOW_HEIGHT * INSTRUCTION_Y_POSITION_RATIO
        );
    }
}
