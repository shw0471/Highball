package com.semicolon.highball.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteWhiskyRoomData(

    @PrimaryKey
    var id: Int,

    var title: String,

    var price: Int,

    var imageUrl: String

)