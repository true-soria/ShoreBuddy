package com.example.shorebuddy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.R;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.HomeViewHolder> {
    public class HomeViewHolder extends RecyclerView.ViewHolder{
        ImageButton icon;
        TextView title;
        public HomeViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.list_icon);
            title = itemView.findViewById(R.id.list_title);
        }
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_module, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
