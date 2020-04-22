package com.example.taskblackcoffer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskblackcoffer.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {
    private EditText otp;
    private Button verifycode;
    private TextView resend;
    private String number,id;
    private FirebaseAuth mAuth;
    private Constants.AuthType authType;
    private Constants.Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        Log.i("sjchd", "registration: "+getIntent().getStringExtra("password"));

        otp = findViewById(R.id.editText);
        verifycode = findViewById(R.id.verifycode);
        resend = findViewById(R.id.resend);
        authType = (Constants.AuthType)getIntent().getSerializableExtra("authType");
        auth = (Constants.Auth)getIntent().getSerializableExtra("auth");


        mAuth = FirebaseAuth.getInstance();
        number = getIntent().getStringExtra("number");

        sendVerificationCode();

        verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(VerificationActivity.this, "Enter Otp", Toast.LENGTH_SHORT).show();
                }
                else if(otp.getText().toString().replace(" ","").length()!=6){
                    Toast.makeText(VerificationActivity.this, "Enter right otp", Toast.LENGTH_SHORT).show();
                }
                else {
                     PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otp.getText().toString().replace(" ",""));
                     signInWithPhoneAuthCredential(credential);
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });

    }

    private void sendVerificationCode() {
        new CountDownTimer(60000,1000){
            @Override
            public void onTick(long l) {
                resend.setText(""+l/1000);
                resend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resend.setText(" Resend");
                resend.setEnabled(true);
            }
        }.start();


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        VerificationActivity.this.id = id;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(VerificationActivity.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("sfsfssf", "onVerificationFailed: "+e.getMessage());
                    }
                });        // OnVerificationStateChangedCallbacks

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("djcbjsdb", "onFailure: "+e.toString());
                        Toast.makeText(VerificationActivity.this, "onFailure: "+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             if(auth==Constants.Auth.REGISTRATION){
                                 registration();
                             }
                             else {
                                 Toast.makeText(VerificationActivity.this, "Type Login ", Toast.LENGTH_SHORT).show();
                             }


                        } else {
                            Toast.makeText(VerificationActivity.this, "Verification Filed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registration() {



        HashMap<String,Object> map = new HashMap<>();
        map.put(Constants.User.email,getIntent().getStringExtra("email"));
        map.put(Constants.User.number,number);
        map.put(Constants.User.password,getIntent().getStringExtra("password"));
        map.put(Constants.User.login_with,authType);

        FirebaseDatabase.getInstance().getReference()
                .child(Constants.User.key)
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(VerificationActivity.this, DashBoardActivity.class));
                        finish();
                    }
                });




    }
}
