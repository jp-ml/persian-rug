package com.persianrug.entity;

public class Quiz {
    private String question;
    private String[] options;
    private int correctAnswer;

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

    public void printQuiz() {
        System.out.println("Question: " + question);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i] +
                    (i == correctAnswer ? " (Correct)" : ""));
        }
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getCorrectAnswerText() {
        return options[correctAnswer];
    }
}