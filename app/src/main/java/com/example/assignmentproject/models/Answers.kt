package com.example.assignmentproject.models

import com.google.gson.annotations.SerializedName

data class Answers(
    @SerializedName("A")
    var a: String="",
    @SerializedName("B")
    var b: String="",
    @SerializedName("C")
    var c: String="",
    @SerializedName("D")
    var d: String="",
    @SerializedName("E")
    var e: String="",
)
