package com.learnacad.learnacad.Fragments;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.learnacad.learnacad.Activities.BaseActivity;
import com.learnacad.learnacad.Adapters.ChipsViewAdapeter;
import com.learnacad.learnacad.Adapters.LibraryCourseListAdapter;
import com.learnacad.learnacad.Models.CONSTANTS;
import com.learnacad.learnacad.Models.Filter;
import com.learnacad.learnacad.Models.FiltersViewModel;
import com.learnacad.learnacad.Models.Minicourse;
import com.learnacad.learnacad.Models.SessionManager;
import com.learnacad.learnacad.Models.Tutor;
import com.learnacad.learnacad.Networking.Api_Urls;
import com.learnacad.learnacad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.orm.SugarRecord.listAll;

/**
 * Created by Sahil Malhotra on 19-06-2017.
 */

public class LibraryCourseListFragment extends Fragment implements ChipsViewAdapeter.onChipDeleted{

    RecyclerView recyclerView;
    LibraryCourseListAdapter listAdapter;
    RelativeLayout relativeLayout;
    ChipsViewAdapeter chipsViewAdapeter;
    ArrayList<Minicourse> minicoursesList;
    ArrayList<Tutor> tutors;
    ArrayList<String> chipsTitles;
    private static View view;
    TextView filterTextview;
    CardView chipsCardView;
    RecyclerView chipRecyclerView;
    Filter f;
    Bundle b;
    FiltersViewModel mViewModel;

    boolean ans;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("lolo","onCreateView LibraryCourseListFragment");

        if(view != null){

            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){

                Log.d("lolo","Inside  if(parent != null) ");

                parent.removeAllViews();
            }
        }


        try {

            Log.d("lolo","Inside TRY ");

            view = inflater.inflate(R.layout.courses_list_library_fragment_layout,container,false);
        }catch (InflateException e){

            Log.d("lolo","Inside CATCH ");

        }

        setHasOptionsMenu(true);
        mViewModel = ViewModelProviders.of(getActivity()).get(FiltersViewModel.class);

        recyclerView = (RecyclerView) view.findViewById(R.id.LibraryCourseListRecyclerView);
        minicoursesList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        tutors = new ArrayList<>();
//        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        listAdapter = new LibraryCourseListAdapter(getActivity(),minicoursesList,swipeRefreshLayout,tutors);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        getActivity().setTitle("Library");
        BaseActivity.getMyCourses();

        chipsTitles = new ArrayList<>();

        filterTextview = (TextView) view.findViewById(R.id.filterTextView);
         chipRecyclerView = (RecyclerView) view.findViewById(R.id.internalLinearLayout);
        chipsCardView = (CardView) view.findViewById(R.id.cardView1);
        chipsViewAdapeter = new ChipsViewAdapeter(getActivity(),chipsTitles,chipsCardView,this);
        chipRecyclerView.setAdapter(chipsViewAdapeter);


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        chipRecyclerView.setLayoutManager(layoutManager);


        chipsCardView.setVisibility(View.GONE);



        b = getArguments();
        setData(b);

        return view;
    }

    private void setData(Bundle b) {

        if(b !=  null){


            final ArrayList<String> checkedItems = b.getStringArrayList("checkedItems");

            Filter filter = (Filter) b.getSerializable("filterObject");

            fetchDataWithFilters(checkedItems);



        //    ChipView chipView = (ChipView) filterLayout.findViewById(R.id.chipView);

            if(checkedItems != null && checkedItems.size() > 0) {

                chipsTitles.clear();
                chipsTitles.addAll(checkedItems);
                chipsViewAdapeter.notifyDataSetChanged();
                Log.d("uiui","chipTitles " + chipsTitles.size());
                chipsCardView.setVisibility(View.VISIBLE);

            }

        }else{

            fetchData();

          //  fetchDataWithFilters();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_library,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.notificationsFilters){

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Filters filters = new Filters();
            Bundle alreadyCheckedItemsBundle = new Bundle();
            alreadyCheckedItemsBundle.putStringArrayList("alreadyChecked",chipsTitles);
            filters.setArguments(alreadyCheckedItemsBundle);
            ft.replace(R.id.content_frame, filters);
            ft.addToBackStack(null).commit();

        }

        return true;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                minicoursesList.clear();
                tutors.clear();

                if(chipsCardView.getVisibility() == View.VISIBLE) {

                    chipsCardView.setVisibility(View.GONE);
                    fetchData();

                }else{

                    fetchData();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void fetchData() {

        pDialog.setMessage("Loading...");
        showDialog();
        minicoursesList.clear();
        tutors.clear();
        mViewModel.clearall();

        List<SessionManager> session = listAll(SessionManager.class);
        AndroidNetworking.get(Api_Urls.BASE_URL + "api/minicourses")
                .addHeaders("Authorization","Bearer " + session.get(0).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("lalala", String.valueOf(response));

                        try {



                            Log.d("looo", String.valueOf(response.length()));

                            for(int i = 0; i < response.length(); ++i){

                                JSONObject object = response.getJSONObject(i);

                                Log.d("toing",object.toString());



                                int minicourse_id = object.getInt("id");
                                String minicourse_name = object.getString("name");

                                String minicourse_description = object.getString("description");
                                String minicourse_rating = object.getString("rating");



                                Minicourse minicourse = new Minicourse();
                                minicourse.setCourse_id(minicourse_id);
                                minicourse.setName(minicourse_name);
                                minicourse.setDescription(minicourse_description);
                                minicourse.setRating(Float.parseFloat(minicourse_rating));
                                StringBuilder builder = new StringBuilder();


                                JSONArray categories = object.getJSONArray("minicoursecategories");
                                for(int j = 0; j < categories.length(); ++j){

                                    JSONObject catObject = categories.getJSONObject(j);
                                    JSONObject lastObjtoCat = catObject.getJSONObject("category");

                                    builder.append(lastObjtoCat.getString("categoryName"));

                                }

                                Log.d("checkCat",builder.toString());

                                minicourse.setEnrolled(false);
                                minicourse.setRelevance(builder.toString());
                                minicoursesList.add(minicourse);

                                JSONObject tutorObj = object.getJSONObject("tutor");
                                Tutor tutor = new Tutor(tutorObj.getInt("id"),tutorObj.getString("name"),tutorObj.getString("description"));

                                Log.d("lalala",minicourse_name);
                                Log.d("lalala","inside loop");


                                tutors.add(tutor);



                            }

                            listAdapter.notifyDataSetChanged();
                            hideDialog();
                        } catch (JSONException e) {
                            new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                    .setContentText("There seems a problem with us.\nPlease try again later.")
                                    .setTitleText("Oops..!!")
                                    .show();
                        }

                           hideDialog();


                    }

                    @Override
                    public void onError(ANError anError) {

                        new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                                .setTitleText("Oops..!!")
                                .show();

                        hideDialog();
                    }
                });


        }


    private void showDialog(){

        if(!pDialog.isShowing()){
            pDialog.show();
        }

    }

    private void hideDialog(){

        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }




    private void fetchDataWithFilters(final ArrayList<String> checkedItems) {


        pDialog.setMessage("Loading...");
        showDialog();

        minicoursesList.clear();
        tutors.clear();

        String generatedStringtoSend = generateRequiredFilterString(checkedItems);

        Log.d("lolololo",generatedStringtoSend);

        if(generatedStringtoSend.isEmpty()){
            fetchData();
            return;
        }


        List<SessionManager> session = listAll(SessionManager.class);


        AndroidNetworking.post(Api_Urls.BASE_URL + "api/minicourses/withFilters")
                .setContentType("application/x-www-form-urlencoded")
                .addStringBody(generatedStringtoSend)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("lalala", String.valueOf(response));

                        try {



                            Log.d("looo", String.valueOf(response.length()));

                            if(response.length() == 0){

                                chipsCardView.setVisibility(View.GONE);
                                checkedItems.clear();

                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.content_frame,new NoFilterResultFragment());
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                            for(int i = 0; i < response.length(); ++i){

                                JSONObject object = response.getJSONObject(i);

                                Log.d("toing",object.toString());

                                Log.d("ioioio",response.length() + "");

                                int minicourse_id = object.getInt("id");
                                String minicourse_name = object.getString("name");

                                String minicourse_description = object.getString("description");
                                double minicourse_rating = object.getDouble("rating");



                                Minicourse minicourse = new Minicourse();
                                minicourse.setCourse_id(minicourse_id);
                                minicourse.setName(minicourse_name);
                                minicourse.setDescription(minicourse_description);
                                minicourse.setRating((float) minicourse_rating);
                                StringBuilder builder = new StringBuilder();


                                JSONArray categories = object.getJSONArray("minicoursecategories");
                                for(int j = 0; j < categories.length(); ++j){

                                    JSONObject catObject = categories.getJSONObject(j);
                                    JSONObject lastObjtoCat = catObject.getJSONObject("category");

                                    builder.append(lastObjtoCat.getString("categoryName"));

                                }

                                Log.d("checkCat",builder.toString());

                                minicourse.setEnrolled(false);
                                minicourse.setRelevance(builder.toString());
                                minicoursesList.add(minicourse);

                                JSONObject tutorObj = object.getJSONObject("tutor");
                                Tutor tutor = new Tutor(tutorObj.getInt("id"),tutorObj.getString("name"),tutorObj.getString("description"));

                                Log.d("lalala",minicourse_name);
                                Log.d("lalala","inside loop");


                                tutors.add(tutor);



                            }

                            listAdapter.notifyDataSetChanged();
                            hideDialog();
                        } catch (JSONException e) {
                            new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                    .setContentText("There seems a problem with us.\nPlease try again later.")
                                    .setTitleText("Oops..!!")
                                    .show();
                        }

                        hideDialog();


                    }

                    @Override
                    public void onError(ANError anError) {

                        new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                                .setTitleText("Oops..!!")
                                .show();

                        hideDialog();
                    }
                });
    }

    private String generateRequiredFilterString(ArrayList<String> checkedItems) {

            StringBuilder filterString = new StringBuilder();

            for(int i = 0; i < checkedItems.size(); ++i){

                filterString.append("filter");

                if(checkedItems.get(i).contentEquals("Physics")){

                    filterString.append("[subjectObject][]=" + CONSTANTS.SUBJECT_PHYSICS + "&");
                }

                if(checkedItems.get(i).contentEquals("Chemistry")){

                    filterString.append("[subjectObject][]=" + CONSTANTS.SUBJECT_CHEMISTRY + "&");
                }

                if(checkedItems.get(i).contentEquals("Maths")){

                    filterString.append("[subjectObject][]=" + CONSTANTS.SUBJECT_MATHS + "&");
                }


                if(checkedItems.get(i).contentEquals("JEE Mains")){

                    filterString.append("[categoryObject][]=" + CONSTANTS.CATEGORY_MAINS + "&");
                }

                if(checkedItems.get(i).contentEquals("JEE Advanced")){

                    filterString.append("[categoryObject][]=" + CONSTANTS.CATEGORY_ADVANCED + "&");
                }

                if(checkedItems.get(i).contentEquals("CBSE")){

                    filterString.append("[categoryObject][]=" + CONSTANTS.CATEGORY_CBSE + "&");
                }


                if(checkedItems.get(i).contentEquals("XII")){

                    filterString.append("[classObject][]=" + CONSTANTS.CLASS_12 + "&");
                }

                if(checkedItems.get(i).contentEquals("XI")){

                    filterString.append("[classObject][]=" + CONSTANTS.CLASS_11 + "&");
                }

                if(checkedItems.get(i).contentEquals("X")){

                    filterString.append("[classObject][]=" + CONSTANTS.CLASS_10 + "&");
                }


                if(checkedItems.get(i).contentEquals("Beginner")){

                    filterString.append("[difficultyObject][]=" + "Beginner&");
                }

                if(checkedItems.get(i).contentEquals("Intermediate")){

                    filterString.append("[difficultyObject][]=" + "Intermediate&");
                }

                if(checkedItems.get(i).contentEquals("Advanced")){

                    filterString.append("[difficultyObject][]=" + "Advance&");
                }


                if(checkedItems.get(i).contentEquals("English")){

                    filterString.append("[mediumObject][]=" + "English&");
                }

                if(checkedItems.get(i).contentEquals("Hindi")){

                    filterString.append("[mediumObject][]=" + "Hindi&");
                }

            }

            int index = filterString.lastIndexOf("&");

        if(index >= 0) {
                filterString.deleteCharAt(index);
            }
            Log.d("ioioio",filterString.toString());

        return filterString.toString();
    }

    @Override
    public void justCallingFunction(ArrayList<String> chips) {

        if(chips.size() > 0 && chipsCardView.getVisibility() == View.GONE) {

            chipsCardView.setVisibility(View.VISIBLE);
        }
        fetchDataWithFilters(chips);

    }

    //  url = "filter[classObject][]=2&filter[categoryObject][]=1&filter[categoryObject][]=2"

    public boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();


        return (activeNetwork != null && (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE));

    }
}
