package com.example.android.youtubefavorites.favorites

import android.app.Application
import androidx.lifecycle.*
import com.example.android.youtubefavorites.database.DatabaseVideo
import com.example.android.youtubefavorites.database.VideoDatabaseDao
import com.example.android.youtubefavorites.database.asDomainModel
import com.example.android.youtubefavorites.domain.Video
import kotlinx.coroutines.*

/**
 * The [ViewModel] that is attached to the [FavoritesViewModel].
 */
class FavoritesViewModel(
    val database: VideoDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _deleteFavorite = MutableLiveData<Boolean>()
    val deleteFavorite: LiveData<Boolean>
        get() = _deleteFavorite

    val favorites: LiveData<List<Video>> = Transformations.map(database.getAllVideos()) {
        it.asDomainModel()
    }

    fun onDeleteClicked(video: Video) {
        uiScope.launch {
            delete(video)
        }
        _deleteFavorite.value = true
    }

    fun onDeleteClickedComplete() {
        _deleteFavorite.value  = null
    }


    private suspend fun delete(video: Video){
        withContext(Dispatchers.IO) {
            video.tableId?.let { DatabaseVideo(tableId = it, videoId = video.id.videoId,
                    title = video.title, date = video.date, url = video.url)
            }?.let { database.delete(it) }
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

        override fun onCleared() {
            super.onCleared()
            viewModelJob.cancel()
        }
    }