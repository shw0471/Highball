package com.semicolon.highball.di

import com.semicolon.highball.remote.WhiskyService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://evening-citadel-85778.herokuapp.com:443")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single <WhiskyService> { get<Retrofit>().create(WhiskyService::class.java) }
}