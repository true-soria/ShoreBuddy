package com.example.shorebuddy.data.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.lakes.Lake;

import java.util.List;

public class LakeWithFish {
    @Embedded public Lake lake;

    @Relation(
            parentColumn = "name",
            entityColumn = "species",
            associateBy = @Junction(LakeFishCrossRef.class)
    )
    public List<Fish> fish;
}
