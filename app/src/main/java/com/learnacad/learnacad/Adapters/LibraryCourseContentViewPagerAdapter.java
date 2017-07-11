package com.learnacad.learnacad.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learnacad.learnacad.Fragments.LCCDetailsFragment;
import com.learnacad.learnacad.Fragments.LCCLecturesFragment;

/**
 * Created by Sahil Malhotra on 22-06-2017.
 */

public class LibraryCourseContentViewPagerAdapter extends FragmentPagerAdapter {


    public LibraryCourseContentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

       if(position == 0){

           return new LCCDetailsFragment();
       }else if(position == 1){

           return new LCCLecturesFragment();
       }else{

           return new LCCDetailsFragment();
       }

    }


    @Override
    public int getCount() {
        return 4;
    }
}
