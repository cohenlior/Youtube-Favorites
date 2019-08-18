package com.example.android.youtubefavorites.favorites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.youtubefavorites.database.VideoDatabaseDao
import com.example.android.youtubefavorites.search.SearchViewModel

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the VideoDatabaseDao and context to the ViewModel.
 */
class FavoritesViewModelFactory(
    private val dataSource: VideoDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}