package com.kev.styleupphysiospine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.kev.styleupphysiospine.ui.Fragment_Equipment;
import com.kev.styleupphysiospine.ui.Fragment_Orthotics;
import com.kev.styleupphysiospine.ui.LoginActivity;
import com.kev.styleupphysiospine.ui.ShowUserProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Fragment fragmentEquipment, fragmentOrthotics, fragmentCart;
    private FragmentManager fragmentManager;
    private Context context;
    private Fragment activeFragment;
    Button addToCart;

    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.navView)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addToCart = findViewById(R.id.addToCart);

//        addToCart.

        initFragments();
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);

    }


    private void initFragments() {
        fragmentEquipment = new Fragment_Equipment();
        fragmentOrthotics = new Fragment_Orthotics();
        activeFragment = fragmentEquipment;

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame, fragmentEquipment, "goalgoalFragment").commit();
        fragmentManager.beginTransaction()
                .add(R.id.frame, fragmentOrthotics, "goalgoalFragment").commit();


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


//    @Override
//    public void onNavigationItemReselected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.nav_equipment:
//                //load threeway data
//                loadEquipmentData();
//                return true;
//
//            case R.id.nav_orthotics:
//                //load gg
//                loadOrthoticsData();
//                return true;
//
//    }
//    return false;
//}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //handles bottom nav item clicks
        switch (item.getItemId()) {

            //        switch (item.getItemId()) {
            case R.id.nav_equipment:
                loadEquipmentData();
                return true;

            case R.id.nav_orthotics:
                loadOrthoticsData();
                return true;

        }
        return false;

    }

    private void loadOrthoticsData() {
        fragmentManager.beginTransaction().hide(activeFragment).show(fragmentOrthotics).commit();
        activeFragment = fragmentOrthotics;
        titleTv.setText("Orthotics");
    }

    private void loadEquipmentData() {
        fragmentManager.beginTransaction().hide(activeFragment).show(fragmentEquipment).commit();
        activeFragment = fragmentEquipment;
        titleTv.setText("Equipment");

    }

}