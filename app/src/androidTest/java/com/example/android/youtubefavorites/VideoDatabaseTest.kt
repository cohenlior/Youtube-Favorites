package com.example.android.youtubefavorites

import androidx.room.Room
import androidx.test.runner.AndroidJUnit4
import com.example.android.youtubefavorites.database.DatabaseVideo
import com.example.android.youtubefavorites.database.VideoDatabase
import com.example.android.youtubefavorites.database.VideoDatabaseDao
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 */
@RunWith(AndroidJUnit4::class)
class VideoDatabaseTest {

    private lateinit var videoDao: VideoDatabaseDao
    private lateinit var db: VideoDatabase

    @Before
    fun createDb() {
        val context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, VideoDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        videoDao = db.videoDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertVideoAndGetDetails() {
        val video = DatabaseVideo(
            videoId = "1234",
            title = "test title", date = "2000", url = "www.test.co.il"
        )
        videoDao.insert(video)
        val databaseVideo = videoDao.getVideo()
        assertEquals(databaseVideo?.videoId, "1234")
        assertEquals(databaseVideo?.title, "test title")
        assertEquals(databaseVideo?.date, "2000")
        assertEquals(databaseVideo?.url, "www.test.co.il")
    }
}
