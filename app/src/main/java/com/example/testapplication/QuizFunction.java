package com.example.testapplication;

public interface QuizFunction {
    void addQuestion(Question q);
    void removeQuestion(Question q);
    void updateMaxScore(int score);
}
