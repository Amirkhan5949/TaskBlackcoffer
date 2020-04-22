package com.example.taskblackcoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskblackcoffer.utils.Constants;

public class PasswordActivity extends AppCompatActivity {

    private EditText password;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        submit=findViewById(R.id.submit);
        password=findViewById(R.id.password);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(PasswordActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().replace(" ","").length()<6){
                    Toast.makeText(PasswordActivity.this, "Enter right password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(PasswordActivity.this, PhoneAuthentication.class);
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    intent.putExtra("authType", (Constants.AuthType)getIntent().getSerializableExtra("authType"));
                    intent.putExtra("auth", (Constants.Auth)getIntent().getSerializableExtra("auth"));
                    intent.putExtra("password",password.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });
    }
}
