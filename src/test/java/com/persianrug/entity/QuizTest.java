package com.persianrug.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {

    @Test
    public void testValidQuizCreation() {
        String question = "What is the capital of France?";
        String[] options = {"Paris", "Berlin", "Madrid", "Rome"};
        int correctAnswer = 0;

        Quiz quiz = new Quiz(question, options, correctAnswer);

        assertEquals(question, quiz.getQuestion(), "Quiz question should match the input.");
        assertArrayEquals(options, quiz.getOptions(), "Quiz options should match the input.");
    }

    @Test
    public void testInvalidOptionsCount() {
        String question = "What is the capital of France?";
        String[] options = {"Paris", "Berlin", "Madrid"};

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Quiz(question, options, 0));

        assertEquals("Quiz must have exactly 4 options", exception.getMessage(), "Exception message should indicate invalid option count.");
    }

    @Test
    public void testInvalidCorrectAnswerIndexLow() {
        String question = "What is the capital of France?";
        String[] options = {"Paris", "Berlin", "Madrid", "Rome"};
        int correctAnswer = -1; // Invalid index

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Quiz(question, options, correctAnswer));

        assertEquals("Correct answer index must be between 0 and 3", exception.getMessage(), "Exception message should indicate invalid correct answer index.");
    }

    @Test
    public void testInvalidCorrectAnswerIndexHigh() {
        String question = "What is the capital of France?";
        String[] options = {"Paris", "Berlin", "Madrid", "Rome"};
        int correctAnswer = 4; // Invalid index

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Quiz(question, options, correctAnswer));

        assertEquals("Correct answer index must be between 0 and 3", exception.getMessage(), "Exception message should indicate invalid correct answer index.");
    }

    @Test
    public void testCheckCorrectAnswer() {
        String question = "What is the capital of France?";
        String[] options = {"Paris", "Berlin", "Madrid", "Rome"};
        int correctAnswer = 0;

        Quiz quiz = new Quiz(question, options, correctAnswer);
        assertTrue(quiz.checkAnswer(0), "Correct answer should return true.");
    }

    @Test
    public void testCheckIncorrectAnswer() {
        String question = "What is the capital of France?";
        String[] options = {"Paris", "Berlin", "Madrid", "Rome"};
        int correctAnswer = 0;

        Quiz quiz = new Quiz(question, options, correctAnswer);
        assertFalse(quiz.checkAnswer(1), "Incorrect answer should return false.");
    }

    @Test
    public void testOptionsAreImmutable() {
        String question = "What is the capital of France?";
        String[] options = {"Paris", "Berlin", "Madrid", "Rome"};
        int correctAnswer = 0;

        Quiz quiz = new Quiz(question, options, correctAnswer);

        String[] fetchedOptions = quiz.getOptions();
        fetchedOptions[0] = "Changed";

        assertArrayEquals(options, quiz.getOptions(), "Options should not be modifiable from outside the class.");
    }
}
