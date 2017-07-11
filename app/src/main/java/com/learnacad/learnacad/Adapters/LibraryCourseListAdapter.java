package com.learnacad.learnacad.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learnacad.learnacad.Activities.LibraryCourseContentActivity;
import com.learnacad.learnacad.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 19-06-2017.
 */

public class LibraryCourseListAdapter extends RecyclerView.Adapter<LibraryCourseListAdapter.LibraryCourseViewHolder> {

    Context mContext;
    ArrayList<String> titles;
    RelativeLayout relativeLayout;

    public  LibraryCourseListAdapter(Context context, ArrayList<String> titles, RelativeLayout relativeLayout){

        mContext = context;
        this.titles = titles;
        this.relativeLayout = relativeLayout;
    }

    @Override
    public LibraryCourseListAdapter.LibraryCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.library_course_list_item_layout,parent,false);
        return new LibraryCourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LibraryCourseListAdapter.LibraryCourseViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, LibraryCourseContentActivity.class));

                }
             });


        holder.bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.bookmarkButton.setImageResource(R.drawable.bookmarkactivebluefilled);
                Snackbar.make(relativeLayout,"Added to your bookmarks",Snackbar.LENGTH_SHORT).show();
            }
        });

        holder.enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.enrollButton.setBackgroundResource(R.drawable.enrolled_button_library_shape);
                holder.enrollButton.setText("Enrolled");
            }
        });

        }


    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class LibraryCourseViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        Button enrollButton;
        ImageButton bookmarkButton;

        public LibraryCourseViewHolder(View itemView) {
            super(itemView);

            enrollButton = (Button) itemView.findViewById(R.id.LibraryCourseItemEnrollButton);
            bookmarkButton = (ImageButton) itemView.findViewById(R.id.LibraryCourseItemBookMarkButton);
            textView = (TextView) itemView.findViewById(R.id.LibraryCourseItemTitle);
        }
    }
}
