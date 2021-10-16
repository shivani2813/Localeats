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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.canhub.cropper.CropImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Random;
import java.util.UUID;

public class AddResturantActivity extends AppCompatActivity {
    EditText E1, E2, E3;
    Button Btn;
    TextView t1, t2;
    ImageView I1;
    RatingBar R1;
    DatabaseReference db;
    String random;
    EditText address;
    StorageReference storageReference;
    String imagePath;
    Toolbar toolbar;
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
        setContentView(R.layout.activity_main9);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        E1 = findViewById(R.id.textview);
        E2 = findViewById(R.id.textView2);
        E3 = findViewById(R.id.textview3);
        t1 = findViewById(R.id.txtrating);
        t2 = findViewById(R.id.txtPriceStart);
        R1 = findViewById(R.id.rating);
        Btn = findViewById(R.id.btnadd);
        address = findViewById(R.id.address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        db.child("test").setValue("hello");
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modal sh = new modal();
                sh.setRestruent(E1.getText().toString());
                sh.setRestruantSlogan(E2.getText().toString());
                sh.setBasicprice(E3.getText().toString());
                sh.setRating(R1.getRating());
                sh.setRstAdrs(address.getText().toString());
                sh.setRstSaftey("Good");
                sh.setImage(imagePath);
                random = String.valueOf(new Random().nextInt(100));
                db.child("Resturant").child("resturant" + random).setValue(sh)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.e("taskk:", "" + task);
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddResturantActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AddResturantActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        I1 = findViewById(R.id.food);
        I1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().start(AddResturantActivity.this);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                String path = activityResult.getUriFilePath(AddResturantActivity.this, true);
                DocumentFile documentFile = DocumentFile.fromFile(new File(path));
                imagePath = documentFile.getUri().toString();
                Bitmap bitmap = Utils.getBitmapFromUri(AddResturantActivity.this, documentFile.getUri(), 300);
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
                                imagePath=uri.toString();
                            }
                        });
                    }
                }
            });
//
//            ref.putFile(Uri.parse(imagePath))
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Log.e("tasksnapshot",taskSnapshot.toString());
//                            Toast.makeText(AddResturantActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(AddResturantActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
//                        }
//                    });
        }
    }
}