package com.example.shorebuddy.data.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.shorebuddy.data.catches.CatchPhoto;
import com.example.shorebuddy.data.catches.CatchRecord;

import java.util.ArrayList;
import java.util.List;

public class CatchRecordWithPhotos {
    @Embedded public CatchRecord record;
    @Relation(
            parentColumn = "uid",
            entityColumn = "catchRecordUid"
    )
    public List<CatchPhoto> photos;

    public CatchRecordWithPhotos() {
        record = new CatchRecord();
        photos = new ArrayList<>();
    }
}
