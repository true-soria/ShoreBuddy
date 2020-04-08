package com.example.shorebuddy.data.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.lakes.Lake;

import java.util.List;

public class FishWithLakes {
    @Embedded public Fish fish;

    @Relation(
        parentColumn = "species",
        entityColumn = "name",
        associateBy = @Junction(LakeFishCrossRef.class)
    )
    public List<Lake> lakes;
}
