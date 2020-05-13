package com.example.shorebuddy.data.catches;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.shorebuddy.BR;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.lakes.Lake;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class CatchRecordFilter extends BaseObservable {
    private Lake lake;
    private List<Fish> fish;

    @Bindable
    public String getLake() {
        if (lake != null) {
            return lake.lakeName;
        }
        return null;
    }

    public void setLake(@NotNull Lake lake) {
        this.lake = lake;
        notifyPropertyChanged(BR.lake);
    }

    public void clearLake() {
        this.lake = null;
        notifyPropertyChanged(BR.fish);
    }

    @Bindable
    public List<String> getFish() {
        if (fish != null) {
            return fish.stream().map(f -> f.species).collect(Collectors.toList());
        }
        return null;
    }

    public void setFish(@NotNull List<Fish> fish) {
        this.fish = fish;
        notifyPropertyChanged(BR.fish);
    }

    public void clearFish() {
        this.fish = null;
        notifyPropertyChanged(BR.fish);
    }
}
