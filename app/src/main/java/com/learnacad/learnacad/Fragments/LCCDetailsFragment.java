package com.learnacad.learnacad.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.learnacad.learnacad.Models.Minicourse;
import com.learnacad.learnacad.Models.SessionManager;
import com.learnacad.learnacad.Models.Tutor;
import com.learnacad.learnacad.Networking.Api_Urls;
import com.learnacad.learnacad.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.orm.SugarRecord.listAll;

/**
 * Created by Sahil Malhotra on 22-06-2017.
 */

public class LCCDetailsFragment extends Fragment {

    View view;
    private TextView mediumTextView, difficultyTextView, durationTextView, relevanceTextView, teachersNameTextView, teachersDescriptionTextView,classTextView;
    CircleImageView circleImageView;
    private Minicourse minicourse;
    private Tutor t;
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view != null) {

            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {

                parent.removeView(view);
            }
        }
        try {

            view = inflater.inflate(R.layout.lccdetails_fragment_layout, container, false);
        } catch (InflateException e) {

        }

        mediumTextView = (TextView) view.findViewById(R.id.lccdetails_courseDetails_languageValueTextView);
        difficultyTextView = (TextView) view.findViewById(R.id.lccdetails_courseDetails_difficultyValueTextView);
        durationTextView = (TextView) view.findViewById(R.id.lccdetails_courseDetails_durationValueTextView);
        relevanceTextView = (TextView) view.findViewById(R.id.lccdetails_courseDetails_relevanceValueTextView);
        classTextView = (TextView) view.findViewById(R.id.lccdetails_courseDetails_classValueTextView);
        circleImageView = (CircleImageView) view.findViewById(R.id.lccdetails_teacherInfo_teachersCircleImageView);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        teachersNameTextView = (TextView) view.findViewById(R.id.lccdetails_teacherInfo_teachersNameTextView);
        teachersDescriptionTextView = (TextView) view.findViewById(R.id.lccdetails_teacherInfo_teachersDescription);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        int course_id = getActivity().getIntent().getIntExtra("MINICOURSE_ID", 0);

        pDialog.setMessage("Loading...");
        showDialog();

        List<SessionManager> session = listAll(SessionManager.class);
        AndroidNetworking.get(Api_Urls.BASE_URL + "api/minicourses/" + course_id)
                .addHeaders("Authorization", "bearer" + session.get(0).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String medium = response.getString("medium");
                            String difficulty = response.getString("level");
                            String duration = response.getString("duration");

                            JSONArray categories = response.getJSONArray("minicoursecategories");

                            Log.d("toing", String.valueOf(categories));
                            StringBuilder builder = new StringBuilder();

                            for (int i = 0; i < categories.length(); ++i) {

                                JSONObject object = categories.getJSONObject(i);
                                JSONObject category = object.getJSONObject("category");


                                builder.append(category.getString("categoryName"));
                                builder.append(",");

                            }

                            int lastIndexOfcomma = builder.lastIndexOf(",");
                            if(lastIndexOfcomma > 0){

                                builder.deleteCharAt(lastIndexOfcomma);
                            }

                            JSONObject tag = response.getJSONObject("tag");
                            JSONObject classNameObject = tag.getJSONObject("class");
                            String className = classNameObject.getString("className");

                            String relevance = builder.toString();
                            minicourse = new Minicourse();
                            minicourse.setMedium(medium);
                            minicourse.setLevel(difficulty);
                            minicourse.setDuration(duration);
                            minicourse.setRelevance(relevance);
                            minicourse.setClassName(className);

                            JSONObject tutor = response.getJSONObject("tutor");
                            String teacherName = tutor.getString("name");
                            String teachersdescription = tutor.getString("description");
                            int teachersId = tutor.getInt("id");
                            t = new Tutor(teachersId, teacherName, teachersdescription);
                            t.setImgUrl(tutor.getString("img"));

                            hideDialog();
                        } catch (JSONException e) {
                            new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                    .setContentText("There seems a problem with us.\nPlease try again later.")
                                    .setTitleText("Oops..!!")
                                    .show();
                            hideDialog();
                        }

                        setData();
                    }

                    @Override
                    public void onError(ANError anError) {
                        new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                                .setTitleText("Oops..!!")
                                .show();
                    }
                });


//        final Button button = (Button) view.findViewById(R.id.lccdetails_teacherInfo_enrollButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getActivity(), "Following", Toast.LENGTH_SHORT).show();
//                button.setBackgroundResource(R.drawable.enrolled_button_library_shape);
//                button.setText("Following");
//                button.setTextColor(Color.WHITE);
//            }
//        });
    }

    private void setData() {

        mediumTextView.setText(minicourse.getMedium());
        difficultyTextView.setText(minicourse.getLevel());
        durationTextView.setText(minicourse.getDuration());
        relevanceTextView.setText(minicourse.getRelevance());
        classTextView.setText(minicourse.getClassName());
        teachersNameTextView.setText(t.getName());
        teachersDescriptionTextView.setText(t.getDescription());


        if(t.getImgUrl().isEmpty() || t.getImgUrl().length() == 0){

            circleImageView.setImageResource(R.drawable.teachersicon);
        }else {

            Picasso.with(getActivity())
                    .load(Api_Urls.BASE_URL + "images/" + t.getImgUrl() + ".jpg")
                    .error(R.drawable.teachersicon)
                    .into(circleImageView);
        }
    }

    private void showDialog() {

        if (!pDialog.isShowing()) {
            pDialog.show();
        }

    }

    private void hideDialog() {

        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }

    }
}