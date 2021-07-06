package com.semicolon.highball.remote

import com.google.gson.annotations.SerializedName

data class CompWhiskyData(

    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("price")
    var price: Int,

    @SerializedName("detail_img_url")
    var imageUrl: String,

)
