package com.example.android.youtubefavorites.favorites

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.android.youtubefavorites.R
import com.example.android.youtubefavorites.database.VideoDatabase
import com.example.android.youtubefavorites.databinding.FragmentFavoritesListBinding
import com.example.android.youtubefavorites.domain.Video
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoritesFragment : Fragment() {

    private lateinit var viewModelFactory: FavoritesViewModelFactory

    private val viewModel: FavoritesViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(activity, viewModelFactory).get(FavoritesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavoritesListBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val dataSource = VideoDatabase.getInstance(application).videoDatabaseDao

        viewModelFactory = FavoritesViewModelFactory(dataSource, application)

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        binding.fab.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_favoritesListFragment_to_searchFragment)
        )

        binding.favoriteList.adapter = FavoriteListAdapter(object : FavoriteListAdapter.OnClickListener {
            override fun onClickFavorite(video: Video) {
                viewModel.onDeleteClicked(video)
            }

            override fun onClickShare(video: Video) {
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "http://m.youtube.com/watch?v=${video.videoId}"
                    )
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
            }
        })
        viewModel.deleteFavorite.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                view?.let {
                    Snackbar.make(it, getString(R.string.delete_from_favorites), Snackbar.LENGTH_SHORT).show()
                }
                viewModel.onDeleteClickedComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                viewModel.onClear()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
