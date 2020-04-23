package com.example.taskblackcoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskblackcoffer.authUtils.Common;
import com.example.taskblackcoffer.authUtils.callback.BooleanCallback;
import com.example.taskblackcoffer.utils.Constants;
import com.example.taskblackcoffer.utils.Loader;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {


    private ImageView fb;
    private EditText email,password;
    private Button register;
    private TextView signup;
    private int RC_SIGN_IN=0;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private SignInButton signInButton;
    private static final String EMAIL = "email";
    private Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        init();

        loginfb();

        googleopt();

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(
                        RegistrationActivity.this,
                        Arrays.asList("email", "public_profile")
                );
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = RegistrationActivity.this.email.getText().toString().replace(" ","");
                final String password = RegistrationActivity.this.password.getText().toString().replace(" ","");

                if(email.length()==0){
                    Toast.makeText(RegistrationActivity.this, "Enter email address.", Toast.LENGTH_SHORT).show();
                }
                else if(!Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$").matcher(email).matches()){
                    Toast.makeText(RegistrationActivity.this, "Enter valid email address.", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegistrationActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6){
                    Toast.makeText(RegistrationActivity.this, "Enter right password", Toast.LENGTH_SHORT).show();
                }
                else {
                    loader.show();
                    Common.checkEmailRegistration(email, new BooleanCallback() {
                        @Override
                        public void callback(boolean isValid) {
                            if(isValid){
                                loader.dismiss();
                                Intent intent = new Intent(RegistrationActivity.this,PhoneAuthentication.class);
                                intent.putExtra("email",email);
                                intent.putExtra("password",password);
                                intent.putExtra("authType",Constants.AuthType.EMAIL);
                                intent.putExtra("auth",Constants.Auth.REGISTRATION);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                loader.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Email already exist.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
            }
        });



    }
    private void init() {
        fb = findViewById(R.id.imageView2);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        signup = findViewById(R.id.signup);
        signInButton = findViewById(R.id.sign_in_button);
        loader = new Loader(this);
    }

    private void loginfb() {

        callbackManager = CallbackManager.Factory.create();

        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                loader.show();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                String email = "";
                                String id = "";

                                try {
                                    email = object.getString("email");

                                }
                                catch (JSONException e) {
                                    e.printStackTrace();

                                }
                                try {
                                    id= object.getString("id");


                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }

                                if ((!email.equals(""))&&(!id.equals(""))){
                                    final String finalId = id;
                                    final String finalEmail = email;
                                    Common.checkEmailRegistration(email, new BooleanCallback() {
                                       @Override
                                       public void callback(boolean isValid) {
                                           if(isValid){
                                               loader.dismiss();
                                               Intent intent=new Intent(RegistrationActivity.this,PasswordActivity.class);
                                               intent.putExtra("Fid", finalId);
                                               intent.putExtra("email", finalEmail);
                                               intent.putExtra("authType",Constants.AuthType.FACEBOOK);
                                               intent.putExtra("auth",Constants.Auth.REGISTRATION);
                                               startActivity(intent);
                                               finish();
                                           }
                                           else {
                                               loader.dismiss();
                                               Toast.makeText(RegistrationActivity.this, "You already registeered.", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   });
                                }
                                else if (email.equals("")&&(!id.equals(""))){
                                    Intent intent=new Intent(RegistrationActivity.this,EmailActivity.class);
                                    intent.putExtra("Fid",id);
                                    intent.putExtra("authType",Constants.AuthType.FACEBOOK);
                                    intent.putExtra("auth",Constants.Auth.REGISTRATION);
                                    startActivity(intent);
                                    finish();

                                }


                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();


                Toast.makeText(RegistrationActivity.this, "successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(RegistrationActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void googleopt() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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

            final String email =  account.getEmail();
            loader.show();

            Common.checkEmailRegistration(email, new BooleanCallback() {
                @Override
                public void callback(boolean isValid) {
                    if(isValid){
                        loader.dismiss();
                        Intent intent = new Intent(RegistrationActivity.this,PasswordActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("authType",Constants.AuthType.GOOGLE);
                        intent.putExtra("auth",Constants.Auth.REGISTRATION);
                        startActivity(intent);
                        finish();

                    }
                    else {
                        loader.dismiss();
                        Log.i("dkvcdfklm", "not ok.. : ");
                        Toast.makeText(RegistrationActivity.this, "User already exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            Toast.makeText(this, "Succesfull", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {


            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            Log.i("adasfsd", "handleSignInResult: " + e.toString());
        }
    }


}
