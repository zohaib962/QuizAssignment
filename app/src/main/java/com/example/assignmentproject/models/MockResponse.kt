package com.example.assignmentproject.models

import com.google.gson.annotations.SerializedName

data class MockResponse(
    @SerializedName("questions")
    val questions: List<Question>,
)
