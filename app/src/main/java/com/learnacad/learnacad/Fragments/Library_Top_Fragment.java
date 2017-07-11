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
 * Created by Sahil Malhotra on 18-06-2017.
 */

public class Library_Top_Fragment extends Fragment {

    private static View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("lolo","onCreateView Library_Top_Fragment");

        if(view != null){


            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){

                parent.removeView(view);
            }
        }


        try {
            view = inflater.inflate(R.layout.library_top_layout,container,false);

        }catch (InflateException e){

        }


        //Log.d("lolo","Inside MyCourses_Fragment RETURN");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (view != null) {
            ViewGroup parentViewGroup = (ViewGroup) view.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
    }
}
