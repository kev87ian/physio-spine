package com.kev.styleupphysiospine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.kev.styleupphysiospine.ui.Fragment_Equipment;
import com.kev.styleupphysiospine.ui.LoginActivity;
import com.kev.styleupphysiospine.ui.ShowUserProfileActivity;

public class MainActivity extends AppCompatActivity {

private Fragment fragmentEquipment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentEquipment = new Fragment_Equipment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame, fragmentEquipment, "goalgoalFragment").commit();
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

            case R.id.showProfile:
                startActivity(new Intent(this, ShowUserProfileActivity.class));



                break;
        }

        return true;
    }

}