package com.example.localeats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.documentfile.provider.DocumentFile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.canhub.cropper.CropImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class MenuActivity extends AppCompatActivity {
    Spinner sp;
    DatabaseReference dr;
    FirebaseDatabase auth;
    Adapter ad;
    StorageReference storageReference;
    EditText E1, E2, E3,E4;
    ImageView I1;
    RatingBar R1;
    Button b1;
    Toolbar toolbar;
    String imagePath;
    RadioGroup radioGroup;
    RadioButton r1, r2;
    DatabaseReference db;
    String Vegtype, random;
    ArrayList<String> ad1 = new ArrayList<String>();
    String selectedResturantKey;

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
        setContentView(R.layout.addmenu);
        toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sp = findViewById(R.id.spinner);
        E1 = findViewById(R.id.editTextTextPersonName9);
        E2 = findViewById(R.id.editTextTextPersonName10);
        E3 = findViewById(R.id.editTextTextPersonName11);
        E4=findViewById(R.id.editTextTextPersonName12);
        radioGroup = findViewById(R.id.radio);
        b1 = findViewById(R.id.button5);
        R1 = findViewById(R.id.rating);
        r1 = findViewById(R.id.radio1);
        r2 = findViewById(R.id.radio2);
        storageReference = FirebaseStorage.getInstance().getReference();

        I1 = findViewById(R.id.food);
        I1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().start(MenuActivity.this);

            }
        });
        dr = FirebaseDatabase.getInstance().getReference();
        db = FirebaseDatabase.getInstance().getReference();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedResturantKey = sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xyz abc = new xyz();
                abc.setTitle(E1.getText().toString());
                abc.setPrize(E2.getText().toString());
                abc.setTag(Vegtype);
                abc.setRating(R1.getNumStars());
                abc.setDiscription(E3.getText().toString());
                abc.setImage(imagePath );
                abc.setQuantity(E4.getText().toString());
                random = String.valueOf(new Random().nextInt(100));
                db.child("Resturant").child(selectedResturantKey).child("Menu").child("Menu" + random).setValue(abc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MenuActivity.this, "working", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(MenuActivity.this, "not working", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton btn = radioGroup.findViewById(R.id.radio1);
                RadioButton btn1 = radioGroup.findViewById(R.id.radio2);
                if (btn.isChecked()) {
                    Vegtype = btn.getText().toString();
                }
                if (btn1.isChecked()) {
                    Vegtype = btn1.getText().toString();
                }
            }
        });


        dr.child("Resturant").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ad1.clear();
                    for (DataSnapshot obj : task.getResult().getChildren()) {
                        Log.e("ffjf", "" + obj.getValue().toString());
                        Log.e("rest key", "" + obj.getKey());
                        //String Restruent = obj.child("restruent").getValue(String.class);
                        String Restruent = obj.getKey();

                        ad1.add(Restruent);
                    }
                    Log.e("ggg", "" + ad1.size());
                    if (ad1 != null) {
                        ArrayAdapter ad = new ArrayAdapter(MenuActivity.this, android.R.layout.simple_dropdown_item_1line, ad1);
                        ad.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        sp.setAdapter(ad);
                    }

                    Toast.makeText(MenuActivity.this, "working", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "not working", Toast.LENGTH_SHORT).show();
                    Log.e("fffh", task.getException().getMessage());
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                String path = activityResult.getUriFilePath(MenuActivity.this, true);
                DocumentFile documentFile = DocumentFile.fromFile(new File(path));
                imagePath = documentFile.getUri().toString();
                Bitmap bitmap = Utils.getBitmapFromUri(MenuActivity.this, documentFile.getUri(), 300);
                I1.setImageBitmap(bitmap);
                uploadImage(documentFile.getUri());


            }
        }

    }

    private void uploadImage(Uri img) {

        if (img != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(img).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                progressDialog.dismiss();
                                Log.e("uri:", uri.toString());
                                imagePath = uri.toString();
                            }
                        });
                    }
                }
            });
        }


    }
}