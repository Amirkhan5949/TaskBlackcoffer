package com.example.taskblackcoffer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private ImageView fb;
    private EditText email, password;
    private Button login;
    private TextView signup;
    private int RC_SIGN_IN = 0;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(
                        LoginActivity.this,
                        Arrays.asList("email", "public_profile")
                );
            }
        });

        loginfb();

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        googleopt();


    }

    private void googleopt() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void init() {
        fb = findViewById(R.id.imageView2);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

    }

    private void loginfb() {

        callbackManager = CallbackManager.Factory.create();

        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(LoginActivity.this, "successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(LoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Toast.makeText(this, "Succesfull", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {


            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            Log.i("adasfsd", "handleSignInResult: " + e.toString());
        }
    }

}
