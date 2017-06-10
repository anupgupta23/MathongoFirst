package com.learnacad.learnacad.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learnacad.learnacad.Fragments.Bookmarks_Fragment;
import com.learnacad.learnacad.Fragments.Feed_Fragment;
import com.learnacad.learnacad.Fragments.MyCourses_Fragment;
import com.learnacad.learnacad.Fragments.OfflineVideos_Fragment;

/**
 * Created by Sahil Malhotra on 03-06-2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new Feed_Fragment();
        }else if(position == 1){

            return new MyCourses_Fragment();
        }else if(position == 2){

            return new Bookmarks_Fragment();
        }else {

            return new OfflineVideos_Fragment();
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 4;
    }
}
