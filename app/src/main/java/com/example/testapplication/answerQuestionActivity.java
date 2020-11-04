package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class answerQuestionActivity extends AppCompatActivity {

    TextView quizTitle, questionTitle;
    RadioButton choice1,choice2,choice3,choice4;
    Button nextQuestion;
    Quiz quiz;
    ArrayList<Question> lstQuestions;
    String selectedChoice;
    Boolean lastQuestionIndicator;
    Question tempQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);

        quizTitle = (TextView) findViewById(R.id.answer_question_title);
        questionTitle = (TextView) findViewById(R.id.answer_question_question);
        choice1 = (RadioButton) findViewById(R.id.answer_question_choice1);
        choice2 = (RadioButton) findViewById(R.id.answer_question_choice2);
        choice3 = (RadioButton) findViewById(R.id.answer_question_choice3);
        choice4 = (RadioButton) findViewById(R.id.answer_question_choice4);
        nextQuestion = (Button) findViewById(R.id.answer_question_next);
        lastQuestionIndicator = false;
        selectedChoice = "";
        Intent intent = getIntent();
        quiz = intent.getExtras().getParcelable("quizObject");
        lstQuestions = quiz.getListOfQuestion();
        quizTitle.setText(quiz.getTitle());
        for (int i = 0; i < lstQuestions.size(); ++i){
            tempQuestion = lstQuestions.get(i);
            Log.d("Answer_Question Debug", "Question " + Integer.toString(i) + " completeness: " + String.valueOf(tempQuestion.isCompleted()));
            //Log.d("Answer_Question Debug", "Total Question" + Integer.toString(lstQuestions.size()));
            //Log.d("Answer_Question Debug", "Quiz Address " + System.identityHashCode(quiz));
            if (!tempQuestion.isCompleted()){
                if (lstQuestions.get(i+1).isCompleted()){
                    lastQuestionIndicator = true;
                    Toast.makeText(answerQuestionActivity.this, "Last Question", Toast.LENGTH_SHORT).show();
                    Log.d("Answer_Question Debug", "Last Question ");
                }
                questionTitle.setText(tempQuestion.getQuestionTitle());
                choice1.setText(tempQuestion.getMcqChoice1());
                choice2.setText(tempQuestion.getMcqChoice2());
                choice3.setText(tempQuestion.getMcqChoice3());
                choice4.setText(tempQuestion.getMcqChoice4());
                break;
            }
        }
        nextQuestion.setOnClickListener(nextQuestionListener);
    }

    View.OnClickListener nextQuestionListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (!selectedChoice.isEmpty()){
                tempQuestion.setCompleted(true);
                Log.d("Answer_Question Debug", "Question Completeness " + String.valueOf(tempQuestion.isCompleted()));
                Toast.makeText(answerQuestionActivity.this, String.valueOf(tempQuestion.isCompleted()), Toast.LENGTH_SHORT).show();
                quiz.addQuestion(tempQuestion);
                tempQuestion.answerQuestion(selectedChoice, quiz);
                Intent intent;
                if (lastQuestionIndicator){
                    intent = new Intent(answerQuestionActivity.this, endOfQuizSummaryActivity.class);
                }
                else {
                    intent = new Intent(answerQuestionActivity.this, answerQuestionActivity.class);
                }

                intent.putExtra("quizObject", quiz);
                startActivity(intent);
            }
            else{
                Toast.makeText(answerQuestionActivity.this, "No choice selected", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.answer_question_choice1:
                if(checked)
                    selectedChoice = choice1.getText().toString();
                break;
            case R.id.answer_question_choice2:
                if(checked)
                    selectedChoice = choice2.getText().toString();
                break;
            case R.id.answer_question_choice3:
                if(checked)
                    selectedChoice = choice3.getText().toString();
                break;
            case R.id.answer_question_choice4:
                if(checked)
                    selectedChoice = choice4.getText().toString();
                break;
        }
        Toast.makeText(answerQuestionActivity.this, selectedChoice, Toast.LENGTH_SHORT).show();
    }
}