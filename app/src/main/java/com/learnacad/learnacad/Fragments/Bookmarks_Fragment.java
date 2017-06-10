package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.learnacad.learnacad.Adapters.BookmarksListAdapter;
import com.learnacad.learnacad.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 03-06-2017.
 */

public class Bookmarks_Fragment extends Fragment {

    RecyclerView recyclerView;
    BookmarksListAdapter adapter;
    ArrayList<String> titles;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.bookmarks_fragment_layout,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.bookmarksRecyclerViewList);
        titles = new ArrayList<>();
        adapter = new BookmarksListAdapter(getActivity(),titles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fetchTitles();
        return v;
    }

    public void fetchTitles() {

        for(int i = 0; i < 5; ++i){

            titles.add("LOL" + " " + i);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
