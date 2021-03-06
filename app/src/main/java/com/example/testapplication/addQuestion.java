package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addQuestion extends AppCompatActivity {
    private Button createQuestionButton;
    private TextInputLayout question,answer, score, choice1, choice2, choice3, choice4;
    private DatabaseReference questionRef, quizRef;
    private Intent intent;
    private String questionString, answerString, quizTitle, mcq1, mcq2, mcq3, mcq4;
    private int questionScore;
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        Intent getIntent = getIntent();
        quiz = getIntent.getExtras().getParcelable("quizObject");
        quizTitle = quiz.getTitle();
        createQuestionButton = (Button) findViewById(R.id.createQuestion_Button);
        question = (TextInputLayout) findViewById(R.id.createQuestion_Question);
        answer = (TextInputLayout) findViewById(R.id.createQuestion_Answer);
        score = (TextInputLayout) findViewById(R.id.createQuestion_Score);
        choice1 = (TextInputLayout) findViewById(R.id.createQuestion_Choice1);
        choice2 = (TextInputLayout) findViewById(R.id.createQuestion_Choice2);
        choice3 = (TextInputLayout) findViewById(R.id.createQuestion_Choice3);
        choice4 = (TextInputLayout) findViewById(R.id.createQuestion_Choice4);

        createQuestionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                intent = new Intent(addQuestion.this, Quiz_Activity.class);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                quizRef = database.getReference("Database").child("Quiz").child(quizTitle);
                questionRef = quizRef.child("Question");
                questionString = question.getEditText().getText().toString();
                answerString = answer.getEditText().getText().toString();
                questionScore = Integer.parseInt(score.getEditText().getText().toString());
                mcq1 = choice1.getEditText().getText().toString();
                mcq2 = choice2.getEditText().getText().toString();
                mcq3 = choice3.getEditText().getText().toString();
                mcq4 = choice4.getEditText().getText().toString();
                questionRef.addListenerForSingleValueEvent(checkForChild);
            }
        });
    }
    ValueEventListener checkForChild = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (!snapshot.hasChild(questionString)){
                questionRef.child(questionString).setValue(new Question(questionString, questionScore, answerString,mcq1, mcq2, mcq3, mcq4, quiz));
                quizRef.child("totalScore").setValue(quiz.getTotalScore());
                intent.putExtra("quizObject", quiz);
                startActivity(intent);
            }
            else {
                Toast.makeText(addQuestion.this, "Question exist", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}
