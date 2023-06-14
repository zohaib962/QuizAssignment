package com.example.assignmentproject.network

import com.example.assignmentproject.models.MockResponse
import io.reactivex.Single
import retrofit2.http.GET

/**
 * @Author: Muhammad Zohaib
 * @Designation: Sr.SoftwareEngineer(Android)
 * @Gmail: muhammad.zohaib@axabiztech.com
 * @Company: Aksa SDS
 * @Created 6/13/2023 at 2:46 PM
 */
interface ApiService {

    @GET("/v1/bbbb1046-3e8d-437f-aa81-8171085cfc82")
    fun getMockQuestionsResponse(): Single<MockResponse>
}
