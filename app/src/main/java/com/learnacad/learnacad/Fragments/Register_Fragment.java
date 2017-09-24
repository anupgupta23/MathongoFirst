package com.learnacad.learnacad.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.learnacad.learnacad.Models.Student;
import com.learnacad.learnacad.Networking.Api_Urls;
import com.learnacad.learnacad.R;
import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Sahil Malhotra on 15-06-2017.
 */

public class Register_Fragment extends Fragment {

    private static View view;
    ViewPager viewPager;
    String name,email,password,pincode,mobileNum,classChosen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if(view != null){

            ViewGroup parent = (ViewGroup) view.getParent();

            if(parent != null){

                parent.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.fragment_register,container,false);
        }catch (InflateException e){


        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final TextInputEditText nameEditText = (TextInputEditText) view.findViewById(R.id.editTextNameRegisterFragment);
        final TextInputEditText emailEditText = (TextInputEditText) view.findViewById(R.id.editTextEmailRegisterFragment);
        final TextInputEditText passwordEditText = (TextInputEditText) view.findViewById(R.id.editTextCreatePasswordRegisterFragment);
        final TextInputEditText pincodeEditText = (TextInputEditText) view.findViewById(R.id.editTextPincodeRegisterFragment);
        final TextInputEditText mobileNumEditText = (TextInputEditText) view.findViewById(R.id.editTextmobileNumRegisterFragment);
        Typeface typefaceRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface typefaceMedium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Medium.ttf");
        nameEditText.setTypeface(typefaceRegular);
        emailEditText.setTypeface(typefaceRegular);
        passwordEditText.setTypeface(typefaceRegular);
        pincodeEditText.setTypeface(typefaceRegular);
        mobileNumEditText.setTypeface(typefaceRegular);
        TextView alreadyRegisteredTV = (TextView) view.findViewById(R.id.textView2);
        alreadyRegisteredTV.setTypeface(typefaceRegular);

        //  viewPager = (ViewPager) getActivity().findViewById(R.id.loginActivityViewPager);
        Button registerButton = (Button) view.findViewById(R.id.buttonRegisterRegisterFragment);
        Button loginButton = (Button) view.findViewById(R.id.RegisterLoginButton);

        loginButton.setTypeface(typefaceMedium);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment();
            }
        });

        ArrayList<String> classes = new ArrayList<>();
        classes.add("Class");
        classes.add("VIII");
        classes.add("IX");
        classes.add("X");
        classes.add("XI");
        classes.add("XII");
        classes.add("XII Pass");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,classes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerRegisterFragment);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0,true);
        View v = spinner.getSelectedView();
        ((TextView)v).setTextColor(Color.WHITE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)view).setTextColor(Color.WHITE);
                classChosen = adapterView.getItemAtPosition(i).toString();

               // Toast.makeText(getActivity(), classSelected , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = nameEditText.getText().toString().trim();
                email = emailEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                pincode = pincodeEditText.getText().toString().trim();
                mobileNum = mobileNumEditText.getText().toString().trim();

                if(name.isEmpty()){

                    nameEditText.setError("Enter Name");
                    return;
                }

                if (email.isEmpty()){


                    emailEditText.setError("Enter email");
                    return;
                }

                if (password.isEmpty()){

                    passwordEditText.setError("Enter Password");
                    return;
                }

                if(pincode.isEmpty() || (pincode.length() < 6)){

                    pincodeEditText.setError("Enter a valid picncode");
                    return;
                }

                if(!isValidEmail(email)){

                    emailEditText.setError("Enter a valid email address");
                    return;
                }


                if(mobileNum.isEmpty() || mobileNum.length() < 10){

                    mobileNumEditText.setError("Enter a valid mobile number");
                    return;
                }

                if(classChosen.contentEquals("Class")){

                    new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No class selected.\n Please select a class to continue.")
                            .show();

                    return;
                }


                Student student = new Student(name,email,password,mobileNum,classChosen,pincode);
                SugarRecord.save(student);
                nameEditText.setText("");
                emailEditText.setText("");
                passwordEditText.setText("");
                mobileNumEditText.setText("");
                pincodeEditText.setText("");

                if(isConnected()){

                    registerUser(name,email,password,mobileNum,pincode,classChosen);


                }else{
                    Snackbar snackbar1 = Snackbar.make(view,"No Internet Connection.Please try again.",Snackbar.LENGTH_LONG);
                    View view1 = snackbar1.getView();
                    TextView textView = (TextView) view1.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.redcircle,0,0,0);
                    textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(android.support.design.R.dimen.design_snackbar_padding_horizontal));
                    snackbar1.show();
                    return;

                }



            }
        });

    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void registerUser(String name, final String email, final String password,String mobileNum,String pincode,String classChosen) {

        AndroidNetworking.post(Api_Urls.BASE_URL+"signup/student")
                .addUrlEncodeFormBodyParameter("name",name)
                .addUrlEncodeFormBodyParameter("email",email)
                .addUrlEncodeFormBodyParameter("password",password)
                .addUrlEncodeFormBodyParameter("contact",mobileNum)
                .addUrlEncodeFormBodyParameter("pincode",pincode)
                .addUrlEncodeFormBodyParameter("class",classChosen)
                .setTag("RegisterRequest")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("lalala",response + "");

                        try {
                            String success = response.getString("success");
                            if (success.contentEquals("true")){



                                new SweetAlertDialog(getActivity(),SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Registered Successfully!")
                                        .setContentText("You have registered successfully.\nPlease login to your account.")
                                        .show();

                                        replaceFragment();



                            }else{


                                new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("It seems you have already registered.\nPlease login to your account")
                                        .show();
                            }

                     //       viewPager.setCurrentItem(0);


                        } catch (JSONException e) {
                            new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                    .setContentText("There seems a problem with us.\nPlease try again later.")
                                    .setTitleText("Oops..!!")
                                    .show();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                .setContentText("There seems a problem with your internet connection.\nPlease try again later.")
                                .setTitleText("Oops..!!")
                                .show();

                    }
                });
    }


    void replaceFragment(){


        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out);
        fragmentTransaction.setCustomAnimations(R.anim.enter,R.anim.exit);
        fragmentTransaction.replace(R.id.content_login_frame,new Login_Fragment());
        fragmentTransaction.commit();

    }

    public boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();


        return (activeNetwork != null && (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE));

    }

}
