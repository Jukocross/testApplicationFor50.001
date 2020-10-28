package com.example.testapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterQuestion extends RecyclerView.Adapter<RecyclerViewAdapterQuestion.questionViewHolder> {

    private Context mContext;
    private List<Question> mData;

    public RecyclerViewAdapterQuestion(Context mContext, List<Question> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public questionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_question,parent,false);
        return new questionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull questionViewHolder holder, int position) {
        String problem = "Problem " + Integer.toString(position+1) + ": ";
        holder.question_Title.setText(problem + mData.get(position).getQuestionTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class questionViewHolder extends RecyclerView.ViewHolder{

        TextView question_Title;

        public questionViewHolder(@NonNull View itemView) {
            super(itemView);

            question_Title = (TextView) itemView.findViewById(R.id.questionTitle);

        }
    }

}
