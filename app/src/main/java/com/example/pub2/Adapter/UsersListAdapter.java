package com.example.pub2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pub2.R;
import com.example.pub2.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.MyViewHolder> {

    private List<User> userList;
    private Context context;

    public UsersListAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;

        Log.d("mylog", "List size is: " + userList.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_user_list, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.get().load(userList.get(i).getPhoto()).into(myViewHolder.userImageView);
        myViewHolder.userNameTextView.setText(userList.get(i).getUserName());
        myViewHolder.lastSeenTextView.setText(userList.get(i).getLastSeen());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTextView, lastSeenTextView;
        CircleImageView userImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userImageView = itemView.findViewById(R.id.listUserCircularImageVIew);
            userNameTextView = itemView.findViewById(R.id.listUserNameTextview);
            lastSeenTextView = itemView.findViewById(R.id.listUserLastSeenTextiView);
        }
    }
}
