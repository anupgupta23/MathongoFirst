package com.learnacad.learnacad.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.learnacad.learnacad.Fragments.LCCDetailsFragment;
import com.learnacad.learnacad.Fragments.LCCLecturesFragment;
import com.learnacad.learnacad.Fragments.LCCMaterialsFragment;
import com.learnacad.learnacad.Fragments.LCCReviewsFragment;

/**
 * Created by Sahil Malhotra on 22-06-2017.
 */

public class LibraryCourseContentViewPagerAdapter extends FragmentStatePagerAdapter {

    Bundle bundle;
    public LibraryCourseContentViewPagerAdapter(FragmentManager fm,Bundle bundle) {
        super(fm);
        this.bundle = bundle;
    }


    @Override
    public Fragment getItem(int position) {

       if(position == 0){

           LCCDetailsFragment detailsFragment = new LCCDetailsFragment();
           detailsFragment.setArguments(bundle);

           return detailsFragment;
       }else if(position == 1){

           LCCLecturesFragment lccLecturesFragment = new LCCLecturesFragment();
           lccLecturesFragment.setArguments(bundle);
           return lccLecturesFragment;
       }else if(position == 2){

           return new LCCMaterialsFragment();
       }

       else{

           return new LCCReviewsFragment();
       }

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;

    }

    @Override
    public int getCount() {
        return 4;
    }
}
