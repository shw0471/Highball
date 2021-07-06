package com.semicolon.highball.local

import androidx.room.Database

@Database(
    entities = [FavoriteWhiskyRoomData::class],
    version = 1
)
abstract class FavoriteWhiskyDatabase {

    abstract fun favoriteWhiskyDao(): FavoriteWhiskyDao
}