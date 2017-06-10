package com.learnacad.learnacad.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.eralp.circleprogressview.CircleProgressView;
import com.learnacad.learnacad.R;

public class ProfileActivity extends BaseActivity {

    ProgressBar progressBar,linearProgressBar;
    CircleProgressView circleProgressView;
    Button p;
    Button n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

/*
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setMax(100);*//*



        p = (Button) findViewById(R.id.Pprogress);
        n = (Button) findViewById(R.id.Nprogress);

        linearProgressBar = (ProgressBar) findViewById(R.id.linearProgress);

        linearProgressBar.setMax(1000);
        linearProgressBar.setProgress(500);

        circleProgressView = (CircleProgressView) findViewById(R.id.circle_view_progress);
        circleProgressView.setTextEnabled(true);
        circleProgressView.setStartAngle(90);
        circleProgressView.setProgress(20);
*/


/*

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                circleProgressView.setProgressWithAnimation(75,2000);

            }
        });
       *//* n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressWheel.setProgress(-0.25f);
            }
        });*/
    }
}
