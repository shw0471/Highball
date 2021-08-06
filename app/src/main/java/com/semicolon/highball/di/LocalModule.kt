package com.semicolon.highball.di

import androidx.room.Room
import com.semicolon.highball.local.FavoriteWhiskyDao
import com.semicolon.highball.local.FavoriteWhiskyDatabase
import org.koin.dsl.module

val localModule = module {
    single {
        Room.databaseBuilder(get(), FavoriteWhiskyDatabase::class.java, "dd")
            .allowMainThreadQueries()
            .build()
    }

    single { get<FavoriteWhiskyDatabase>().favoriteWhiskyDao() }
}