package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class quizStartActivity extends AppCompatActivity {

    private TextView quizTitle, quizScore, quizDescription;
    private Quiz quiz;
    private Button startButton;
    private ArrayList<Question> lstQuestion;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("Database");
    private DatabaseReference quizRef = ref.child("Quiz");
    private DatabaseReference questionListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        lstQuestion = new ArrayList<Question>();

        quizTitle = (TextView) findViewById(R.id.quiz_start_title);
        quizScore = (TextView) findViewById(R.id.quiz_start_score);
        quizDescription = (TextView) findViewById(R.id.quiz_start_description);
        startButton = (Button) findViewById(R.id.start_quiz_button);

        Intent intent = getIntent();
        quiz = intent.getExtras().getParcelable("quizObject");

        String title = quiz.getTitle();
        questionListRef = quizRef.child(title).child("Question");

        questionListRef.addValueEventListener(checkForChild);

        startButton.setOnClickListener(startQuestions);

    }

    View.OnClickListener startQuestions = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(quizStartActivity.this, answerQuestionActivity.class);
            intent.putExtra("quizObject", quiz);
            startActivity(intent);
        }
    };

    ValueEventListener checkForChild = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()){
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Question temp = ds.getValue(Question.class);
                    if (!lstQuestion.contains(temp)){
                        lstQuestion.add(temp);
                        temp.updateQuiz(quiz);
                        quizScore.setText(quiz.getScoreToString());
                    }
                }
            }
            else {
                Toast.makeText(quizStartActivity.this, "Questions does not exist", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}
