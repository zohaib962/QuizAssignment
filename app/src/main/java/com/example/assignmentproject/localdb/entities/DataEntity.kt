package com.example.assignmentproject.localdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var response: String = "",
)
