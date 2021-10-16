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
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class RegistrationActivity extends AppCompatActivity {
    DatabaseReference db;
    FirebaseAuth Auth;
    Toolbar tool;

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
        setContentView(R.layout.activity_main3);

        Auth= FirebaseAuth.getInstance();
        EditText E1,E2,E3,E4;
        Button btn1,btn2;
        //tool=findViewById(R.id.toolbar2);
        //setSupportActionBar(tool);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db= FirebaseDatabase.getInstance().getReference();
        E1=findViewById(R.id.editTextTextPersonName2);
        E2=findViewById(R.id.editTextTextPersonName4);
        E3=findViewById(R.id.editTextTextPersonName5);
        E4=findViewById(R.id.editTextTextPersonName8);
        btn1=findViewById(R.id.button2);
        btn2=findViewById(R.id.button3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = E2.getText().toString();
                String pass = E3.getText().toString();
                Log.e("mail:", mail);
                Log.e("pas:", pass);
                Auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            abc abc1 = new abc();
                            abc1.setFirstName(E1.getText().toString());
                            abc1.setEmail(E2.getText().toString());
                            abc1.setPassword(E3.getText().toString());
                            abc1.setPhoneNumber(E4.getText().toString());
                            db.child("user").setValue(abc1).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "working", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("error", "" + task.getException().getMessage());
                                        Toast.makeText(RegistrationActivity.this, "not working", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            Toast.makeText(RegistrationActivity.this, "working", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("error:", "" + task.getException().getMessage());
                            Toast.makeText(RegistrationActivity.this, "not working", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(in);
                //mobile connect krna apna
            }
        });
    }
}








