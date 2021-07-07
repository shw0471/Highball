package com.semicolon.highball.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteWhiskyRoomData::class],
    version = 1
)
abstract class FavoriteWhiskyDatabase : RoomDatabase() {

    abstract fun favoriteWhiskyDao(): FavoriteWhiskyDao
}