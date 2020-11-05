package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    ArrayList<Quiz> lstQuiz;
    RecyclerView quizRV;
    FloatingActionButton btnFloating;
    RecyclerViewAdapterQuiz quizAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Database");
    FirebaseUser user;
    DatabaseReference quizRef = ref.child("Quiz");
    DatabaseReference schoolRef = ref.child("School");
    private String email;
    private static final String TAG ="Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstQuiz = new ArrayList<>();
        quizRV = (RecyclerView) findViewById(R.id.recyclerview_id);
        quizAdapter = new RecyclerViewAdapterQuiz(this, lstQuiz);
        btnFloating = (FloatingActionButton) findViewById(R.id.btnFloating);
        quizRef.addValueEventListener(quizListener);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "User signed in");
        } else {
            // No user is signed in
            Log.d(TAG, "No user signed in");
        }
        //Toast.makeText(MainActivity.this, "" + lstQuiz.isEmpty(), Toast.LENGTH_SHORT).show();

        //quizRef.push().setValue(new Quiz("Quiz 1", 10));

        if(user != null){
            email = user.getEmail();
            Log.d(TAG, "Info of User: " + email);
        }

        quizRV.setLayoutManager(new LinearLayoutManager(this));
        quizRV.setAdapter(quizAdapter);

        quizRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(quizRV.SCROLL_STATE_DRAGGING==newState){
                    btnFloating.hide();
                }
                else {
                    btnFloating.show();
                }
            }
        });

        btnFloating.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, addQuiz.class);
                startActivity(intent);
            }
        });

    }

    ValueEventListener quizListener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Quiz temp = ds.getValue(Quiz.class);
                    if (!lstQuiz.contains(temp)){
                        lstQuiz.add(temp);
                    }
                }
                quizRV.setAdapter(quizAdapter);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w("loadPost:onCancelled", error.toException());
        }
    };


}
