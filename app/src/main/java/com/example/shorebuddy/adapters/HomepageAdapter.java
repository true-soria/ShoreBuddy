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
import com.example.shorebuddy.data.solunar.Solunar;
import com.example.shorebuddy.views.homepage.ModuleWidget;

import java.util.List;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.HomeViewHolder> {
    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageButton icon;
        TextView title;
        ConstraintLayout widget;

        public HomeViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.list_icon);
            title = itemView.findViewById(R.id.list_title);
            widget = itemView.findViewById(R.id.generic_widget);
        }
    }

    private List<ModuleWidget> widgets;

    public HomepageAdapter(List<ModuleWidget> widgets) {
        this.widgets = widgets;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_module, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        ModuleWidget widget = widgets.get(position);
        Drawable icon = widget.getIcon();
        String title = widget.getTitle();
        holder.icon.setImageDrawable(icon);
        holder.title.setText(title);
        holder.widget.addView(widget, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getItemCount() {
        return widgets.size();
    }

}
