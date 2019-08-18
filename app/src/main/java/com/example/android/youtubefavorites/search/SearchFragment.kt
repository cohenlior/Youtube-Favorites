package com.example.android.youtubefavorites.search


import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.android.youtubefavorites.R
import com.example.android.youtubefavorites.database.VideoDatabase
import com.example.android.youtubefavorites.databinding.SearchFragmentBinding
import com.example.android.youtubefavorites.domain.Video
import com.example.android.youtubefavorites.favorites.FavoritesViewModel
import com.example.android.youtubefavorites.favorites.FavoritesViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment() {
    /**
     * Lazily initialize our [SearchViewModel].
     */
    private lateinit var viewModelFactory: SearchViewModelFactory

    private val searchViewModel: SearchViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(activity, viewModelFactory).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SearchFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val dataSource = VideoDatabase.getInstance(application).videoDatabaseDao

        viewModelFactory = SearchViewModelFactory(dataSource, application)

        binding.viewModel = searchViewModel

        binding.lifecycleOwner = this

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchViewModel.getResults(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.resultList.adapter = ResultListAdapter(object : ResultListAdapter.OnClickListener {
            override fun onClickFavorite(video: Video) {
                searchViewModel.onFavoriteClicked(video)
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onClickThumbnail(video: Video, imageView: ImageView) {
                val extras = FragmentNavigatorExtras(imageView to getString(R.string.transition_image))
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(video),
                    extras
                )
            }
        })

        searchViewModel.addToFavorite.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                val massage: String
                when (it) {
                    true -> massage = getString(R.string.add_to_favorites)
                    false -> massage = getString(R.string.exist_in_favorites)
                }
                view?.let {
                    Snackbar.make(it, massage, Snackbar.LENGTH_LONG).show()
                }
                searchViewModel.onFavoriteClickedComplete()
            }
        })

        return binding.root
    }
}
