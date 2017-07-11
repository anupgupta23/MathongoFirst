package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.learnacad.learnacad.R;

/**
 * Created by Sahil Malhotra on 16-06-2017.
 */

public class Library_Fragment extends Fragment {

    private static View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view != null){

            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){

                parent.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.library_fragment,container,false);


        }catch (InflateException e){


        }

//        setHasOptionsMenu(true);



        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_Library,new Library_Top_Fragment());
        fragmentTransaction.addToBackStack(null).commit();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // menu.clear();
        inflater.inflate(R.menu.menu_library,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.notificationsFilters){

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_frame_Library,new Filters());
            ft.addToBackStack(null).commit();

        }

        return true;
    }
}
