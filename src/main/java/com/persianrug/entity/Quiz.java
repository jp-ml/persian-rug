package com.persianrug.entity;

public class Quiz {
    private final String question;
    private final String[] options;
    private final int correctAnswer;

    public Quiz(String question, String[] options, int correctAnswer) {
        if (options.length != 4) {
            throw new IllegalArgumentException("Quiz must have exactly 4 options");
        }
        if (correctAnswer < 0 || correctAnswer >= options.length) {
            throw new IllegalArgumentException("Correct answer index must be between 0 and 3");
        }

        this.question = question;
        this.options = options.clone();
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options.clone();
    }

    public boolean checkAnswer(int selectedAnswer) {
        boolean isCorrect = selectedAnswer == correctAnswer;
        System.out.println("Answer checked: " + (isCorrect ? "Correct!" : "Wrong!"));
        return isCorrect;
    }

}