package com.example.taskblackcoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView google,fb;
    EditText email,password;
    Button   login;
    TextView signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        google=findViewById(R.id.imageView);
        fb=findViewById(R.id.imageView2);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup     );
    }
}
