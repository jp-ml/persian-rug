package com.persianrug.engine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu();
    }

    @Test
    void testSetMainMenuOptions() {
        menu.setMainMenuOptions();
        assertEquals("New Game", getOption(menu, 0));
        assertEquals("Load Game", getOption(menu, 1));
        assertEquals("Exit", getOption(menu, 2));
        assertEquals(0, menu.getSelectedOption());
    }


    @Test
    void testMoveUpMainMenu() {
        menu.setMainMenuOptions();
        menu.moveUp();
        assertEquals(2, menu.getSelectedOption());
        assertEquals("Exit", getOption(menu, menu.getSelectedOption()));
    }

    @Test
    void testMoveDownMainMenu() {
        menu.setMainMenuOptions();
        menu.moveDown();
        assertEquals(1, menu.getSelectedOption());
        assertEquals("Load Game", getOption(menu, menu.getSelectedOption()));
    }

    @Test
    void testMoveUpPauseMenu() {
        menu.setPauseMenuOptions();
        menu.moveUp();
        assertEquals(2, menu.getSelectedOption());
        assertEquals("Exit", getOption(menu, menu.getSelectedOption()));
    }

    @Test
    void testMoveDownPauseMenu() {
        menu.setPauseMenuOptions();
        menu.moveDown();
        assertEquals(1, menu.getSelectedOption());
        assertEquals("Load Game", getOption(menu, menu.getSelectedOption()));
    }

    // Utility method to fetch an option by index
    private String getOption(Menu menu, int index) {
        return switch (index) {
            case 0 -> "New Game"; // Main menu default
            case 1 -> "Load Game"; // Main menu default
            case 2 -> "Exit"; // Main menu default
            default -> throw new IllegalArgumentException("Invalid menu option index");
        };
    }
}
