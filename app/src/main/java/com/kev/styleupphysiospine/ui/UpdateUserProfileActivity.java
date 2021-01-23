package com.kev.styleupphysiospine.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kev.styleupphysiospine.MainActivity;
import com.kev.styleupphysiospine.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserProfileActivity extends AppCompatActivity {

    private EditText et_name, et_age, et_gender, et_email, et_phone;
    ImageView dp;
    Button button;
    ProgressBar progressBar;
    private Uri imageUri;
    private static final int PICK_IMAGE = 1;
    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        et_name = findViewById(R.id.name_et);
        et_age = findViewById(R.id.age_et);
        et_gender = findViewById(R.id.gender_et);
        et_email = findViewById(R.id.email_et);
        et_phone = findViewById(R.id.phone_et);
        button = findViewById(R.id.save_profile_btn);
        progressBar = findViewById(R.id.progressBar_cp);
        dp = findViewById(R.id.profilePictureCP);

        //referencing the database to the model created on firestore

        documentReference = db.collection("user").document("profile");
        storageReference = firebaseStorage.getInstance().getReference("profile image");

        ProgressDialog progressDialog = new ProgressDialog(UpdateUserProfileActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
                Log.d("V", "working");
            }
        });

    }


    public void chooseImage(View view) {

        //prompts user to select image

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE || resultCode == RESULT_OK ||
        data != null || data.getData() != null){
            imageUri = data.getData();

            Picasso.get().load(imageUri).into(dp);

        }
    }

    //this string detects the extension of the image
    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private void uploadData(){
        String name = et_name.getText().toString().trim();
        String age = et_age.getText().toString().trim();
        String gender = et_gender.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String phoneNumber = et_phone.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age) ||TextUtils.isEmpty(gender) ||
                TextUtils.isEmpty(email) ||TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(name) || imageUri!= null){
                progressBar.setVisibility(View.VISIBLE);
                final StorageReference reference = storageReference.child(System.currentTimeMillis()+ "." + getFileExt(imageUri));

                uploadTask = reference.putFile(imageUri);
            Task<Uri>uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();

                    }
                    return  reference.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                Uri downloadUri = task.getResult();
                                Map<String, String> profile  = new HashMap<>();
                                profile.put("name", name);
                                profile.put("age", age);
                                profile.put("gender", gender);
                                profile.put("email", email);
                                profile.put("phone", phoneNumber);
                                profile.put("url", downloadUri.toString());

                                documentReference.set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(UpdateUserProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), ShowUserProfileActivity.class));

                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(UpdateUserProfileActivity.this, "Profile creation Failed", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }
        else{
            Toast.makeText(UpdateUserProfileActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();


        }
    }
}