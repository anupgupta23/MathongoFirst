package com.learnacad.learnacad.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.learnacad.learnacad.R;

/**
 * Created by Sahil Malhotra on 08-10-2017.
 */

public class OnBoardingLayer2 extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.onboarding_pager_item_layout,container,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.onboardingPagerAdapterImageView);
        imageView.setImageResource(R.drawable.layer2);
        imageView.setBackgroundResource(R.drawable.onboarding2);

        return view;
    }
}
