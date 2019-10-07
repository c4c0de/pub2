package com.example.pub2;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class MatchDetailsActivity extends AppCompatActivity {

    private Button registerBtn;
    private TextView slotView, ruleView;

    private FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference =  firebaseDatabase.getReference();

    private String slot, rules, balance, fee , username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        slotView = findViewById(R.id.slotview);
        ruleView = findViewById(R.id.ruleView);
        registerBtn = findViewById(R.id.registerBtn);

        final String match_id = getIntent().getStringExtra("match_id");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.child("user").child(firebaseUser.getUid()).child("matches").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.wtf("myTag", "Match exist: " + dataSnapshot.hasChild(match_id));
                if(dataSnapshot.hasChild(match_id)){
                    registerBtn.setClickable(false);
                    registerBtn.setAlpha(0.3f);
                    registerBtn.setTextColor(Color.WHITE);
                    registerBtn.setText("Registered");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("match").child(match_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rules = dataSnapshot.child("rules").getValue().toString();
                ruleView.setText(rules);
                slot = dataSnapshot.child("slot").getValue().toString();
                slotView.setText("Players: "+ slot + "/100");
                fee = dataSnapshot.child("fee").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        slotView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchDetailsActivity.this , ParticipantsActivity.class);
                intent.putExtra("match_id", match_id);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference.child("user").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        balance = dataSnapshot.child("balance").getValue().toString();
                        username = dataSnapshot.child("name").getValue().toString();


                        if (Integer.parseInt(balance) >= Integer.parseInt(fee)){

                             if (Integer.parseInt(slot) <= 100){

                                 //register
                                 databaseReference.child("user").child(firebaseUser.getUid()).child("matches").child(match_id).setValue(match_id);

                                 databaseReference.child("participants").child(match_id)
                                         .child(firebaseUser.getUid()).child("username").setValue(username);

                                 int new_balance = Integer.parseInt(balance) - Integer.parseInt(fee);
                                 databaseReference.child("user").child(firebaseUser.getUid()).child("balance").setValue(new_balance);


                                 int new_slot = Integer.parseInt(slot) + 1;
                                 databaseReference.child("match").child(match_id).child("slot").setValue(new_slot);

                                 Intent intent = new Intent(MatchDetailsActivity.this, ParticipantsActivity.class);
                                 startActivity(intent);

                             }
                             else{
                                 Toast.makeText(MatchDetailsActivity.this,
                                         "No more slot left", Toast.LENGTH_SHORT).show();
                             }
                        }
                        else{
                            Toast.makeText(MatchDetailsActivity.this,
                                    "Please recharge your wallet", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
