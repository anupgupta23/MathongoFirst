package com.learnacad.learnacad.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.learnacad.learnacad.Activities.BaseActivity;
import com.learnacad.learnacad.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sahil Malhotra on 13-06-2017.
 */

public class Login_Fragment extends Fragment {

    public static View view;
    LoginButton loginButtonFacebook;
    CallbackManager callbackManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login,container,false);

        callbackManager = CallbackManager.Factory.create();
        loginButtonFacebook = (LoginButton) view.findViewById(R.id.login_button_facebook);
        loginButtonFacebook.setFragment(this);
        loginButtonFacebook.setReadPermissions("public_profile email");


        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                if(accessToken != null){
                    RequestData(accessToken);
                }

                startActivity(new Intent(getActivity(), BaseActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        return view;
    }

    private void RequestData(AccessToken accessToken) {

        final GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                Log.d("Tag", object + "");
                Log.d("Tag", response + "");

                JSONObject obj = response.getJSONObject();
                String name = null;
                try {
                    name = obj.getString("name");
                    String email = obj.getString("email");
                    Toast.makeText(getActivity(), name + email, Toast.LENGTH_SHORT).show();
                    Log.d("Tag",name + email );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                }




        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

}
