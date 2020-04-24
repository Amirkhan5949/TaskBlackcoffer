package com.example.taskblackcoffer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.taskblackcoffer.model.User;
import com.example.taskblackcoffer.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.preference.PowerPreference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent=new Intent(SplashActivity.this,DashBoardActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            User user = PowerPreference.getDefaultFile().getObject("user",User.class);
            if(user==null){
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                if(user.getAuth()== Constants.Auth.REGISTRATION){
                     if(user.getAuthType()==Constants.AuthType.GOOGLE){
                        if(user.getPassword()==null){
                            Intent intent=new Intent(SplashActivity.this,PasswordActivity.class);
                            intent.putExtra("email",user.getEmail());
                            intent.putExtra("auth",user.getAuth());
                            intent.putExtra("authType",user.getAuthType());
                            startActivity(intent);
                            finish();
                        }
                        else if(user.getNumber()==null){
                            Intent intent=new Intent(SplashActivity.this,PhoneAuthentication.class);
                            intent.putExtra("email",user.getEmail());
                            intent.putExtra("auth",user.getAuth());
                            intent.putExtra("authType",user.getAuthType());
                            intent.putExtra("password",user.getPassword());
                            startActivity(intent);
                            finish();
                        }
                     }
                     else if(user.getAuthType()==Constants.AuthType.FACEBOOK){
                         if(user.getEmail()==null){
                             Intent intent=new Intent(SplashActivity.this,EmailActivity.class);
                             intent.putExtra("Fid",user.getfId());
                             intent.putExtra("auth",user.getAuth());
                             intent.putExtra("authType",user.getAuthType());
                             startActivity(intent);
                             finish();
                         }
                         if(user.getPassword()==null){
                             Intent intent=new Intent(SplashActivity.this,PasswordActivity.class);
                             intent.putExtra("Fid",user.getfId());
                             intent.putExtra("email",user.getEmail());
                             intent.putExtra("auth",user.getAuth());
                             intent.putExtra("authType",user.getAuthType());
                             startActivity(intent);
                             finish();
                         }
                         else if(user.getNumber()==null){
                             Intent intent=new Intent(SplashActivity.this,PhoneAuthentication.class);
                             intent.putExtra("Fid",user.getfId());
                             intent.putExtra("email",user.getEmail());
                             intent.putExtra("auth",user.getAuth());
                             intent.putExtra("authType",user.getAuthType());
                             intent.putExtra("password",user.getPassword());
                             startActivity(intent);
                             finish();
                         }

                     }
                     else if(user.getAuthType()==Constants.AuthType.EMAIL){
                         if(user.getNumber()==null){
                             Intent intent=new Intent(SplashActivity.this,PhoneAuthentication.class);
                             intent.putExtra("email",user.getEmail());
                             intent.putExtra("auth",user.getAuth());
                             intent.putExtra("authType",user.getAuthType());
                             intent.putExtra("password",user.getPassword());
                             startActivity(intent);
                             finish();
                         }
                     }
                }
                else if(user.getAuth()== Constants.Auth.LOGIN){
                    if(user.getAuthType()==Constants.AuthType.GOOGLE){
                         if(user.getNumber()==null){


                         }
                         else{
                             Intent intent=new Intent(SplashActivity.this,PhoneAuthentication.class);
                             intent.putExtra("email",user.getEmail());
                             intent.putExtra("auth",user.getAuth());
                             intent.putExtra("authType",user.getAuthType());
                             intent.putExtra("password",user.getPassword());
                             startActivity(intent);
                             finish();
                         }
                    }
                    else if(user.getAuthType()==Constants.AuthType.FACEBOOK){
                        if(user.getNumber()==null){
                            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Intent intent=new Intent(SplashActivity.this,PhoneAuthentication.class);
                            intent.putExtra("Fid",user.getfId());
                            intent.putExtra("email",user.getEmail());
                            intent.putExtra("auth",user.getAuth());
                            intent.putExtra("authType",user.getAuthType());
                            intent.putExtra("password",user.getPassword());
                            startActivity(intent);
                            finish();
                        }
                    }
                    else if(user.getAuthType()==Constants.AuthType.EMAIL){
                        if(user.getNumber()==null){
                            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Intent intent=new Intent(SplashActivity.this,PhoneAuthentication.class);
                            intent.putExtra("email",user.getEmail());
                            intent.putExtra("auth",user.getAuth());
                            intent.putExtra("authType",user.getAuthType());
                            intent.putExtra("password",user.getPassword());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        }




    }
}
