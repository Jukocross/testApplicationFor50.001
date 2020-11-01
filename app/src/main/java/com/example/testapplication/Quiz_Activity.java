package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Quiz_Activity extends AppCompatActivity {

    private TextView quizTitle, quizScore, quizDescription;
    private List<Question> lstQuestion;
    private RecyclerView questionRV;
    private RecyclerViewAdapterQuestion questionAdapter;
    private Button addQuestionButton, deleteQuestionButton, deleteQuizButton;
    private Quiz quiz;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("Database");
    private DatabaseReference quizRef = ref.child("Quiz");
    private DatabaseReference questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_);

        quizTitle = (TextView) findViewById(R.id.quizTitle_id);
        quizScore = (TextView) findViewById(R.id.quizScore_id);
        quizDescription = (TextView) findViewById(R.id.quizDescription_id);
        lstQuestion = new ArrayList<Question>();

        addQuestionButton = (Button) findViewById(R.id.addQuestionButton);
        deleteQuestionButton = (Button) findViewById(R.id.deleteQuestionButton);
        deleteQuizButton = (Button) findViewById(R.id.deleteQuizButton);
        addQuestionButton.setOnClickListener(addQuestionListener);
        deleteQuestionButton.setOnClickListener(deleteQuestionListener);
        deleteQuizButton.setOnClickListener(deleteQuizListener);

        //Receive Data
        Intent intent = getIntent();
        quiz = intent.getExtras().getParcelable("quizObject");

        String title = quiz.getTitle();
        String description = quiz.getDescription();

        questionList = quizRef.child(title).child("Question");
        questionList.addValueEventListener(questionListener);

        String score = quiz.getScoreToString();

        //Set Data
        quizTitle.setText(title);
        quizScore.setText(score);
        quizDescription.setText(description);

        questionRV = (RecyclerView) findViewById(R.id.recyclerviewQuestion_id);
        questionAdapter  = new RecyclerViewAdapterQuestion(this, lstQuestion);
        questionRV.setLayoutManager(new LinearLayoutManager(this));
        questionRV.setAdapter(questionAdapter);
    }

    View.OnClickListener addQuestionListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Quiz_Activity.this, addQuestion.class);
            intent.putExtra("quizObject", quiz);
            startActivity(intent);
        }
    };

    View.OnClickListener deleteQuestionListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Quiz_Activity.this, deleteQuestion.class);
            startActivity(intent);
        }
    };

    View.OnClickListener deleteQuizListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent =  new Intent(Quiz_Activity.this, MainActivity.class);
            quizRef.child(quiz.getTitle()).setValue(null);
            startActivity(intent);
        }
    };

    ValueEventListener questionListener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Question temp = ds.getValue(Question.class);
                    if (!lstQuestion.contains(temp)){
                        lstQuestion.add(temp);
                        temp.updateQuiz(quiz);
                        quizScore.setText(quiz.getScoreToString());
                    }
                }
                questionRV.setAdapter(questionAdapter);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w("loadPost:onCancelled", error.toException());
        }
    };
}
