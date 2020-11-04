package com.example.testapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private String questionTitle;
    private String answer;
    private String mcqChoice1 = "";
    private String mcqChoice2 = "";
    private String mcqChoice3 = "";
    private String mcqChoice4 = "";
    private int questionScore;
    private boolean isSelected, completed = false;

    public Question(){}

    public Question(String title, int score, String ans,String choice1, String choice2, String choice3, String choice4, QuizFunction quiz) {
        questionTitle = title;
        questionScore = score;
        answer = ans;
        mcqChoice1 = choice1;
        mcqChoice2 = choice2;
        mcqChoice3 = choice3;
        mcqChoice4 = choice4;
        quiz.addQuestion(this);
        quiz.updateMaxScore(score);
    }
    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in){
            return new Question(in);
        }
        public Question[] newArray(int size){
            return new Question[size];
        }
    };

    protected Question(Parcel in){
        questionTitle = in.readString();
        answer = in.readString();
        questionScore = in.readInt();
        mcqChoice1 = in.readString();
        mcqChoice2 = in.readString();
        mcqChoice3 = in.readString();
        mcqChoice4 = in.readString();
        isSelected = in.readInt() == 1;
        completed = in.readInt() == 1;
    }

    public void updateQuiz(QuizFunction quiz){
        if(quiz.addQuestion(this)){
            quiz.updateMaxScore(questionScore);
        }
    }

    public boolean checkAnswer(String s){
        if (answer.equals(s)){
         return true;
        }
        return false;
    }

    public void answerQuestion(String s, QuizFunction quiz){
        if (checkAnswer(s)){
            quiz.updateCurrentScore(questionScore);
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public int getQuestionScore() {
        return questionScore;
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

    public String getMcqChoice4() {
        return mcqChoice4;
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

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }


    public void deleteQuestion(QuizFunction quiz){
        quiz.removeQuestion(this);
    }

    public void setQuestionTitle(String questionTitle, String answer) {
        this.questionTitle = questionTitle;
        this.answer = answer;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setMcqChoice4(String mcqChoice4) {
        this.mcqChoice4 = mcqChoice4;
    }

    public void setQuestionScore(int questionScore) {
        this.questionScore = questionScore;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionTitle);
        dest.writeString(answer);
        dest.writeInt(questionScore);
        dest.writeString(mcqChoice1);
        dest.writeString(mcqChoice2);
        dest.writeString(mcqChoice3);
        dest.writeString(mcqChoice4);
        dest.writeInt(isSelected ? 1 : 0);//write 1 if it is true
        dest.writeInt(completed ? 1 : 0);
    }
}
