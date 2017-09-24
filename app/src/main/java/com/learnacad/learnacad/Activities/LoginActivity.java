package com.learnacad.learnacad.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.androidnetworking.AndroidNetworking;
import com.learnacad.learnacad.Fragments.Login_Fragment;
import com.learnacad.learnacad.Models.SessionManager;
import com.learnacad.learnacad.R;

import java.util.List;

import static com.orm.SugarRecord.listAll;

public class LoginActivity extends AppCompatActivity {

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        frameLayout = (FrameLayout) findViewById(R.id.content_login_frame);

        AndroidNetworking.initialize(getApplicationContext());

        List<SessionManager> sessionManager = listAll(SessionManager.class);

        if(sessionManager != null) {

            if (sessionManager.size() > 0) {

                startActivity(new Intent(this, BaseActivity.class));
                finish();
            }
        }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_login_frame, new Login_Fragment());
            fragmentTransaction.commit();


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
