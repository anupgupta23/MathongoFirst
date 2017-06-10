package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.learnacad.learnacad.R;

/**
 * Created by Sahil Malhotra on 04-06-2017.
 */

public class ButtonPanelFragment extends Fragment {


    private static View v;
    private static Home_Fragment home_fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(v != null){

            ViewGroup parent = (ViewGroup) v.getParent();
            if(parent != null){

                parent.removeView(v);
            }
        }
        try{

            v = inflater.inflate(R.layout.button_panel,container,false);
        }catch (InflateException e){

        }

        return v;
    }

  /*  public void setPanelButtonClicked(PanelButtonClickListener listener){

        this.listener = listener;
    }*/


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button home = (Button) view.findViewById(R.id.homeButton);
        Button library = (Button) view.findViewById(R.id.libraryButton);
        Button events = (Button) view.findViewById(R.id.eventsButton);
        Button profile = (Button) view.findViewById(R.id.profileButton);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (home_fragment == null) {

                    home_fragment = new Home_Fragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, home_fragment);
                    ft.commit();
                } else {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, home_fragment);
                    ft.commit();
                }
            }
        });

        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Library", Toast.LENGTH_SHORT).show();
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Events", Toast.LENGTH_SHORT).show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Profile", Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,new Profile_Fragment());
                fragmentTransaction.commit();

            }
        });


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
 /*   @Override
    public void onClick(View v) {

        id = v.getId();
    }

    public interface PanelButtonClickListener{

            void onPanelButtonClicked(int id);
    }*/
}
