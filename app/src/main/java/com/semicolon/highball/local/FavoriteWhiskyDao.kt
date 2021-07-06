package com.semicolon.highball.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteWhiskyDao {

    @Query("SELECT * FROM FavoriteWhiskyRoomData")
    fun getFavoriteWhiskyList(): Single<List<FavoriteWhiskyRoomData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteWhisky(favoriteWhisky: FavoriteWhiskyRoomData): Completable

    @Delete()
    fun deleteFavoriteWhisky(favoriteWhisky: FavoriteWhiskyRoomData): Completable

}