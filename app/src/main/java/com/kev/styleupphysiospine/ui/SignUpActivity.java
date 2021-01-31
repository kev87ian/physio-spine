package com.kev.styleupphysiospine.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kev.styleupphysiospine.MainActivity;
import com.kev.styleupphysiospine.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputName, inputAge, inputNumber;
    private Button btnSignIn,  btnResetPassword;
    private TextView clickHere;
  //   private ProgressBar progressBar;
    private FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        inputEmail = findViewById(R.id.et_email);
        inputPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_signup);
        clickHere = (TextView) findViewById(R.id.clickhereTv);
        inputName = findViewById(R.id.et_name);
        inputAge = findViewById(R.id.et_age);
        inputNumber = findViewById(R.id.phoneNumber_et);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //  progressBar = findViewById(R.id.progressbarSignUp);


        clickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String name = inputName.getText().toString().trim();
                String phoneNumber = inputNumber.getText().toString().trim();
                String age = inputAge.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 10 || phoneNumber.length() > 12) {
                    Toast.makeText(getApplicationContext(), "Enter a valid phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(age)) {
                    Toast.makeText(getApplicationContext(), "Enter your age!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter a minimum of 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "You've been Successfully Registered" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                FirebaseUser mFirebaseUser = auth.getCurrentUser();
                                if(mFirebaseUser != null) {
                                    userID = mFirebaseUser.getUid(); //Do what you need to do with the id
                                    DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                                    //storing data into the document
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("fName", name);
                                    user.put("phoneNumber", phoneNumber);
                                    user.put("email", email);
                                    user.put("age", age);

                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG", "onSuccess: user profile is created for "+ userID );

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("TAG", "onFailure "+ e.toString());
                                        }
                                    });
                                }
                                                              //saving user id of logged in user

                                ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                                progressDialog.setTitle("Signing you Up");
                                progressDialog.setMessage("Creating Account");
                                progressDialog.setIndeterminate(true);
                                progressDialog.setCancelable(true);
                                progressDialog.show();

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Registration Failed!" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }
}
