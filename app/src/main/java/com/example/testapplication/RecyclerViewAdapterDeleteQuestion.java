package com.example.testapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterDeleteQuestion extends RecyclerView.Adapter<RecyclerViewAdapterDeleteQuestion.deleteQuestionViewHolder> {

    private Context mContext;
    private List<Question> mData;

    public RecyclerViewAdapterDeleteQuestion(Context mContext, List<Question> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public deleteQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_deletequestion,parent,false);
        return new deleteQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull deleteQuestionViewHolder holder, int position) {
        holder.question_Title.setText(mData.get(position).getQuestionTitle());
        holder.deleteQuestion_ChkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class deleteQuestionViewHolder extends RecyclerView.ViewHolder{

        TextView question_Title;
        CheckBox deleteQuestion_ChkBox;

        public deleteQuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            question_Title = (TextView) itemView.findViewById(R.id.deleteQuestionTitle);
            deleteQuestion_ChkBox = (CheckBox) itemView.findViewById(R.id.deleteQuestionChkBox);

        }
    }
}
