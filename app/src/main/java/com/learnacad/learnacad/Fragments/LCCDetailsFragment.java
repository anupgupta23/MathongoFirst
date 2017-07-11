package com.learnacad.learnacad.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.learnacad.learnacad.R;

/**
 * Created by Sahil Malhotra on 22-06-2017.
 */

public class LCCDetailsFragment extends Fragment {

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

            view = inflater.inflate(R.layout.lccdetails_fragment_layout,container,false);
        }catch (InflateException e){

        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final Button button = (Button) view.findViewById(R.id.lccdetails_teacherInfo_enrollButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Following", Toast.LENGTH_SHORT).show();
                button.setBackgroundResource(R.drawable.enrolled_button_library_shape);
                button.setText("Following");
                button.setTextColor(Color.WHITE);
            }
        });
    }
}
