package com.example.localeats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements Adapter.callBack {

    EditText E1, E2;
    DatabaseReference db;
    String de1;
    String s1, s2, random;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    DatabaseReference dr;
    Button button;
    Toolbar toolbar;
    FirebaseAuth auth;
    ArrayList<modal> ad1=new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return false;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        toolbar=findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button=findViewById(R.id.button);
        db = FirebaseDatabase.getInstance().getReference();
        db.keepSynced(true);
        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        Adapter adapter = new Adapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
       auth= FirebaseAuth.getInstance();
       if(auth.getCurrentUser().getEmail().equals("tshivani035@gmail.com"))
       {
           fab.setVisibility(View.VISIBLE);
           button.setVisibility(View.VISIBLE);
       }
       else
       {
           fab.setVisibility(View.GONE);
           button.setVisibility(View.GONE);
       }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddResturantActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        dr= FirebaseDatabase.getInstance().getReference();
        Adapter ad =new Adapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

//        progressDialog.show();
        dr.child("Resturant").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    ad1.clear();
                    for(DataSnapshot obj:task.getResult().getChildren()){
                        Log.e("ffjf",""+obj.getValue().toString());
                        String Restruent=obj.child("restruent").getValue(String.class);
                        String Image=obj.child("image").getValue(String.class);
                        String RestruantSlogan=obj.child("restruantSlogan").getValue(String.class);
                        float rating=obj.child("rating").getValue(Float.class);
                        String basicprice=obj.child("basicprice").getValue(String.class);
                        String rstName=obj.child("rstName").getValue(String.class);
                        String rstSaftey=obj.child("rstSaftey").getValue(String.class);
                        String rstAdrs=obj.child("rstAdrs").getValue(String.class);
                        xyz menuObj=obj.child("Menu").getValue(xyz.class);


                        modal sm=new modal();
                        sm.setRestruent(Restruent);
                        sm.setImage(Image);
                        sm.setRestruantSlogan(RestruantSlogan);
                        sm.setRating(rating);
                        sm.setBasicprice(basicprice);
                        sm.setRstName(rstName);
                        sm.setRstSaftey(rstSaftey);
                        sm.setRstAdrs(rstAdrs);
                        sm.setMenu(menuObj);
                        ad1.add(sm);
                    }

                    ad.setArrayList(ad1);
                    recyclerView.setAdapter(ad);
                    Toast.makeText(HomeActivity.this, "working", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "not working", Toast.LENGTH_SHORT).show();
                    Log.e("fffh",task.getException().getMessage());
                }
            }
        });

    }

    @Override
    public void cardItemClick(modal menu) {
        Intent intent = new Intent(HomeActivity.this, ResturantDetailActivity.class);
        intent.putExtra("data",new Gson().toJson(menu,modal.class));
        startActivity(intent);
    }
}