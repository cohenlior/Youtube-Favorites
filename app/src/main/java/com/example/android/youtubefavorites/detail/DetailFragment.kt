package com.example.android.youtubefavorites.detail


import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater

import com.example.android.youtubefavorites.R
import com.example.android.youtubefavorites.database.VideoDatabase
import com.example.android.youtubefavorites.databinding.FragmentDetailBinding
import com.example.android.youtubefavorites.favorites.FavoritesViewModel
import com.example.android.youtubefavorites.favorites.FavoritesViewModelFactory

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailFragment : Fragment() {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        val selectedVideo = DetailFragmentArgs.fromBundle(arguments!!).selectedVideo

        val viewModelFactory = DetailViewModelFactory(selectedVideo, application)
        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(DetailViewModel::class.java)

        return binding.root
    }


}
