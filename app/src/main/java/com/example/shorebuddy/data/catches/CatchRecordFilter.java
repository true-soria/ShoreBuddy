package com.example.shorebuddy.data.catches;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.shorebuddy.BR;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CatchRecordFilter extends BaseObservable {
    private String lake;
    private List<String> fish;

    @Bindable
    public String getLake() {
        if (lake != null) {
            return lake;
        }
        return null;
    }

    public void setLake(@NotNull String lake) {
        this.lake = lake;
        notifyPropertyChanged(BR.lake);
    }

    public void clearLake() {
        this.lake = null;
        notifyPropertyChanged(BR.fish);
    }

    @Bindable
    public List<String> getFish() {
            return fish;
    }

    public void setFish(@NotNull List<String> fish) {
        this.fish = fish;
        notifyPropertyChanged(BR.fish);
    }

    public void clearFish() {
        this.fish = null;
        notifyPropertyChanged(BR.fish);
    }
}
