package com.example.assignmentproject.viewModels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignmentproject.localdb.entities.DataEntity
import com.example.assignmentproject.localdb.entities.ScoreEntity
import com.example.assignmentproject.models.MockResponse
import com.example.assignmentproject.models.Question
import com.example.assignmentproject.repositories.DataRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author: Muhammad Zohaib
 * @Designation: Sr.SoftwareEngineer(Android)
 * @Gmail: muhammad.zohaib@axabiztech.com
 * @Company: Aksa SDS
 * @Created 6/14/2023 at 4:02 PM
 */

@HiltViewModel
class QuestionAnswerViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    private val _data = MutableLiveData<List<Question>>()
    val data: LiveData<List<Question>> get() = _data

    private val _dataInserted = MutableLiveData<Boolean>()
    val dataInserted: LiveData<Boolean> get() = _dataInserted

    @SuppressLint("CheckResult")
    fun fetchDataFromDb() {
        dataRepository.fetchDataFromDb()
            .subscribe(
                { result ->
                    _data.postValue(Gson().fromJson(result.response, MockResponse::class.java).questions)
                },
                { error -> },
            )
    }

    @SuppressLint("CheckResult")
    fun insertScoreInDb(score: Int) {
        val scoreEntity = ScoreEntity()
        scoreEntity.score=score

        dataRepository.insertScore(scoreEntity)
            .subscribe(
                {
                    _dataInserted.postValue(true)
                },
                { error ->
                    _dataInserted.postValue(false)
                },
            )
    }
}
