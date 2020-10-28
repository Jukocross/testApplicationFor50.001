package com.example.testapplication;

public class Question {
    private String questionTitle;
    private String answer;
    private String mcqChoice1;
    private String mcqChoice2;
    private String mcqChoice3;
    private int questionScore;

    public Question(String title, int score, QuizFunction quiz) {
        questionTitle = title;
        questionScore = score;
        quiz.addQuestion(this);
        quiz.updateMaxScore(score);
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setMcqChoice1(String mcqChoice1) {
        this.mcqChoice1 = mcqChoice1;
    }

    public void setMcqChoice2(String mcqChoice2) {
        this.mcqChoice2 = mcqChoice2;
    }

    public void setMcqChoice3(String mcqChoice3) {
        this.mcqChoice3 = mcqChoice3;
    }

    public String getAnswer() {
        return answer;
    }

    public String getMcqChoice1() {
        return mcqChoice1;
    }

    public String getMcqChoice2() {
        return mcqChoice2;
    }

    public String getMcqChoice3() {
        return mcqChoice3;
    }


    public void deleteQuestion(QuizFunction quiz){
        quiz.removeQuestion(this);
    }

    public void setQuestionTitle(String questionTitle, String answer) {
        this.questionTitle = questionTitle;
        this.answer = answer;
    }

}
