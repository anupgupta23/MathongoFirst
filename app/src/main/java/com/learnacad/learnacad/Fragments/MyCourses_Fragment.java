package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnacad.learnacad.R;

/**
 * Created by Sahil Malhotra on 03-06-2017.
 */

public class MyCourses_Fragment extends Fragment {


    private static View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("lolo","Inside MyCourses_Fragment START");

        if(view != null){

            Log.d("lolo","Inside MyCourses_Fragment IF");
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){

                parent.removeView(view);
            }
        }


        try {
            view = inflater.inflate(R.layout.mycourses_home_layout,container,false);
            Log.d("lolo","Inside MyCourses_Fragment TRY");

        }catch (InflateException e){

            Log.d("lolo","Inside MyCourses_Fragment CATCH");
        }


        Log.d("lolo","Inside MyCourses_Fragment RETURN");
        return view;
//        return inflater.inflate(R.layout.mycourses_home_layout,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
