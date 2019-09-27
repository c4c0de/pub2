package com.example.pub2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.pub2.Adapter.MatchAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference =  firebaseDatabase.getReference();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            BottomNavigationView navigation =  findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(this);

            loadFragment(new HomeFragment());
        } else {
            Log.d("mylog", "Starting splash");
            startActivity(new Intent(this, SplashScreenActivity.class));
        }

    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment=null;

        switch(menuItem.getItemId()){

            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_notifications:
                fragment = new NotifiacationFragment();
                break;

            case R.id.navigation_wallet:
                fragment = new WalletFragment();
                break;

            case R.id.navigation_users:
                fragment = new UserListFragment();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseReference.child("user").child(firebaseUser.getUid()).child("last_seen").setValue("online");
    }

    @Override
    protected void onPause() {
        super.onPause();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        databaseReference.child("user").child(firebaseUser.getUid()).child("last_seen").setValue(currentDateandTime);
    }
}
