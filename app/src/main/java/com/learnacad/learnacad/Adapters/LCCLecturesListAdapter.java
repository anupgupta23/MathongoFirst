package com.learnacad.learnacad.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learnacad.learnacad.Activities.LecturePlayerActivity;
import com.learnacad.learnacad.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 30-06-2017.
 */

public class LCCLecturesListAdapter extends RecyclerView.Adapter<LCCLecturesListAdapter.LCCLecturesListViewHolder> {

    Context mContext;
    ArrayList<String> titles;
    ArrayList<String> duration;

    public LCCLecturesListAdapter(Context context, ArrayList<String> titles,ArrayList<String> duration){

        mContext = context;
        this.titles = titles;
        this.duration = duration;
    }

    @Override
    public LCCLecturesListAdapter.LCCLecturesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lcclectures_item_layout,parent,false);
        return new LCCLecturesListViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(LCCLecturesListAdapter.LCCLecturesListViewHolder holder, final int position) {

        int num = position + 1;
        holder.titleTextView.setText(num + ". " +titles.get(position));
        holder.durationTextView.setText(duration.get(position));
        holder.updatedTextView.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, LecturePlayerActivity.class));
            }
        });

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, titles.get(position) + " gets played", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class LCCLecturesListViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView durationTextView;
        TextView updatedTextView;
        ImageButton playButton;

        public LCCLecturesListViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.lcclectures_item_titleTextView);
            durationTextView = (TextView) itemView.findViewById(R.id.lcclectures_item_DurartionTextView);
            updatedTextView = (TextView) itemView.findViewById(R.id.lcclectures_item_ModifyTextView);
            playButton = (ImageButton) itemView.findViewById(R.id.lcclectures_item_playButton);
        }
    }
}
