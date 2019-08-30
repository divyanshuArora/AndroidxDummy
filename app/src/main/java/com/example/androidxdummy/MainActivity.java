package com.example.androidxdummy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity{
//////First add android.useAndroidX=true and  android.enableJetifier=true in gradle.properties
    Button googleSignIn;
    GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    CallbackManager callbackManager;
    LoginButton FB_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions  googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        callbackManager = CallbackManager.Factory.create();



        googleSignIn = findViewById(R.id.googleSignIn);

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInMethod();
            }
        });
        FB_login_button = findViewById(R.id.FB_login_button);
//        FB_login_button.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));
        FB_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginWithFB();
            }
        });

    }


    //////////////////////google signIn start
    private void GoogleSignInMethod()
    {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(googleSignInAccountTask);
        }



    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> googleSignInAccountTask)
    {
        try
        {
                GoogleSignInAccount googleSignInAccount = googleSignInAccountTask.getResult(ApiException.class);
                  String name = googleSignInAccount.getDisplayName();
                  String email = googleSignInAccount.getEmail();

            Log.d("MainActivity", "onStart: "+"name "+name+" email "+email);
                Log.d("MainActivity", "handleSignInResult: "+"Success");
                Toast.makeText(this, "Successful "+name, Toast.LENGTH_SHORT).show();
                Intent xyz = new Intent(getApplicationContext(),Dash.class);
                startActivity(xyz);
                finish();

        }
        catch (Exception e)
        {
            Log.d("MainActivity", "handleSignInResult Exception: "+e );
        }



    }
//////////////////////google signIn over

/////////////
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // [START on_start_sign_in]
//        // Check for existing Google Sign In account, if the user is already signed in
//        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        String name = account.getDisplayName();
//        String email = account.getEmail();
//
//        Log.d("MainActivity", "onStart: "+"name "+name+"email "+email);
//        Intent login = new Intent(MainActivity.this,Dash.class);
//        startActivity(login);
//        finish();
//
//        // [END on_start_sign_in]
//    }



  public void LoginWithFB()
    {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


//                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                Log.d("Main", "onSuccess: "+response);
//
//                                try {
//                                     email = object.getString("email");
//                                     name = object.getString("name");
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                        graphRequest.executeAsync();
                        Log.d("Main", "onSuccess: ");
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Main", "onCancel: ");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("Main", "onException: "+exception);
                    }
                });





    }






}
