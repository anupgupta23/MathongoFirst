package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnacad.learnacad.R;

/**
 * Created by Sahil Malhotra on 08-06-2017.
 */

public class Profile_Fragment extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view != null){

            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){

                parent.removeView(view);
            }
        }
        try{

            view = inflater.inflate(R.layout.activity_profile,container,false);
        }catch (InflateException e){

        }

        return view;
    }
}
