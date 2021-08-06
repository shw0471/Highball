package com.semicolon.highball.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WhiskyService {

    @GET("whiskey")
    fun getWhiskyList(@Query("page") page: Int): Single<WhiskyResponse>

    @GET("whiskey/{id}/")
    fun getWhiskyById(@Path("id") id: Int): Single<WhiskyData>

}