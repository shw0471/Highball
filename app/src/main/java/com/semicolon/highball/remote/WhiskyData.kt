package com.semicolon.highball.remote

import com.google.gson.annotations.SerializedName

data class WhiskyData(

    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("list_img_url")
    var imageUrl: String,

    @SerializedName("region")
    var region: String,

    @SerializedName("price")
    var price: Int,

    @SerializedName("rating")
    var rating: Int,

    @SerializedName("description")
    var description: String,

    @SerializedName("tags")
    var tags: List<TagData>,

    @SerializedName("comparables")
    var comparable: List<CompWhiskyData>

)