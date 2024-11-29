package com.persianrug.engine;

import java.io.*;
import java.util.List;
import com.persianrug.entity.Item;

public class GameSaveManager {
    private static final String SAVE_FILE = "game_save.dat";

    public static class GameSave implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        double playerX;
        double playerY;
        int correctAnswers;
        boolean[] collectedItems;

        public GameSave(double playerX, double playerY, int correctAnswers, List<Item> items) {
            this.playerX = playerX;
            this.playerY = playerY;
            this.correctAnswers = correctAnswers;
            this.collectedItems = new boolean[items.size()];
            for (int i = 0; i < items.size(); i++) {
                this.collectedItems[i] = items.get(i).isCollected();
            }
        }
    }

    public static void saveGame(GameSave save) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(save);
            System.out.println("Game saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public static GameSave loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(SAVE_FILE))) {
            return (GameSave) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    public static boolean saveExists() {
        return new File(SAVE_FILE).exists();
    }
}