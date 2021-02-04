package com.kev.styleupphysiospine.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kev.styleupphysiospine.R;

public class ShowUserProfileActivity extends AppCompatActivity {
    TextView name_tv, email_tv, age_tv, phone_tv;
    FirebaseFirestore fStore;
    String userID;
    private FirebaseAuth auth;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_profile);
        name_tv = findViewById(R.id.name_tv_sp);
        email_tv = findViewById(R.id.email_tv_sp);
        age_tv = findViewById(R.id.age_tv_sp);
        phone_tv = findViewById(R.id.phone_tv_sp);
        
        floatingActionButton = findViewById(R.id.floatingbtn_sp);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        FirebaseUser mFirebaseUser = auth.getCurrentUser();
        userID = mFirebaseUser.getUid(); //Do what you need to do with the id

        DocumentReference documentReference = fStore.collection("users").document(userID);

            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                phone_tv.setText(value.getString("phoneNumber"));
                name_tv.setText(value.getString("fName"));
                age_tv.setText(value.getString("age"));
                email_tv.setText(value.getString("email"));

                }
            });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "Thank you for using the app", Toast.LENGTH_LONG).show();

                break;
        }

        return true;
    }



}