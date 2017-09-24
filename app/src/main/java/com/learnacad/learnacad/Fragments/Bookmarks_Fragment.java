package com.learnacad.learnacad.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.learnacad.learnacad.Adapters.BookmarksListAdapter;
import com.learnacad.learnacad.Models.Lecture;
import com.learnacad.learnacad.Models.SessionManager;
import com.learnacad.learnacad.Networking.Api_Urls;
import com.learnacad.learnacad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.orm.SugarRecord.listAll;

/**
 * Created by Sahil Malhotra on 03-06-2017.
 */

public class Bookmarks_Fragment extends Fragment {

    RecyclerView recyclerView;
    BookmarksListAdapter adapter;
    ArrayList<Lecture> fetchedBookmarks;
    private ProgressDialog pDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.bookmarks_fragment_layout,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.bookmarksRecyclerViewList);
        fetchedBookmarks = new ArrayList<>();
        adapter = new BookmarksListAdapter(getActivity(),fetchedBookmarks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);


        if(!isConnected()){

            new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                    .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                    .setTitleText("Oops..!!")
                    .show();

        }

        getActivity().setTitle("My Bookmarks");
        fetchTitles();

        return v;
    }

    public void fetchTitles() {

        final List<SessionManager> session = listAll(SessionManager.class);

        showDialog();
        pDialog.setMessage("Loading...");

        Log.d("bookmarks","Bearer "+session.get(0).getToken());

        AndroidNetworking.get(Api_Urls.BASE_URL + "api/students/bookmarks")
                .addHeaders("Authorization", "Bearer " + session.get(0).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {


                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("123456",response.toString());

                        Log.d("123456",response.length() + " ");

                        for(int i = 0; i < response.length(); ++i){

                            try {

                                JSONObject object = response.getJSONObject(i);
                                int l_id = object.getInt("lessonId");
                                JSONObject lesson = object.getJSONObject("lesson");
                                String name = lesson.getString("name");
                                Log.d("123456", name);
                                String duration = lesson.getString("duration");
                                String url = lesson.getString("videoUrl");
                                String desc = lesson.getString("description");
                               // int upvotes = lesson.getInt("upvotes");

                                Lecture  l = new Lecture(l_id,name,url,duration,desc,0,false,false);


                                fetchedBookmarks.add(l);

                            } catch (JSONException e) {
                                new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                        .setContentText("There seems a problem with us.\nPlease try again later.")
                                        .setTitleText("Oops..!!")
                                        .show();
                                hideDialog();
                            }

                        }

                        hideDialog();

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {

                        new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                                .setTitleText("Oops..!!")
                                .show();


                        hideDialog();
                    }
                });

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

    public boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();


        return (activeNetwork != null && (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE));

    }
}
