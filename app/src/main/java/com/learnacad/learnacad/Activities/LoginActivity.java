package com.learnacad.learnacad.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.androidnetworking.AndroidNetworking;
import com.learnacad.learnacad.Fragments.Login_Fragment;
import com.learnacad.learnacad.Fragments.OnBoardingFragment;
import com.learnacad.learnacad.Models.SessionManager;
import com.learnacad.learnacad.Models.SharedPrefManager;
import com.learnacad.learnacad.R;

import java.util.List;

import static com.orm.SugarRecord.listAll;

public class LoginActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        frameLayout = (FrameLayout) findViewById(R.id.content_login_frame);



        AndroidNetworking.initialize(getApplicationContext());

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.splashRelativeLayout);


        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        if(sharedPrefManager.isFirstTimeLaunch()) {

            Log.d("-=-=-","Inside if part of if shared");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_login_frame,new OnBoardingFragment());
            fragmentTransaction.commit();

            sharedPrefManager.setFirstTimeLaunch(false);
        }else{

            List<SessionManager> sessionManager = listAll(SessionManager.class);
            Log.d("-=-=-","Inside if part of else shared");
            if (sessionManager != null) {

                if (sessionManager.size() > 0) {

                    startActivity(new Intent(this, BaseActivity.class));
                    finish();
                }else{

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Log.d("-=-=-","Inside else part of else shared");
                    fragmentTransaction.replace(R.id.content_login_frame, new Login_Fragment());
                    fragmentTransaction.commit();

                }
            } else {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Log.d("-=-=-","Inside else part of else shared");
                fragmentTransaction.replace(R.id.content_login_frame, new Login_Fragment());
                fragmentTransaction.commit();
            }

        }

        frameLayout.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                relativeLayout.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
            }
        },2329);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


}
