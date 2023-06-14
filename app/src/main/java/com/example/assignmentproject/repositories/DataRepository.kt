package com.example.assignmentproject.repositories

import com.example.assignmentproject.localdb.DataDao
import com.example.assignmentproject.localdb.ScoreDao
import com.example.assignmentproject.localdb.entities.DataEntity
import com.example.assignmentproject.localdb.entities.ScoreEntity
import com.example.assignmentproject.models.MockResponse
import com.example.assignmentproject.network.ApiService
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @Author: Muhammad Zohaib
 * @Designation: Sr.SoftwareEngineer(Android)
 * @Gmail: muhammad.zohaib@axabiztech.com
 * @Company: Aksa SDS
 * @Created 6/13/2023 at 3:41 PM
 */
class DataRepository @Inject constructor(
    private val apiService: ApiService,
    private val dataDao: DataDao,
    private val scoreDao: ScoreDao
) {

    fun fetchData(): Single<MockResponse> {
        return apiService.getMockQuestionsResponse()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertMockResponse(data: DataEntity): Completable {
        return dataDao.insertData(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteAllMockResponse(): Completable {
        return dataDao.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchDataFromDb() : Single<DataEntity>{
        return dataDao.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchScoreFromDb() : Single<ScoreEntity>{
        return scoreDao.getHighestScore()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertScore(data: ScoreEntity): Completable {
        return scoreDao.insertScore(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
