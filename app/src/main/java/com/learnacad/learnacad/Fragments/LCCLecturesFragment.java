package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnacad.learnacad.Adapters.LCCLecturesListAdapter;
import com.learnacad.learnacad.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 22-06-2017.
 */

public class LCCLecturesFragment extends Fragment {

    private static View view;
    private RecyclerView recyclerView;
    private LCCLecturesListAdapter lccLecturesListAdapter;
    ArrayList<String> titles;
    ArrayList<String> durations;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lcclectures_fragment_layout,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.lcclecturesFragmentRecyclerView);
        titles = new ArrayList<>();
        durations = new ArrayList<>();
        lccLecturesListAdapter = new LCCLecturesListAdapter(getActivity(),titles,durations);
        recyclerView.setAdapter(lccLecturesListAdapter);
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

            durations.add(0,"3 min");
            durations.add(1,"6 min");
            durations.add(2,"15 minutes 30 seconds");
            durations.add(3,"10 min");
            durations.add(4,"20 minutes 5 seconds");

            lccLecturesListAdapter.notifyDataSetChanged();
    }
}
