package com.example.testapplication;

public interface QuizFunction {
    boolean addQuestion(Question q);
    void removeQuestion(Question q);
    void updateMaxScore(int score);
    void updateCurrentScore(int score);
}
