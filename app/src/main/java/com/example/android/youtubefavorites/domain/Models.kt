package com.example.android.youtubefavorites.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */
@Parcelize
data class Video(
    val tableId: Long?,
    val id: Id,
    val snippet: Snippet,
    val statistics: Statistics?
) : Parcelable {
    val videoId: String
        get() = id.videoId

    val date: String
        get() = snippet.publishedAt

    val title: String
        get() = snippet.title

    val url: String
        get() = snippet.thumbnails.high.url

    val description: String?
        get() = snippet.description
}

@Parcelize
data class SearchResponse(
    @Json(name = "items") val videos: List<Video>
) : Parcelable

@Parcelize
data class Id (
    val videoId: String
) : Parcelable

@Parcelize
data class Statistics(
    val commentCount: String,
    val dislikeCount: String,
    val favoriteCount: String,
    val likeCount: String,
    val viewCount: String
) : Parcelable

@Parcelize
data class Snippet(
    val description: String?,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val title: String
) : Parcelable

@Parcelize
data class Thumbnails(
    val high: High
) : Parcelable

@Parcelize
data class High(
    val url: String
) : Parcelable