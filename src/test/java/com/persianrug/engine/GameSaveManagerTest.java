package com.persianrug.engine;
import com.persianrug.entity.Item;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class GameSaveManagerTest {
    private static final String TEST_SAVE_FILE = "test_game_save.dat";

    @BeforeEach
    void setUp() {
        deleteTestSaveFile();
    }

    @AfterEach
    void tearDown() {
        deleteTestSaveFile();
    }

    @Test
    void testLoadNonExistentSave() {
        deleteTestSaveFile();
        GameSaveManager.GameSave loadedSave = loadGame();
        assertNull(loadedSave, "Loaded save should be null for non-existent save file");
    }

    private void saveGame(GameSaveManager.GameSave save) {
        try (var oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(TEST_SAVE_FILE))) {
            oos.writeObject(save);
        } catch (Exception e) {
            fail("Exception occurred during save: " + e.getMessage());
        }
    }

    private GameSaveManager.GameSave loadGame() {
        try (var ois = new java.io.ObjectInputStream(new java.io.FileInputStream(TEST_SAVE_FILE))) {
            return (GameSaveManager.GameSave) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    private void deleteTestSaveFile() {
        File file = new File(TEST_SAVE_FILE);
        if (file.exists() && !file.delete()) {
            fail("Failed to delete test save file");
        }
    }

    private Item createTestItem(double x, double y, boolean collected) {
        return new Item(x, y, "/dummy/image/path", null) {
            @Override
            public boolean isCollected() {
                return collected;
            }
        };
    }
}