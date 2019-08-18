package com.example.android.youtubefavorites.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.youtubefavorites.domain.*

@Entity(tableName = "favorite_video_list_table")
data class DatabaseVideo(
    @PrimaryKey(autoGenerate = true)
    var tableId: Long = 0L,
    val videoId: String,
    val title: String,
    val date: String,
    val url: String
)

fun List<DatabaseVideo>.asDomainModel(): List<Video> {
    return map {
        Video(
            tableId = it.tableId,
            id = Id(it.videoId),
            snippet = Snippet("", it.date, Thumbnails(High(it.url)), it.title),
            statistics = Statistics("","","","","")
        )
    }
}