package com.example.android.youtubefavorites.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.youtubefavorites.domain.Video

@Dao
interface VideoDatabaseDao {

    @Insert
    fun insert(databaseVideo: DatabaseVideo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg databaseVideos: DatabaseVideo)

    @Update
    fun update(databaseVideo: DatabaseVideo)

    @Delete
    fun delete(databaseVideo: DatabaseVideo)

    @Query("SELECT * from favorite_video_list_table WHERE videoId = :key")
    fun get(key: String): DatabaseVideo?

    @Query("DELETE FROM favorite_video_list_table")
    fun clear()

    @Query("SELECT * FROM favorite_video_list_table ORDER BY tableId DESC")
    fun getAllVideos(): LiveData<List<DatabaseVideo>>

    @Query("SELECT * FROM favorite_video_list_table ORDER BY videoId DESC LIMIT 1")
    fun getVideo(): DatabaseVideo?

    @Query("SELECT * from favorite_video_list_table WHERE tableId = :key")
    fun getVideoWithId(key: Long): LiveData<DatabaseVideo>
}