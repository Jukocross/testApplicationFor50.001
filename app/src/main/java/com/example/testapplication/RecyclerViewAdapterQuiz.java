package com.example.testapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterQuiz extends RecyclerView.Adapter<RecyclerViewAdapterQuiz.MyViewHolder> {

    private Context mContext;
    private List<Quiz> mData;

    public RecyclerViewAdapterQuiz(Context mContext, List<Quiz> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_quiz,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.quiz_title.setText(mData.get(position).getTitle());
        holder.quiz_score.setText(mData.get(position).getScoreToString());
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent intent = new Intent(mContext, Quiz_Activity.class);
                Intent intent = new Intent(mContext, quizStartActivity.class);
                intent.putExtra("quizObject", mData.get(position));
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView quiz_title;
        TextView quiz_score;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);
            quiz_title = (TextView) itemView.findViewById(R.id.quiz_title_id);
            quiz_score = (TextView) itemView.findViewById(R.id.quiz_score_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
