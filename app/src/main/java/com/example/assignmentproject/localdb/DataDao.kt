package com.example.assignmentproject.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignmentproject.localdb.entities.DataEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface DataDao {
    @Query("SELECT * FROM data")
    fun getData(): Single<DataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: DataEntity): Completable

    @Query("DELETE FROM data")
    fun deleteAll(): Completable
}
