package com.example.pub2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pub2.R;
import com.example.pub2.model.Match;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder> {

    private Context context;
    private List<Match> matchList;

    public MatchAdapter(Context context, List<Match> matchList) {
        this.context = context;
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_maatch, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.get().load(matchList.get(i).getPhoto()).into(myViewHolder.imageView);
        myViewHolder.killrewardTextView.setText(matchList.get(i).getKillreward());
        myViewHolder.winrewardTextView.settext(matchList.get(i).getWinreward())
        myViewHolder.titleTextView.setText(matchList.get(i).getTitle());
        myViewHolder.descriptionTextView.setText(matchList.get(i).getDescription());
        myViewHolder.feeTextview.setText("Entry : ₹" + matchList.get(i).getFee());
        myViewHolder.dateTimeTextView.setText(matchList.get(i).getDate());
        myViewHolder.timeTextView.setText(matchList.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView killrewardTextView;
        TextView winreward;
        TextView titleTextView;
        TextView descriptionTextView;
        TextView feeTextview;
        TextView dateTimeTextView;
        TextView timeTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.bgImageView);
            killrewardTextView = itemView.findViewById(R.id.killrewardTextview);
            winrewardTextView = itemView.findViewById(R.id.winrewardTextview)
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextview);
            feeTextview = itemView.findViewById(R.id.registeredTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);

        }
    }
}
