package com.example.assignmentproject.viewModels

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignmentproject.localdb.entities.DataEntity
import com.example.assignmentproject.models.MockResponse
import com.example.assignmentproject.repositories.DataRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author: Muhammad Zohaib
 * @Designation: Sr.SoftwareEngineer(Android)
 * @Gmail: muhammad.zohaib@axabiztech.com
 * @Company: Aksa SDS
 * @Created 6/13/2023 at 3:51 PM
 */

@HiltViewModel
class MainViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    private val _data = MutableLiveData<Boolean>()
    val data: LiveData<Boolean> get() = _data

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    @SuppressLint("CheckResult")
    fun fetchData(view: View) {
        dataRepository.fetchData()
            .subscribe(
                { result ->
                    dataRepository.deleteAllMockResponse()
                        .subscribe {
                            insertMockResponseInDb(result)
                        }
                },
                { error -> _data.postValue(false) },
            )
    }

    @SuppressLint("CheckResult")
    fun fetchScore() {
        dataRepository.fetchScoreFromDb()
            .subscribe(
                { result ->
                    _score.postValue(result.score)
                },
                { error -> _data.postValue(false) },
            )
    }

    @SuppressLint("CheckResult")
    private fun insertMockResponseInDb(mockResponse: MockResponse) {
        val dataEntity = DataEntity()
        dataEntity.response = Gson().toJson(mockResponse)

        dataRepository.insertMockResponse(dataEntity)
            .subscribe(
                {
                    _data.postValue(true)
                },
                { error ->
                    _data.postValue(false)
                },
            )
    }
}
