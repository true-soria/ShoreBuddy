package com.example.shorebuddy.data.relationships;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"name", "species"})
public class LakeFishCrossRef {
    @NotNull
    @ColumnInfo(name = "name")
    public String lakeName;

    @NotNull
    @ColumnInfo(index = true)
    public String species;
}