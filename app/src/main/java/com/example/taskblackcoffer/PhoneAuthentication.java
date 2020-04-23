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
import com.hbb20.CountryCodePicker;

public class PhoneAuthentication extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText number;
    private Button next;
    private Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phione_authentication);

        countryCodePicker = findViewById(R.id.ccp);
        number = findViewById(R.id.number);
        next = findViewById(R.id.next);
        countryCodePicker.registerCarrierNumberEditText(number);
        loader = new Loader(this);
        final String Fid=getIntent().getStringExtra("Fid");


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = countryCodePicker.getFullNumberWithPlus().replace(" ","");

                if(TextUtils.isEmpty(number)){
                    Toast.makeText(PhoneAuthentication.this, "Enter No ....", Toast.LENGTH_SHORT).show();
                }
                else if(PhoneAuthentication.this.number.getText().toString().replace(" ","").length()!=10){
                    Toast.makeText(PhoneAuthentication.this, "Enter Correct No ...", Toast.LENGTH_SHORT).show();
                }
                else {
                    loader.show();
                    Common.checkNumberRegistration(number, new BooleanCallback() {
                        @Override
                        public void callback(boolean isValid) {
                            loader.dismiss();
                            if(isValid){
                                Intent intent = new Intent(PhoneAuthentication.this,VerificationActivity.class);
                                intent.putExtra("number",countryCodePicker.getFullNumberWithPlus().replace(" ",""));
                                intent.putExtra("email",getIntent().getStringExtra("email"));
                                intent.putExtra("authType", (Constants.AuthType)getIntent().getSerializableExtra("authType"));
                                intent.putExtra("auth", (Constants.Auth)getIntent().getSerializableExtra("auth"));
                                intent.putExtra("password", getIntent().getStringExtra("password"));
                                intent.putExtra("Fid",Fid);

                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(PhoneAuthentication.this, "Number exist.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });

    }
}
