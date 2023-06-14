package com.example.assignmentproject.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignmentproject.localdb.entities.DataEntity
import com.example.assignmentproject.localdb.entities.ScoreEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ScoreDao {
    @Query("SELECT * FROM score")
    fun getScore(): Single<ScoreEntity>

    @Query("SELECT * FROM score ORDER BY score DESC LIMIT 1")
    fun getHighestScore(): Single<ScoreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScore(scoreEntity: ScoreEntity): Completable

    @Query("DELETE FROM score")
    fun deleteAll(): Completable
}
