package com.example.assignmentproject.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignmentproject.localdb.entities.DataEntity
import com.example.assignmentproject.localdb.entities.ScoreEntity

@Database(entities = [DataEntity::class, ScoreEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
    abstract fun scoreDao(): ScoreDao
}
