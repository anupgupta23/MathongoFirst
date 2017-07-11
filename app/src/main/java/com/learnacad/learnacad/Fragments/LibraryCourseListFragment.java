package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.learnacad.learnacad.Adapters.LibraryCourseListAdapter;
import com.learnacad.learnacad.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 19-06-2017.
 */

public class LibraryCourseListFragment extends Fragment {

    RecyclerView recyclerView;
    LibraryCourseListAdapter listAdapter;
    RelativeLayout relativeLayout;
    ArrayList<String> titleList;
    private static View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("lolo","onCreateView LibraryCourseListFragment");

        if(view != null){

            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){

                Log.d("lolo","Inside  if(parent != null) ");

                parent.removeAllViews();
            }
        }


        try {

            Log.d("lolo","Inside TRY ");

            view = inflater.inflate(R.layout.courses_list_library_fragment_layout,container,false);
        }catch (InflateException e){

            Log.d("lolo","Inside CATCH ");

        }



        recyclerView = (RecyclerView) view.findViewById(R.id.LibraryCourseListRecyclerView);
        titleList = new ArrayList<>();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        listAdapter = new LibraryCourseListAdapter(getActivity(),titleList,relativeLayout);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        fetchData();
        return view;
    }

    private void fetchData() {

        for(int i = 0; i < 5; ++i){

            titleList.add("Title" + i);
        }
        listAdapter.notifyDataSetChanged();
    }


}
