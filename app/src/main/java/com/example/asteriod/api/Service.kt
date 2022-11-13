package com.example.asteriod.api

import androidx.lifecycle.LiveData
import com.example.asteriod.PictureOfDay
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


interface ImageOfThedayService {
    @GET("planetary/apod?api_key=enter_your_key")
    fun getPhoto(): Deferred<PictureOfDay>
//    @GET("devbytes.json")
//    fun getPlaylist(): Deferred<NetworkVideoContainer>
}
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object NetworkImageOfTheDay {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val imageApi = retrofit.create(ImageOfThedayService::class.java)
}
//
//////////////////////


interface AsteriodService {
    @GET("neo/rest/v1/feed?api_key=enter_your_key")
    fun getAsteriods(): Deferred<String>


}

object NetworkAsteriod {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
    val asteriod = retrofit.create(AsteriodService::class.java)
}
