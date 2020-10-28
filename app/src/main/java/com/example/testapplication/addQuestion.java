package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addQuestion extends AppCompatActivity {
    //TODO: ADD QUESTION UNDER QUIZ DATABASE AND UPDATE THE SCORE.
    //TODO: THORW BACK THE QUIZ OBJECT TO THE QUIZ_ACTIVITY
    //TODO: CHECK FOR THE QUESTION EXISTED ANOT.
    private Button createQuestionButton;
    private TextInputLayout question,answer;
    private DatabaseReference questionRef;
    private Intent intent;
    private String questionString, answerString, child;
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        Intent getIntent = getIntent();
        quiz = getIntent.getExtras().getParcelable("quizObject");
        child = quiz.getTitle();
        createQuestionButton = (Button) findViewById(R.id.createQuestion_Button);
        question = (TextInputLayout) findViewById(R.id.createQuestion_Question);
        answer = (TextInputLayout) findViewById(R.id.createQuestion_Answer);

        createQuestionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                intent = new Intent(addQuestion.this, Quiz_Activity.class);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Database").child("Quiz");
                questionRef = ref.child(child);
                questionString = question.getEditText().getText().toString();
                answerString = answer.getEditText().getText().toString();
                questionRef.addListenerForSingleValueEvent(checkForChild);
            }
        });
    }
}
