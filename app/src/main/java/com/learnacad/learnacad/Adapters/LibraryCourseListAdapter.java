package com.learnacad.learnacad.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.learnacad.learnacad.Activities.LibraryCourseContentActivity;
import com.learnacad.learnacad.Models.Minicourse;
import com.learnacad.learnacad.Models.Tutor;
import com.learnacad.learnacad.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 19-06-2017.
 */

public class LibraryCourseListAdapter extends RecyclerView.Adapter<LibraryCourseListAdapter.LibraryCourseViewHolder> {

    Context mContext;
    ArrayList<Minicourse> minicourses;
    SwipeRefreshLayout relativeLayout;
    ArrayList<Tutor> tutors;
//    EnrollbuttonClicklistener enrollClickListener;

    public  LibraryCourseListAdapter(Context context, ArrayList<Minicourse> minicoursesList, SwipeRefreshLayout relativeLayout, ArrayList<Tutor> tutors){

        mContext = context;
        this.tutors = tutors;
        this.minicourses = minicoursesList;
        this.relativeLayout = relativeLayout;
//        this.enrollClickListener = enrollbuttonClicklistener;
    }

    @Override
    public LibraryCourseListAdapter.LibraryCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.library_course_list_item_layout,parent,false);
        return new LibraryCourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LibraryCourseListAdapter.LibraryCourseViewHolder holder, final int position) {


        holder.textViewCourseItemTutorName.setText(tutors.get(position).getName());
        holder.textViewCourseItemDescription.setText(minicourses.get(position).getDescription());
        holder.textViewCourseItemTitle.setText(minicourses.get(position).getName());
        holder.ratingBar.setRating(minicourses.get(position).getRating());

        String category = minicourses.get(position).getRelevance();

        Log.d("care",category);

        if(category.contains("Mains") && category.contains("CBSE")){

            holder.linearColorView.setBackgroundResource(R.drawable.jeecbse);
        }
        else if(category.contains("Mains") && category.contains("Advanced")){

            holder.linearColorView.setBackgroundResource(R.drawable.mainadvanced);
        }
        else if(category.contentEquals("CBSE")){

            holder.linearColorView.setBackgroundResource(R.drawable.onlycbsehdpi);
        }
        else if(category.contentEquals("JEE Mains")){

            holder.linearColorView.setBackgroundResource(R.drawable.onlymainshdpi);
        }
        else if(category.contentEquals("JEE Advance")){

            holder.linearColorView.setBackgroundResource(R.drawable.onlyadvancehdpi);
        }else{

            holder.linearColorView.setBackgroundResource(R.drawable.categoryall);
        }

/*
        if(minicourses.get(position).getEnrolled()){

            holder.enrollButton.setBackgroundResource(R.drawable.enrolled_button_library_shape);
            holder.enrollButton.setText("Enrolled");
        }
*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext,LibraryCourseContentActivity.class);
                String pid = String.valueOf(minicourses.get(position).getCourse_id()) + String.valueOf(tutors.get(position).getTutor_id());
                intent.putExtra("MINICOURSE_ID",minicourses.get(position).getCourse_id());
                intent.putExtra("PROCESS_ID",pid);
                intent.putExtra("ENROLLED",minicourses.get(position).getEnrolled());
                mContext.startActivity(intent);

                }
             });

/*
        holder.bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.bookmarkButton.setImageResource(R.drawable.bookmarkactivebluefilled);
                Snackbar.make(relativeLayout,"Added to your bookmarks",Snackbar.LENGTH_SHORT).show();
            }
        });*/

/*        holder.enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(minicourses.get(position).getEnrolled()){

                    Snackbar.make(relativeLayout,"Already Enrolled",Snackbar.LENGTH_SHORT).show();
                }else{

                    holder.enrollButton.setBackgroundResource(R.drawable.enrolled_button_library_shape);
                    holder.enrollButton.setText("Enrolled");

                    enrollClickListener.EnrollButtonClicked(minicourses.get(position));

                        new SweetAlertDialog(mContext,SweetAlertDialog.SUCCESS_TYPE)
                                .setContentText("You have successfully enrolled")
                                .setTitleText("Enrolled !")
                                .show();


                }
            }
        });*/

        }


    @Override
    public int getItemCount() {
        Log.d("toing",minicourses.size() + "");
        return minicourses.size();
    }


    public class LibraryCourseViewHolder extends RecyclerView.ViewHolder{

        TextView textViewCourseItemTitle;
        TextView textViewCourseItemDescription;
        TextView textViewCourseItemTutorName;
//        Button enrollButton;
        RatingBar ratingBar;
        View linearColorView;

        public LibraryCourseViewHolder(View itemView) {
            super(itemView);

//            enrollButton = (Button) itemView.findViewById(R.id.LibraryCourseItemEnrollButton);
            linearColorView = itemView.findViewById(R.id.categoryLineColor);
            ratingBar = (RatingBar) itemView.findViewById(R.id.LibraryCourseItemRatingbar);
//            bookmarkButton = (ImageButton) itemView.findViewById(R.id.LibraryCourseItemBookMarkButton);
            textViewCourseItemTitle = (TextView) itemView.findViewById(R.id.LibraryCourseItemTitle);
            textViewCourseItemDescription = (TextView) itemView.findViewById(R.id.LibraryCourseItemDescription);
            textViewCourseItemTutorName = (TextView) itemView.findViewById(R.id.LibraryCourseItemTeacherNameTextView);
        }
    }
/*
    public interface EnrollbuttonClicklistener{

        void EnrollButtonClicked(Minicourse m);
    }*/
}
