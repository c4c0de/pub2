package com.example.pub2;

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

import com.example.pub2.Adapter.UsersListAdapter;
import com.example.pub2.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    private RecyclerView userListRecyclerView;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference =  firebaseDatabase.getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        userListRecyclerView = view.findViewById(R.id.userListRecyclerView);
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userListRecyclerView.setHasFixedSize(true);

        final Query query = databaseReference.child("user");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("mylog", ds.child("name").getValue().toString());

                    User user = new User();

                    user.setUserName(ds.child("name").getValue().toString());
                    user.setPhoto(ds.child("photo_url").getValue().toString());
                    user.setLastSeen(ds.child("last_seen").getValue().toString());

                    userList.add(user);
                }

                UsersListAdapter usersListAdapter = new UsersListAdapter(userList, getContext());
                userListRecyclerView.setAdapter(usersListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

//    private String getLastSeenStatus(String data) {
//
//
//
//    }

}
