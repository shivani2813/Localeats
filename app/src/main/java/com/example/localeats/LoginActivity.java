package com.example.localeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText E1, E2;
        Button b1;
        TextView t1, t2;
        Toolbar toolbar;
        FirebaseAuth MAuth;
        MAuth = FirebaseAuth.getInstance();
        E1 = findViewById(R.id.editTextTextPersonName);
        E2 = findViewById(R.id.editTextTextPersonName3);
        b1 = findViewById(R.id.button);
        t1 = findViewById(R.id.textView2);
        t2 = findViewById(R.id.textView3);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = E1.getText().toString();
                String pass1 = E2.getText().toString();
                MAuth.signInWithEmailAndPassword(email, pass1).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "pass is correct", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("error",task.getException().getMessage());
                            Toast.makeText(LoginActivity.this, "pass is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
       t2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent in = new Intent(LoginActivity.this,RegistrationActivity.class);
               startActivity(in);
           }
       });
    }
}