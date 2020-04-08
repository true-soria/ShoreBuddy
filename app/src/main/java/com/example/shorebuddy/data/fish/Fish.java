package com.example.shorebuddy.data.fish;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "fish")
public class Fish {
    @PrimaryKey
    @NonNull
    @ColumnInfo(index = true)
    public String species;

    public Fish(@NotNull String species) {
        this.species = species;
    }
}
