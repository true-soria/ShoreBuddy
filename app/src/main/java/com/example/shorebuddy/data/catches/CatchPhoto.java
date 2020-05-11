package com.example.shorebuddy.data.catches;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "CatchPhotos",
        primaryKeys = {"path", "catchRecordUid"},
        indices = {@Index(value="catchRecordUid")},
        foreignKeys = {
            @ForeignKey(entity = CatchRecord.class,
            parentColumns = "uid",
            childColumns = "catchRecordUid",
            onDelete = ForeignKey.CASCADE)
        })
public class CatchPhoto {
    @NonNull public String path;
    public int catchRecordUid;

    public CatchPhoto(@NotNull String path, int catchRecordUid) {
        this.path = path;
        this.catchRecordUid = catchRecordUid;
    }

    @Ignore
    public CatchPhoto(@NotNull String path) {
        this.path = path;
        catchRecordUid = 0;
    }
}

