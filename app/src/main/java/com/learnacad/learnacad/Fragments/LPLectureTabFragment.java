package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnacad.learnacad.Adapters.LPLectureTabAdapter;
import com.learnacad.learnacad.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 11-07-2017.
 */

public class LPLectureTabFragment extends Fragment {

    RecyclerView recyclerView;
    LPLectureTabAdapter adapter;
    ArrayList<String> titles;
    private static View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view != null){

            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){

                parent.removeAllViews();
            }
        }


        try {

            view = inflater.inflate(R.layout.lecture_player_lecturetab,container,false);
        }catch (InflateException e){


        }



        recyclerView = (RecyclerView) view.findViewById(R.id.lecturePlayerLectureTabRecyclerView);
        titles = new ArrayList<>();
        adapter = new LPLectureTabAdapter(getActivity(),titles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        fetchData();
        return view;
    }

    private void fetchData() {

        titles.add(0,"Introduction");
        titles.add(1,"Domain");
        titles.add(2,"Range");
        titles.add(3,"Graph");
        titles.add(4,"Practice Problems");

        adapter.notifyDataSetChanged();
    }

}
