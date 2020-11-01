package com.example.testapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private String questionTitle;
    private String answer;
    private String mcqChoice1 = "";
    private String mcqChoice2 = "";
    private String mcqChoice3 = "";
    private int questionScore;
    private boolean isSelected;

    public Question(){}

    public Question(String title, int score, String ans, QuizFunction quiz) {
        questionTitle = title;
        questionScore = score;
        answer = ans;
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
    }

    public void updateQuiz(QuizFunction quiz){
        quiz.addQuestion(this);
        quiz.updateMaxScore(questionScore);
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
    }
}
