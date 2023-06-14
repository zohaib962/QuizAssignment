package com.example.assignmentproject.localdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var score: Int = 0,
)
