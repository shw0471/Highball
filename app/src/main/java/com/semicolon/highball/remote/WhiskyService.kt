package com.semicolon.highball.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WhiskyService {

    @GET("whiskey")
    fun getWhiskyList(): Single<List<WhiskyData>>

    @GET("whiskey/{id}")
    fun getWhiskyById(@Path("id") id: Int): Single<WhiskyData>

}