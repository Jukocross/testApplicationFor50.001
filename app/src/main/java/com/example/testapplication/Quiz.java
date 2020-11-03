package com.example.testapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Quiz implements QuizFunction,Parcelable {

    private String Title;
    private String Description;
    private int totalScore = 0;
    private int currentScore = 0;
    private ArrayList<Question> listOfQuestion = new ArrayList<Question>();

    public Quiz(){
    }

    public Quiz(String title, String description) {
        this.Title = title;
        this.Description = description;
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
      @Override
      public Quiz createFromParcel(Parcel in){
          return new Quiz(in);
      }
      public Quiz[] newArray(int size){
          return new Quiz[size];
      }
    };

    protected Quiz(Parcel in){
        Title = in.readString();
        Description = in.readString();
        totalScore = in.readInt();
        currentScore = in.readInt();
        in.readList(listOfQuestion, Quiz.class.getClassLoader());
    }

    public String getDescription() {
        if (Description == null){
            return "Description";
        }
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public ArrayList<Question> getListOfQuestion() {
        return listOfQuestion;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getScoreToString(){
        return ("Score: " + Integer.toString(currentScore) + " / " + Integer.toString(totalScore));
    }

    @Override
    public boolean addQuestion(Question q){
        if (!listOfQuestion.contains(q)){
            listOfQuestion.add(q);
            return true;
        }
        return false;
    }
    public void removeQuestion(Question q){
        listOfQuestion.remove(q);
    }
    public void updateMaxScore(int score){
        this.totalScore = this.totalScore + score;
    }
    //Parcel
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(Title);
        dest.writeString(Description);
        dest.writeInt(totalScore);
        dest.writeInt(currentScore);
        dest.writeList(listOfQuestion);
    }
    public int describeContents() {
        return 0;
    }
}
