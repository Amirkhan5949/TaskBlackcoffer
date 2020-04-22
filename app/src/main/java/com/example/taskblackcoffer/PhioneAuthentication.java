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
import com.hbb20.CountryCodePicker;

public class PhioneAuthentication extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText number;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phione_authentication);

        countryCodePicker = findViewById(R.id.ccp);
        number = findViewById(R.id.number);
        next = findViewById(R.id.next);
        countryCodePicker.registerCarrierNumberEditText(number);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(number.getText().toString())){
                    Toast.makeText(PhioneAuthentication.this, "Enter No ....", Toast.LENGTH_SHORT).show();
                }
                else if(number.getText().toString().replace(" ","").length()!=10){
                    Toast.makeText(PhioneAuthentication.this, "Enter Correct No ...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(PhioneAuthentication.this,VerificationActivity.class);
                    intent.putExtra("number",countryCodePicker.getFullNumberWithPlus().replace(" ",""));
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    intent.putExtra("authType", (Constants.AuthType)getIntent().getSerializableExtra("authType"));
                    intent.putExtra("auth", (Constants.Auth)getIntent().getSerializableExtra("auth"));
                    intent.putExtra("password", getIntent().getStringExtra("password"));

                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
