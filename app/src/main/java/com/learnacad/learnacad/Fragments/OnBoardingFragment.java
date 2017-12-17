package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.learnacad.learnacad.Adapters.OnBoardingViewPagerAdapter;
import com.learnacad.learnacad.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sahil Malhotra on 07-10-2017.
 */

public class OnBoardingFragment extends Fragment {
    public static View view;
    private OnBoardingViewPagerAdapter onBoardingViewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Button loginButton;
    private static int NUM = 3;
    private static int currentPage = 3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.onboarding_fragment_layout,container,false);

        viewPager = (ViewPager) view.findViewById(R.id.onboardingFragmentLayoutViewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.onboardingFragmentLayoutTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        loginButton = (Button) view.findViewById(R.id.onboardingFragmentLayoutSloginButton);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        onBoardingViewPagerAdapter = new OnBoardingViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(onBoardingViewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter,R.anim.exit);
                fragmentTransaction.replace(R.id.content_login_frame, new Login_Fragment());
                fragmentTransaction.commit();

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
