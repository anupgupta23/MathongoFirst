package com.learnacad.learnacad.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.learnacad.learnacad.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 10-06-2017.
 */

public class BookmarksListAdapter  extends RecyclerView.Adapter<BookmarksListAdapter .BookmarksListViewHolder>{

    Context mContext;
    ArrayList<String> titles;

    public BookmarksListAdapter (Context context,ArrayList<String>titleList){

        mContext = context;
        titles = titleList;
        Log.d("lolo","Reached adp");
    }
    @Override
    public BookmarksListAdapter .BookmarksListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.recommended_feed_item_layout,parent,false);
        return new BookmarksListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookmarksListAdapter.BookmarksListViewHolder holder, final int position) {

        holder.textView.setText("Lol" + " " + position);

        Button button = (Button) holder.itemView.findViewById(R.id.enrollButtonRecommendedFeed);
        button.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "LOL" + " " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        Log.d("lolo", String.valueOf(titles.size()));
        return titles.size();
    }

    public class BookmarksListViewHolder extends  RecyclerView.ViewHolder{

        TextView textView;

        public BookmarksListViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
