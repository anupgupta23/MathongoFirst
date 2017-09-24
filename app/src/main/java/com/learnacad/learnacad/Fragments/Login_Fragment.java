package com.learnacad.learnacad.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.learnacad.learnacad.Activities.BaseActivity;
import com.learnacad.learnacad.Models.SessionManager;
import com.learnacad.learnacad.Models.Student;
import com.learnacad.learnacad.Networking.Api_Urls;
import com.learnacad.learnacad.R;
import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.learnacad.learnacad.Fragments.Register_Fragment.isValidEmail;

/**
 * Created by Sahil Malhotra on 13-06-2017.
 */

public class Login_Fragment extends Fragment {

    public static View view;
    String email,password;
    Snackbar snackbar;

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
            view = inflater.inflate(R.layout.fragment_login,container,false);
        }catch (InflateException e){


        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final TextInputEditText emailEditText = (TextInputEditText) view.findViewById(R.id.loginEmailEditText);
        final TextInputEditText passwordEditText = (TextInputEditText) view.findViewById(R.id.loginPasswordEditText);
        Button signUp = (Button) view.findViewById(R.id.loginSingupButton);
        Typeface typefaceMedium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Medium.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        emailEditText.setTypeface(typefaceRegular);
        signUp.setTypeface(typefaceMedium);
        passwordEditText.setTypeface(typefaceRegular);

        snackbar = Snackbar.make(view,null,Snackbar.LENGTH_INDEFINITE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                // fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out);
                fragmentTransaction.setCustomAnimations(R.anim.enter,R.anim.exit);
                fragmentTransaction.replace(R.id.content_login_frame,new Register_Fragment());
                fragmentTransaction.commit();
            }
        });

        Button loginButton = (Button) view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();


                if (password.isEmpty()) {

                    passwordEditText.setError("Enter Password");
                    return;
                }

                if (email.isEmpty()) {


                    emailEditText.setError("Enter email");
                    return;
                }


                if (!isValidEmail(email)) {

                    emailEditText.setError("Enter a valid email address");
                    return;
                }


                if(isConnected()){

                    authorizeUser(email,password,view);


                }else{
                    snackbar.dismiss();
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

    private void authorizeUser(String email, String password, final View rootview) {


        snackbar.setText("Logging you in...");
            View view = snackbar.getView();
            TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.greencircle,0,0,0);
            textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(android.support.design.R.dimen.design_snackbar_padding_horizontal));
            snackbar.show();
        AndroidNetworking.post(Api_Urls.BASE_URL+ "authorize")
                .addUrlEncodeFormBodyParameter("email",email)
                .addUrlEncodeFormBodyParameter("password",password)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("lalala", String.valueOf(response));
                        try {
                            String result = response.getString("success");
                            if(result.contentEquals("true")){

                                String tokenId = response.getString("token");

                                Student student = new Student();

                                student.setName(response.getString("name"));
                                SugarRecord.save(student);

                                SessionManager session = new SessionManager(tokenId);
                                SugarRecord.save(session);
                                snackbar.dismiss();
                                startActivity(new Intent(getActivity(), BaseActivity.class));
                            }
                            else{

                                String message = response.getString("message");

                                snackbar.dismiss();

                                Log.d("snack","inside else " + message);
                                Snackbar snackbar1 = Snackbar.make(rootview,message  + ".Please try again.",Snackbar.LENGTH_LONG);
                                View view = snackbar1.getView();
                                TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.redcircle,0,0,0);
                                textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(android.support.design.R.dimen.design_snackbar_padding_horizontal));
                                snackbar1.show();
//                                snackbar.show();
//                                Toast.makeText(getContext(), message , Toast.LENGTH_SHORT).show();
//                                SweetAlertDialog dialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.SUCCESS_TYPE)
//                                        .setContentText("Happy Learning");
//
//                                dialog.show();
//
//                                dialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);


                            }

                        } catch (JSONException e) {
                            new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                    .setContentText("There seems a problem with us.\nPlease try again later.")
                                    .setTitleText("Oops..!!")
                                    .show();
                          //  snackbar.dismiss();
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

    public boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();


        return (activeNetwork != null && (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE));

    }

}

