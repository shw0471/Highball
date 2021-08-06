package com.semicolon.highball.remote

import com.google.gson.annotations.SerializedName

data class WhiskyResponse(

    @SerializedName("count")
    var count: Int,

    @SerializedName("next")
    var next: String?,

    @SerializedName("previous")
    var previous: String?,

    @SerializedName("results")
    var results: MutableList<WhiskyData>
)