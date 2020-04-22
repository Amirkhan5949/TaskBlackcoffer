package com.example.taskblackcoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskblackcoffer.authUtils.Common;
import com.example.taskblackcoffer.authUtils.callback.BooleanCallback;
import com.example.taskblackcoffer.utils.Constants;
import com.example.taskblackcoffer.utils.Loader;

import java.util.regex.Pattern;

public class EmailActivity extends AppCompatActivity {
    private EditText email;
    private Button submit;
    private Loader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        email=findViewById(R.id.email);
         submit=findViewById(R.id.submit);

        loader = new Loader(this);

        final String Fid=getIntent().getStringExtra("Fid");

     submit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             final String email = EmailActivity.this.email.getText().toString().replace(" ","");

             if(email.length()==0){
                 Toast.makeText(EmailActivity.this, "Enter email address.", Toast.LENGTH_SHORT).show();
             }
             else if(!Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
                     "[a-zA-Z0-9_+&*-]+)*@" +
                     "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                     "A-Z]{2,7}$").matcher(email).matches()){
                 Toast.makeText(EmailActivity.this, "Enter valid email address.", Toast.LENGTH_SHORT).show();
             }


             else {
                Common.checkEmailRegistration(email, new BooleanCallback() {
                    @Override
                    public void callback(boolean isValid) {
                        if(isValid){
                            Intent intent=new Intent(EmailActivity.this,PasswordActivity.class);
                            intent.putExtra("email",email);
                            intent.putExtra("Fid",Fid);
                            intent.putExtra("authType",Constants.AuthType.FACEBOOK);
                            intent.putExtra("auth",Constants.Auth.REGISTRATION);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(EmailActivity.this, "You already registeered.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
             }
         }
     });


    }
}
