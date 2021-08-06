package com.semicolon.highball.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteWhiskyDao {

    @Query("SELECT * FROM FavoriteWhiskyRoomData")
    fun getFavoriteWhiskyList(): Single<List<FavoriteWhiskyRoomData>>

    @Query("SELECT * FROM FavoriteWhiskyRoomData WHERE id IS :id")
    fun getFavoriteWhiskyListById(id: Int): Single<List<FavoriteWhiskyRoomData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteWhisky(favoriteWhisky: FavoriteWhiskyRoomData): Completable

    @Delete()
    fun deleteFavoriteWhisky(favoriteWhisky: FavoriteWhiskyRoomData): Completable

}