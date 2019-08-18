package com.example.android.youtubefavorites.network

import com.example.android.youtubefavorites.domain.SearchResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

interface YoutubeApiService {
    @GET("search")
    fun getSearchResults(
        @Query("part") property: String,
        @Query("maxResults") maxResults: Int,
        @Query("q") search: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): Deferred<SearchResponse>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

object YoutubeApi {
    const val API_KEY = "***Place your own YouTube API key***"
    const val PROPERTY = "snippet"
    const val RESULTS_PER_PAGE = 50
    const val RESOURCE_TYPE = "video"

    val retrofitService: YoutubeApiService by lazy {
        retrofit.create(YoutubeApiService::class.java)
    }
}