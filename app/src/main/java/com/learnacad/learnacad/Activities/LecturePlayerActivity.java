package com.learnacad.learnacad.Activities;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.learnacad.learnacad.Adapters.LecturePlayerViewPagerAdapter;
import com.learnacad.learnacad.R;

public class LecturePlayerActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{

    public static final String GOOGLE_DEVELOPER_KEY = "AIzaSyBrJtaqoS-xR6LdGTdlSHJm7pp8pBTGrEE";
    public static final String YOUTUBE_CODE = "K3lviiNR0pI";
    YouTubePlayer player;
    ViewPager viewPager;
    TabLayout tabLayout;
    private String[] tabTitles = {"LECTURES", "COMMENTS","DOUBTS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

//        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nested);
//        scrollView.setFillViewport(true);

        viewPager = (ViewPager) findViewById(R.id.lecturePlayerViewPager);
        tabLayout = (TabLayout) findViewById(R.id.lecturePlayerTabLayout);

        for(int i = 0; i < 3; ++i){

            tabLayout.addTab(tabLayout.newTab().setText(tabTitles[i]));
        }

        final LecturePlayerViewPagerAdapter viewPagerAdapter = new LecturePlayerViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        YouTubePlayerSupportFragment youTubePlayerSupportFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtubePlayer);
        youTubePlayerSupportFragment.initialize(GOOGLE_DEVELOPER_KEY,this);

        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.BLACK);
        drawable.setSize(2,1);
        drawable.setShape(GradientDrawable.LINE);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);


        final Button upVoteButton = (Button) findViewById(R.id.attachedUpvotesButton);
        final Button shareButton = (Button) findViewById(R.id.attachedshareButton);
        final Button downloadsButton = (Button) findViewById(R.id.attachedDownloadsButton);
        Button reportButton = (Button) findViewById(R.id.attachedUReportButton);
        final Button followButton = (Button) findViewById(R.id.lecturePlayerTeacherFollowButton);

        followButton.setTransformationMethod(null);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                followButton.setBackgroundResource(R.drawable.following_button_shape);
                followButton.setText("Following");
                followButton.setTextColor(Color.parseColor("#ffffffff"));
            }
        });

        upVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upVoteButton.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.upvoteiconactive,0,0);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareButton.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.shareactive,0,0);
            }
        });

        downloadsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadsButton.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.downloadiconactive,0,0);
                downloadsButton.setText("Downloaded");

            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        player = youTubePlayer;

        if(!b){

            player.loadVideo(YOUTUBE_CODE);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        final int REQUEST_CODE = 1;

        if(youTubeInitializationResult.isUserRecoverableError()){

            youTubeInitializationResult.getErrorDialog(this,REQUEST_CODE);
        }else{

            Toast.makeText(this, "Error in initializing the player", Toast.LENGTH_SHORT).show();
        }
    }
}
