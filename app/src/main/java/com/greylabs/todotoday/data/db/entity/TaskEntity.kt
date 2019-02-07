package com.greylabs.todotoday.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
        @PrimaryKey
        @ColumnInfo
        var id: String,
        @ColumnInfo
        var name: String,
        @ColumnInfo
        var description: String,
        @ColumnInfo
        var addDate: String
)