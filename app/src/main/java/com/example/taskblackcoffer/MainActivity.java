package com.example.taskblackcoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.ssw.linkedinmanager.dto.LinkedInAccessToken;
import com.ssw.linkedinmanager.dto.LinkedInEmailAddress;
import com.ssw.linkedinmanager.dto.LinkedInUserProfile;
import com.ssw.linkedinmanager.events.LinkedInManagerResponse;
import com.ssw.linkedinmanager.ui.LinkedInRequestManager;

public class MainActivity extends AppCompatActivity {
    ImageView google,fb;
    EditText email,password;
    Button   login;
    TextView signup;
    int RC_SIGN_IN=0;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        google=findViewById(R.id.imageView);
        fb=findViewById(R.id.imageView2);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup );




        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkdin();
            }
        });



        SignInButton signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void linkdin() {
        LinkedInRequestManager linkedInRequestManager = new LinkedInRequestManager(
                this, new LinkedInManagerResponse() {
            @Override
            public void onGetAccessTokenFailed() {
                  Log.i("dshcbsd","onGetAccessTokenFailed");
            }

            @Override
            public void onGetAccessTokenSuccess(LinkedInAccessToken linkedInAccessToken) {
                Log.i("dshcbsd","onGetAccessTokenFailed 2");
            }

            @Override
            public void onGetCodeFailed() {
                Log.i("dshcbsd","onGetAccessTokenFailed 3");
            }

            @Override
            public void onGetCodeSuccess(String code) {
                Log.i("dshcbsd","onGetAccessTokenFailed 4");
            }

            @Override
            public void onGetProfileDataFailed() {
                Log.i("dshcbsd","onGetAccessTokenFailed 5");
            }

            @Override
            public void onGetProfileDataSuccess(LinkedInUserProfile linkedInUserProfile) {
                Log.i("dshcbsd","onGetAccessTokenFailed 6");
            }

            @Override
            public void onGetEmailAddressFailed() {
                Log.i("dshcbsd","onGetAccessTokenFailed 7");
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetEmailAddressSuccess(LinkedInEmailAddress linkedInEmailAddress) {
                Log.i("dshcbsd","onGetAccessTokenFailed 8");
               String email =  linkedInEmailAddress.getEmailAddress(); // User's email address
                Toast.makeText(MainActivity.this, ""+email, Toast.LENGTH_SHORT).show();
            }
        }, "81cub3e9w2rwg1", "DGBX95JgxjIjia2c", "https://github.com/Amirkhan5949/TaskBlackcoffer");

        linkedInRequestManager.showAuthenticateView(LinkedInRequestManager.MODE_EMAIL_ADDRESS_ONLY);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            Log.i("adasfsd", "handleSignInResult: "+e.toString());
        }
    }
}
