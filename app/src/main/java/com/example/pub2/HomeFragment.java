package com.example.pub2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pub2.model.Match;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment{

    private ImageView imageView;
    private TextView textView;
    private RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference =  firebaseDatabase.getReference();

    private String userName;
    private String photo_url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.child("user").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("name").getValue().toString();
                if (dataSnapshot.child("photo_url").getValue().toString() != null) {
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

        Query query = databaseReference.child("match");

        FirebaseRecyclerOptions<Match> options =
                new FirebaseRecyclerOptions.Builder<Match>()
                        .setQuery(query, Match.class)
                        .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Match, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Match model) {
                Picasso.get().load(model.getPhoto()).into(holder.imageView);
                holder.hostTextView.setText(model.getHost());
                holder.titleTextView.setText(model.getTitle());
                holder.descriptionTextView.setText(model.getDescription());
                holder.registeredTextView.setText(model.getRegistered());
                holder.dateTimeTextView.setText(model.getDate() + " | " + model.getTime());
            }

            @NonNull
            @Override
            public HomeFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_maatch, viewGroup, false);

                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

        return view;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView hostTextView;
        TextView titleTextView;
        TextView descriptionTextView;
        TextView registeredTextView;
        TextView dateTimeTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.bgImageView);
            hostTextView = itemView.findViewById(R.id.hostTextview);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextview);
            registeredTextView = itemView.findViewById(R.id.registeredTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
        }
    }

}
