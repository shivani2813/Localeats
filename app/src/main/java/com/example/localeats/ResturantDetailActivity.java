package com.example.localeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ResturantDetailActivity extends AppCompatActivity implements MenuAdapter.callBack, Adapter.callBack {

    Bundle extra;
    RecyclerView recyclerView;
    TextView name;
    TextView T1, T2;
    EditText E1;
    Button btn, btn2, btn3,btn4,btn5;
    RatingBar R1;
    Toolbar toolbar;
    ArrayList<additem> ad = new ArrayList<>();
    DatabaseReference db;
    MenuAdapter adapter;
    String number="+91-8437839297";

    RelativeLayout layoutCall;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_detail);
        db = FirebaseDatabase.getInstance().getReference();
        name = findViewById(R.id.txtLblname);
        layoutCall=findViewById(R.id.layoutCall);
        T1 = findViewById(R.id.textvi);
        T2 = findViewById(R.id.textvie);
        btn = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn4 =findViewById(R.id.button);
        btn5=findViewById(R.id.butt);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone="+number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:123456789"));
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResturantDetailActivity.this, activity_gallery.class);
                startActivity(intent);
            }
        });
        toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn3 = findViewById(R.id.add);

        R1 = findViewById(R.id.rating);
        recyclerView = findViewById(R.id.recyclerview);
        extra = getIntent().getExtras();
        modal modalObj = new Gson().fromJson(extra.getString("data"), modal.class);

        Log.e("menu get", modalObj.getRestruent());
        modalObj.getMenu();
        Log.e("menu---", "" + modalObj.getMenu());


        adapter = new MenuAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.child("Resturant").child("resturant16").child("Menu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.e("inside", "success" + task.getResult());
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        String discription = dataSnapshot.child("discription").getValue(String.class);
                        String tag = dataSnapshot.child("tag").getValue(String.class);
                        String title = dataSnapshot.child("title").getValue(String.class);
                        String prize = dataSnapshot.child("prize").getValue(String.class);
                        Integer rating = dataSnapshot.child("rating").getValue(Integer.class);
                        String image= dataSnapshot.child("image").getValue(String.class);

                        Log.e("tt", discription);
                        additem abc = new additem();
                        abc.setVegType("tag");
                        abc.setDescription(discription);
                        abc.setName(title);
                        abc.setRating((float) 4.87);
                        abc.setImageURL(image);
                        abc.setPrice(prize);
                        ad.add(abc);
                    }
                    adapter.setArrayList(ad);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("db exectiption:", task.getException().getMessage());
                }
            }
        });


        name.setText(modalObj.getRestruent());
        T1.setText(modalObj.getRestruantSlogan());
        R1.setRating(modalObj.getRating());


    }

    @Override
    public void cardItemClick(additem menu) {

    }

    @Override
    public void addBtnClick(String tag) {
        layoutCall.setVisibility(View.VISIBLE);
    }

    @Override
    public void cardItemClick(modal menu) {

    }
}