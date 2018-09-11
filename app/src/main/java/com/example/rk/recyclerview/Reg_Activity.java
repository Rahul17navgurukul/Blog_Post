package com.example.rk.recyclerview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Reg_Activity extends AppCompatActivity {

    private EditText reg_name;
    private EditText reg_username;
    private EditText reg_password;
    private Button reg_btn;

    private FirebaseAuth mAuth;
    private ProgressDialog mRegProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_);

        mAuth = FirebaseAuth.getInstance();

        reg_name = findViewById(R.id.reg_name);
        reg_username = findViewById(R.id.username);
        reg_password = findViewById(R.id.password);
        reg_btn = findViewById(R.id.btn_login);

        mRegProgress = new ProgressDialog(this);


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = reg_name.getText().toString();
                String email = reg_username.getText().toString();
                String password = reg_password.getText().toString();

                if (!TextUtils.isEmpty(name)|| !TextUtils.isEmpty(email)|| !TextUtils.isEmpty(password)) {

                    mRegProgress.setTitle("Registring User");
                    mRegProgress.setMessage("Please Wait");
                    mRegProgress.show();
                    mRegProgress.setCanceledOnTouchOutside(false);

                    register_user(name, email, password);
                }
            }
        });



    }

    private void register_user(String name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    mRegProgress.dismiss();
                    Intent main = new Intent(Reg_Activity.this, MainActivity.class);
                    startActivity(main);
                    finish();

                }

                else {
                    mRegProgress.hide();
                    Toast.makeText(Reg_Activity.this,"Cannot sing in Please check and Try again later  ",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
