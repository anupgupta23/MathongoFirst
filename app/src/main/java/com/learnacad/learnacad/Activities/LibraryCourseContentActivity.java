package com.learnacad.learnacad.Activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learnacad.learnacad.Adapters.LibraryCourseContentViewPagerAdapter;
import com.learnacad.learnacad.R;

public class LibraryCourseContentActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView titleTextView,followersTextView,descriptionTextView,previousToCurr;
    private static String[] tabTitles = {"DETAILS", "LECTURES", "MATERIALS", "REVIEW"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_course_content);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cordinatorLayout_library_course_content);

        titleTextView = (TextView) findViewById(R.id.LibraryCourseItemTitle);
        followersTextView = (TextView) findViewById(R.id.LibraryCourseItemFollowersTextView);
        descriptionTextView = (TextView) findViewById(R.id.LibraryCourseItemDescription);
        previousToCurr = (TextView) findViewById(R.id.previousTOcurrentTextView);

        Typeface typefaceMedium = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        titleTextView.setTypeface(typefaceMedium);
        followersTextView.setTypeface(typefaceMedium);
        previousToCurr.setTypeface(typefaceMedium);
        descriptionTextView.setTypeface(typefaceRegular);

        Toolbar toolbar = (Toolbar) findViewById(R.id.libraryCourseContentToolbar);
        toolbar.setNavigationIcon(R.drawable.leftarrow);
        setSupportActionBar(toolbar);
        this.setTitle("");

        viewPager = (ViewPager) findViewById(R.id.libraryCourseContentViewPager);


        tabLayout = (TabLayout) findViewById(R.id.libraryCourseContentTabLayout);

        for (int i = 0; i < 4; ++i) {

            tabLayout.addTab(tabLayout.newTab().setText(tabTitles[i]));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        LibraryCourseContentViewPagerAdapter pagerAdapter = new LibraryCourseContentViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

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



        final Button enrollbutton = (Button) findViewById(R.id.LibraryCourseItemEnrollButton);
        enrollbutton.setTransformationMethod(null);
        enrollbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enrollbutton.setBackgroundResource(R.drawable.enrolled_button_shape);
                enrollbutton.setText("Enrolled");
                enrollbutton.setTextColor(Color.parseColor("#ff1589ee"));
            }
        });

        final ImageButton bookmarkedButton = (ImageButton) findViewById(R.id.LibraryCourseItemBookMarkButton);


        bookmarkedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookmarkedButton.setImageResource(R.drawable.bookmarkfilled);
                Toast.makeText(LibraryCourseContentActivity.this, "Added", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(coordinatorLayout,"Added to your Bookmarks",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}



