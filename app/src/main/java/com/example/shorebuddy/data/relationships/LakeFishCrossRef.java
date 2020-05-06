package com.example.shorebuddy.data.relationships;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.lakes.Lake;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"name", "species"},
        foreignKeys = {@ForeignKey(entity = Lake.class,
                                   parentColumns = "name",
                                   childColumns = "name",
                                   onDelete = ForeignKey.CASCADE),
                       @ForeignKey(entity = Fish.class,
                                   parentColumns = "species",
                                   childColumns = "species",
                                   onDelete = ForeignKey.CASCADE)})
public class LakeFishCrossRef {
    @NotNull
    @ColumnInfo(name = "name")
    public String lakeName;

    @NotNull
    @ColumnInfo(index = true)
    public String species;
}