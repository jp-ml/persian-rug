package com.persianrug.entity;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a quiz with a question, options, and the index of the correct answer.
 * The quiz consists of exactly four options, and the correct answer is specified by an index.
 * @author Juhyun Park
 * @version 2024
 */
public class Quiz {
    private final String question;
    private final String[] options;
    private final int correctAnswer;
    private final int length = 4;

    /**
     * Constructs a new Quiz instance with the specified question,
     * options, and correct answer index.
     *
     * @param question The question to be asked in the quiz.
     * @param options An array of four options for the quiz.
     * @param correctAnswer The index of the correct answer (0-based).
     * @throws IllegalArgumentException If the number of options is not exactly 4,
     * or if the correctAnswer index is invalid.
     */
    public Quiz(final String question, final String[] options, final int correctAnswer) {
        if (options.length != length) {
            throw new IllegalArgumentException("Quiz must have exactly 4 options");
        }
        if (correctAnswer < 0 || correctAnswer >= options.length) {
            throw new IllegalArgumentException("Correct answer index must be between 0 and 3");
        }

        this.question = question;
        this.options = options.clone();
        this.correctAnswer = correctAnswer;
    }

    /**
     * Returns the question of the quiz.
     *
     * @return The question as a String.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns a clone of the options array for the quiz.
     *
     * @return A clone of the options array.
     */
    public String[] getOptions() {
        return options.clone();
    }

    /**
     * Checks whether the selected answer is correct.
     *
     * @param selectedAnswer The index of the answer selected by the user (0-based).
     * @return {@code true} if the selected answer is correct, otherwise {@code false}.
     */
    public boolean checkAnswer(final int selectedAnswer) {
        boolean isCorrect = selectedAnswer == correctAnswer;
        if (isCorrect) {
            System.out.println("Answer checked: Correct!");
        } else {
            System.out.println("Answer checked: Wrong!");
        }
        return isCorrect;
    }

    /**
     * Compares this quiz with another object for equality.
     *
     * @param object The object to compare with.
     * @return {@code true} if the quizzes are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Quiz quiz = (Quiz) object;
        return correctAnswer == quiz.correctAnswer && Objects.equals(question, quiz.question)
                && Objects.deepEquals(options, quiz.options);
    }

    /**
     * Returns a hash code for this quiz based on its properties.
     *
     * @return A hash code value for this quiz.
     */
    @Override
    public int hashCode() {
        return Objects.hash(question, Arrays.hashCode(options), correctAnswer, length);
    }

    /**
     * Returns a string representation of this quiz.
     *
     * @return A string representing this quiz.
     */
    @Override
    public String toString() {
        return "Quiz{"
                + "question='" + question + '\''
                + ", options=" + Arrays.toString(options)
                + ", correctAnswer=" + correctAnswer
                + ", length=" + length
                + '}';
    }
}
