package com.learnacad.learnacad.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.learnacad.learnacad.Fragments.Login_Fragment;
import com.learnacad.learnacad.Fragments.Register_Fragment;

/**
 * Created by Sahil Malhotra on 13-06-2017.
 */

public class LoginViewPagerAdapter extends ViewPagerAdapter {



    @Override
    public Fragment getItem(int position) {

        if(position == 0){

            return new Login_Fragment();
        }else{

            return new Register_Fragment();
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 2;
    }

    public LoginViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
}
