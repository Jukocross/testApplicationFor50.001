package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class endOfQuizSummaryActivity extends AppCompatActivity {
    //TODO FINISH UP THIS SUMMARY QUIZ PAGE
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("Database");
    private DatabaseReference quizRef = ref.child("Quiz");
    private DatabaseReference questionListRef;
    private Intent intent;
    private Quiz quiz;
    private ArrayList<Question> lstQuestion;
    private Button endOfQuizButton;
    private TextView showScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_quiz_summary);

        intent = getIntent();
        quiz = intent.getExtras().getParcelable("quizObject");
        String title = quiz.getTitle();
        questionListRef = quizRef.child(title).child("Question");
        lstQuestion = quiz.getListOfQuestion();
        String score = quiz.getScoreToString();

        showScore = (TextView) findViewById(R.id.quiz_summary_score);
        endOfQuizButton = (Button) findViewById(R.id.quiz_summary_button);

        //Set score to text view


    }
}