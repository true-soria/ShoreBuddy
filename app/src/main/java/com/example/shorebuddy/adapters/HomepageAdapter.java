package com.example.shorebuddy.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.R;

import java.util.List;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.HomeViewHolder> {
    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageButton icon;
        TextView title;
        ConstraintLayout widget;
        ViewGroup parent;

        public HomeViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.list_icon);
            title = itemView.findViewById(R.id.list_title);
            widget = itemView.findViewById(R.id.generic_widget);
            parent = itemView.findViewById(R.id.module_layout);
        }
    }

    private List<Drawable> icons;
    private List<String> titles;
    private List<ConstraintLayout> widgets;
    private Context context;

    public HomepageAdapter( Context context, List<Drawable> icons, List<String> titles, List<ConstraintLayout> widgets) {
        this.icons = icons;
        this.titles = titles;
        this.widgets = widgets;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_module, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.icon.setImageDrawable(icons.get(position));
        holder.title.setText(titles.get(position));
        holder.widget.addView(widgets.get(position), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

}
