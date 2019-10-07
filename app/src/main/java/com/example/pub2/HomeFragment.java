package com.example.pub2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pub2.Adapter.MatchAdapter;
import com.example.pub2.model.Match;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    public interface OnClickHomeFragment {
        void onClick();
    }

    private CircleImageView imageView;
    private TextView textView;
    private RecyclerView recyclerView;

    private MatchAdapter matchAdapter;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference =  firebaseDatabase.getReference();

    private String userName;
    private String photo_url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Log.wtf("myTag", "On Create");

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(getContext(), SplashScreenActivity.class));
        }

        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.child("user").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("name").getValue().toString();
                if (dataSnapshot.child("photo_url").getValue() != null) {
                    photo_url = dataSnapshot.child("photo_url").getValue().toString();
                } else {
                    photo_url = "https://homepages.cae.wisc.edu/~ece533/images/baboon.png";
                }

                textView.setText(userName);
                Picasso.get().load(photo_url).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final List<Match> list = new ArrayList<>();
        Query query = databaseReference.child("match").orderByKey();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Match match = new Match();
                    match.setKillreward(ds.child("killreward").getValue().toString());
                    match.setWinreward(ds.child("winreward").getValue().toString());
                    match.setPhoto(ds.child("photo").getValue().toString());
                    match.setTitle(ds.child("title").getValue().toString());
                    match.setDescription(ds.child("description").getValue().toString());
                    match.setDate(ds.child("date").getValue().toString());
                    match.setTime(ds.child("time").getValue().toString());
                    match.setFee(ds.child("fee").getValue().toString());
                    match.setSlot(ds.child("slot").getValue().toString());
                    match.setMatchid(ds.getKey());

                    list.add(match);
                }

                matchAdapter = new MatchAdapter(getContext(), list);
                recyclerView.setAdapter(matchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }



}
