package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_);

        quizTitle = (TextView) findViewById(R.id.quizTitle_id);
        quizScore = (TextView) findViewById(R.id.quizScore_id);
        quizDescription = (TextView) findViewById(R.id.quizDescription_id);
        lstQuestion = new ArrayList<>();

        addQuestionButton = (Button) findViewById(R.id.addQuestionButton);
        addQuestionButton.setOnClickListener(addQuestionListener);
        //Receive Data
        Intent intent = getIntent();
        quiz = intent.getExtras().getParcelable("quizObject");
        String title = quiz.getTitle();
        String description = quiz.getDescription();

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
}
