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

public class addQuiz extends AppCompatActivity {

    private Button createQuiz;
    private TextInputLayout quizTitle, quizDescription;
    private DatabaseReference quizRef;
    private String title, description;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        createQuiz = (Button) findViewById(R.id.createQuizButton);
        quizTitle = (TextInputLayout) findViewById(R.id.createQuizTitle);
        quizDescription = (TextInputLayout) findViewById(R.id.createQuizDescription);

        createQuiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                intent = new Intent(addQuiz.this, MainActivity.class);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Database");
                quizRef = ref.child("Quiz");
                title = quizTitle.getEditText().getText().toString();
                description = quizDescription.getEditText().getText().toString();
                quizRef.addListenerForSingleValueEvent(checkForChild);
            }
        });
    }

    ValueEventListener checkForChild = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (!snapshot.hasChild(title)){
                quizRef.child(title).setValue(new Quiz(title, description));
                startActivity(intent);
            }
            else {
                Toast.makeText(addQuiz.this, "Quiz exist", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}
