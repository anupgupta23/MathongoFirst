package com.learnacad.learnacad.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnacad.learnacad.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 11-07-2017.
 */

public class LPLectureTabAdapter extends RecyclerView.Adapter<LPLectureTabAdapter.LPLectureTabViewHolder> {

    Context mContext;
    ArrayList<String> titles;

    public LPLectureTabAdapter(Context context,ArrayList<String> tiles){

        mContext = context;
        titles = tiles;

    }

    @Override
    public LPLectureTabAdapter.LPLectureTabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lecture_player_lecturetab_item_layout,parent,false);
        return new LPLectureTabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LPLectureTabAdapter.LPLectureTabViewHolder holder, int position) {

        int num = position + 1;
        holder.titleTextView.setText(titles.get(position));
        holder.numTextView.setText(num + "");

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class LPLectureTabViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView numTextView;

        public LPLectureTabViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.lecturePlayerLectureItemTitleTextView);
            numTextView = (TextView) itemView.findViewById(R.id.lecturePlayerLectureItemTextView);
        }
    }
}
