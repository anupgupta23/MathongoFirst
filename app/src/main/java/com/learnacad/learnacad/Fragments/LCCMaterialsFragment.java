package com.learnacad.learnacad.Fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.learnacad.learnacad.Adapters.LCCMatereialsAdapeter;
import com.learnacad.learnacad.Models.Material;
import com.learnacad.learnacad.Models.SessionManager;
import com.learnacad.learnacad.Networking.Api_Urls;
import com.learnacad.learnacad.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.orm.SugarRecord.listAll;

/**
 * Created by Sahil Malhotra on 24-08-2017.
 */

public class LCCMaterialsFragment extends Fragment {


    View view;
    LCCMatereialsAdapeter lccMatereialsAdapeter;
    ArrayList<Material> materials;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    NestedScrollView noMaterialsView;
    public static final int PERMISSION_REQUEST_CODE = 200;
    int courseid;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.lccmaterials_fragment_layout,container,false);
        recyclerView = view.findViewById(R.id.lccmaterials_recyclerView);
        noMaterialsView = view.findViewById(R.id.noMaterialView);
        materials = new ArrayList<>();
        lccMatereialsAdapeter = new LCCMatereialsAdapeter(getActivity(),materials,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(lccMatereialsAdapeter);

        progressBar = view.findViewById(R.id.pb);
        progressBar.setIndeterminate(true);

        if(!checkPermission()){

            requestPermission();
        }

        fetchData();

        return view;
    }

    private void requestPermission() {

        requestPermissions(new String[]{READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    private  boolean checkPermission() {

        int readExternal = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), READ_EXTERNAL_STORAGE);
        int writeExternal = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return (readExternal == PackageManager.PERMISSION_GRANTED && writeExternal == PackageManager.PERMISSION_GRANTED);
    }



    private void fetchData() {

        courseid = getActivity().getIntent().getIntExtra("MINICOURSE_ID", 0);

        progressBar.setVisibility(View.VISIBLE);

        Log.d("materialFileCheck", String.valueOf(courseid));

        List<SessionManager> session = listAll(SessionManager.class);

        Log.d("materialFileCheck",Api_Urls.BASE_URL+"api/minicourses/"+courseid + "/material");


        AndroidNetworking.get(Api_Urls.BASE_URL+"api/minicourses/"+courseid + "/material")
                .addHeaders("Authorization", "bearer " + session.get(0).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() == 0){

                            noMaterialsView.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);

                        }else {

                            recyclerView.setVisibility(View.VISIBLE);
                            noMaterialsView.setVisibility(View.GONE);

                            for (int i = 0; i < response.length(); ++i) {

                                try {
                                    Material material = new Material(response.getString(i), courseid);
                                    materials.add(material);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            lccMatereialsAdapeter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        progressBar.setVisibility(View.GONE);
                        noMaterialsView.setVisibility(View.VISIBLE);
                        Log.d("materialFileCheck",anError.getErrorBody());
                        Log.d("materialFileCheck", String.valueOf(anError.getErrorCode()));
                    }
                });
    }
}
