package com.learnacad.learnacad.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learnacad.learnacad.Fragments.OnBoardingLayer1;
import com.learnacad.learnacad.Fragments.OnBoardingLayer2;
import com.learnacad.learnacad.Fragments.OnBoardingLayer3;

/**
 * Created by Sahil Malhotra on 08-10-2017.
 */

public class OnBoardingViewPagerAdapter extends FragmentPagerAdapter {

    public OnBoardingViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){

            return new OnBoardingLayer1();
        }else if(position == 1){

            return new OnBoardingLayer2();

        }else{

            return new OnBoardingLayer3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
