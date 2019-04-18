package com.example.pub2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileCreationActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation_page);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                String name = editText.getText().toString();
                EditText editText1 = findViewById(R.id.editTextNumber);
                String phone = editText1.getText().toString();
                String photo_url;

                if (firebaseUser.getPhotoUrl() == null) {
                    photo_url = "default";
                } else {
                    photo_url = firebaseUser.getPhotoUrl().toString();
                }

                Map map = new HashMap();
                map.put("phone", phone);
                map.put("name", name);
                map.put("photo_url", photo_url);
                map.put("balance", "0");
                map.put("notification","Notification Here");

                databaseReference.child("user").child(firebaseUser.getUid()).updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        startActivity(new Intent(ProfileCreationActivity.this, MainActivity.class));
                    }
                });
            }
        });
    }

}
