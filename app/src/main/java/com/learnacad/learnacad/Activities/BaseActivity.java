package com.learnacad.learnacad.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.learnacad.learnacad.Fragments.Bookmarks_Fragment;
import com.learnacad.learnacad.Fragments.LibraryCourseListFragment;
import com.learnacad.learnacad.Fragments.Library_Fragment;
import com.learnacad.learnacad.Fragments.MyCourses_Fragment;
import com.learnacad.learnacad.Fragments.NoInternetConnectionFragment;
import com.learnacad.learnacad.Models.MyCoursesEnrolled;
import com.learnacad.learnacad.Models.SessionManager;
import com.learnacad.learnacad.Models.Student;
import com.learnacad.learnacad.Networking.Api_Urls;
import com.learnacad.learnacad.R;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.orm.SugarRecord.listAll;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private  NavigationView navigationView;
    public static MyCoursesEnrolled coursesEnrolled;
    boolean doubleBackPressToExitPressedOnce = false;
    private FirebaseAnalytics firebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        SugarRecord.deleteAll(MyCoursesEnrolled.class);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);

/*        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  Toolbar bottomToolbar = (Toolbar) findViewById(R.id.toolbarBottom);

        List<Student> students = SugarRecord.listAll(Student.class);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        if(isConnected()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, new Library_Fragment());
            fragmentTransaction.commit();
        }else{

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, new NoInternetConnectionFragment());
            fragmentTransaction.commit();
        }


        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.splashRelativeLayout);

        Intent intent = getIntent();
        boolean isSplashDone = intent.getBooleanExtra("SPLASH SHOWN",false);

        if(!isSplashDone) {
            frameLayout.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    relativeLayout.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);

                }
            }, 2329);
        }




//        drawer.setDrawerShadow();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView nameTextViewNavDrawer = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewNameNavigationDrawer);

        String name = students.get(students.size() - 1).getName();
        nameTextViewNavDrawer.setText(name + " ");

                getMyCourses();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            FragmentManager fm = getSupportFragmentManager();

            if (fm.getBackStackEntryCount() == 1) {

                for (int i = 0; i < navigationView.getMenu().size(); ++i) {

                    navigationView.getMenu().getItem(i).setChecked(false);
                }

                if (doubleBackPressToExitPressedOnce) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                    return;
                }
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                this.doubleBackPressToExitPressedOnce = true;


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        doubleBackPressToExitPressedOnce = false;
                    }
                }, 2000);
            } else {

                super.onBackPressed();
            }


        }

        }


    public static void getMyCourses(){



        final ArrayList<Integer> enrolledCourses = new ArrayList<>();



        List<SessionManager> sessions = listAll(SessionManager.class);

        AndroidNetworking.get(Api_Urls.BASE_URL + "api/students/mycourses")
                .addHeaders("Authorization","Bearer " + sessions.get(0).getToken() )
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0; i < response.length(); ++i){

                            try {
                                JSONObject object = response.getJSONObject(i);

                                int idToAdd = object.getInt("minicourseId");

                                if(!enrolledCourses.contains(idToAdd)){

                                    enrolledCourses.add(idToAdd);
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        JSONArray courses = new JSONArray();


                        for(int i = 0; i < enrolledCourses.size(); ++i){

                            courses.put(enrolledCourses.get(i));
                        }

                        coursesEnrolled = new MyCoursesEnrolled();
                        coursesEnrolled.setMycourses(courses.toString());

                        SugarRecord.save(coursesEnrolled);

                        Log.d("789456123","Inside getMyCourse done");


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
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

                if(!isConnected()){

                    new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                            .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                            .setTitleText("Oops..!!")
                            .show();
                            return true;
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,new LibraryCourseListFragment());
                fragmentTransaction.addToBackStack(null).commit();
            }
            break;

/*            case R.id.homeNavigationDrawer:{
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new Home_Fragment());
                ft.commit();
            }
            break;*/

            case R.id.mybookmarksNavigationDrawer:{


                if(!isConnected()){

                    new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                            .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                            .setTitleText("Oops..!!")
                            .show();
                    return true;
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new Bookmarks_Fragment());
                ft.commit();
            }
            break;

            case R.id.myCoursesNavigationDrawer:{

                if(!isConnected()){

                    new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                            .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                            .setTitleText("Oops..!!")
                            .show();
                    return true;
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new MyCourses_Fragment());
                ft.commit();
            }
            break;

            case R.id.logoutNavigationDrawer:{

                if(!isConnected()){

                    new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                            .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                            .setTitleText("Oops..!!")
                            .show();
                    return true;
                }

               SweetAlertDialog dialog =  new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setConfirmText("Logout!?")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                LogoutAsyncTask logoutTask = new LogoutAsyncTask();
                                logoutTask.execute(Api_Urls.BASE_URL);
                            }
                        });

                dialog.setCancelable(true);
                dialog.show();



            }
            break;

            default:{

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(boolean message) {

        Log.d("tutu","Logout functin called " + message);

        if(message) {
            SugarRecord.deleteAll(SessionManager.class);
            SugarRecord.deleteAll(Student.class);
            startActivity(new Intent(this, LoginActivity.class));
        }else{

            new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                    .setContentText("There occured a problem,Please try again later.")
                    .setTitleText("Oops..")
                    .show();
        }
    }



    public boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();


        return (activeNetwork != null && (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE));

    }



    public class LogoutAsyncTask extends AsyncTask<String,Void,String>  {


        @Override
        protected String doInBackground(String... params) {
           StringBuffer sb = new StringBuffer();

            try {
                URL url = new URL(Api_Urls.BASE_URL + "logout");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

                if(inputStream == null){
                    return null;
                }

                Scanner sc = new Scanner(inputStream);

                while(sc.hasNext()){

                    sb.append(sc.nextLine());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

                Log.d("tutu",s);

            try {
                JSONObject obj = new JSONObject(s);
                String success = obj.getString("success");
                Log.d("tutu","success = " + success);
                if(success.contentEquals("true")){

                    logout(true);
                }else{

                    logout(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
