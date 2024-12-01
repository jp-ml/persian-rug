package com.persianrug.engine;

import java.io.*;
import java.util.List;
import com.persianrug.entity.Item;

/**
 * Manages game save and load functionality using serialization.
 *
 * @author Homayoun Khoshi
 * @version 2024
 */
public class GameSaveManager {

    /**
     * The name of the file where the game state is saved.
     */
    private static final String SAVE_FILE = "game_save.dat";

    /**
     * Represents the saved game state.
     */
    public static class GameSave implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * The x-coordinate of the player.
         */
        double playerX;

        /**
         * The y-coordinate of the player.
         */
        double playerY;

        /**
         * The number of correct answers by the player.
         */
        int correctAnswers;

        /**
         * Array indicating whether each item is collected.
         */
        boolean[] collectedItems;

        /**
         * Constructs a {@code GameSave} instance with the specified game state.
         *
         * @param playerX       the x-coordinate of the player
         * @param playerY       the y-coordinate of the player
         * @param correctAnswers the number of correct answers by the player
         * @param items         the list of items in the game, used to determine collected items
         */
        public GameSave(final double playerX, final double playerY,
                        final int correctAnswers, final List<Item> items) {
            this.playerX = playerX;
            this.playerY = playerY;
            this.correctAnswers = correctAnswers;
            this.collectedItems = new boolean[items.size()];
            for (int i = 0; i < items.size(); i++) {
                this.collectedItems[i] = items.get(i).isCollected();
            }
        }
    }

    /**
     * Saves the current game state to a file.
     *
     * @param save the game state to be saved
     */
    public static void saveGame(final GameSave save) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(save);
            System.out.println("Game saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads the game state from the save file.
     *
     * @return the loaded game state, or {@code null} if loading fails
     */
    public static GameSave loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (GameSave) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if a save file exists.
     *
     * @return {@code true} if the save file exists, otherwise {@code false}
     */
    public static boolean saveExists() {
        return new File(SAVE_FILE).exists();
    }
}
