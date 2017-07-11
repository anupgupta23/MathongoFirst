package com.learnacad.learnacad.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.learnacad.learnacad.Fragments.Bookmarks_Fragment;
import com.learnacad.learnacad.Fragments.Home_Fragment;
import com.learnacad.learnacad.Fragments.Library_Fragment;
import com.learnacad.learnacad.Fragments.MyCourses_Fragment;
import com.learnacad.learnacad.R;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private  NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  Toolbar bottomToolbar = (Toolbar) findViewById(R.id.toolbarBottom);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,new Home_Fragment());
        fragmentTransaction.commit();

//        drawer.setDrawerShadow();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        for(int i = 0; i < navigationView.getMenu().size(); ++i){

                navigationView.getMenu().getItem(i).setChecked(false);
        }

        item.setChecked(true);

        switch (id){

            case R.id.libraryNavigationDrawer:{

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Log.d("lolo","Inside lbraryNavigationDrawer fragment replacing");
                ft.replace(R.id.content_frame, new Library_Fragment());
                Log.d("lolo","Inside lbraryNavigationDrawer fragment after replace");
                ft.commit();
            }

            case R.id.homeNavigationDrawer:{
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new Home_Fragment());
                ft.commit();
            }
            break;

            case R.id.mybookmarksNavigationDrawer:{
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new Bookmarks_Fragment());
                ft.commit();
            }
            break;

            case R.id.myCoursesNavigationDrawer:{
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new MyCourses_Fragment());
                ft.commit();
            }
            break;

            default:{
                Toast.makeText(this, "LOL", Toast.LENGTH_SHORT).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
