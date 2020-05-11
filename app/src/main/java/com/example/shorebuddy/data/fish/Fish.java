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
    public final String species;

    public Fish(@NotNull String species) {
        this.species = species;
    }

    @NonNull
    @Override
    public String toString() {
        return species;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof Fish) {
            Fish other = (Fish) obj;
            return this.species.equals(other.species);
        } else {
            return false;
        }
    }
}
