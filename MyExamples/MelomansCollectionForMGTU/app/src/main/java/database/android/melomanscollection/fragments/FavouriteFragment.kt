package database.android.melomanscollection.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.android.melomanscollection.AppDelegate

import database.android.melomanscollection.R
import database.android.melomanscollection.database.MusicDao
import database.android.melomanscollection.database.Song
import database.android.melomanscollection.recyclerview.SongAdapter
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.fragment_favourite.view.*


class FavouriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicDao: MusicDao
    private lateinit var favouriteSongs: List<Song>
    private lateinit var noFavouritesTextView: TextView
//    private var favouritesCount: Int = 0

    private val FAVOURITES_COUNT = "FavouriteFragment.favourites_count"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_favourite, container, false)

        musicDao = (activity!!.applicationContext as AppDelegate).musicDatabase.getMusicDao()
        favouriteSongs = musicDao.getFavouriteSongs()

        recyclerView = v.findViewById(R.id.favourite_recycler)
        noFavouritesTextView = v.findViewById(R.id.tv_no_favourites)

//        if (savedInstanceState != null) {
//            favouritesCount = savedInstanceState.getInt(FAVOURITES_COUNT)
//        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = SongAdapter(favouriteSongs)

        if (favouriteSongs.isNotEmpty()) {
            noFavouritesTextView.visibility = View.GONE
        }

        return v
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt(FAVOURITES_COUNT, favouritesCount)
//    }

//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        favouriteSongs = musicDao.getFavouriteSongs()
//        if (favouriteSongs.size != favouritesCount) {
//            recyclerView.layoutManager = LinearLayoutManager(activity)
//            recyclerView.adapter = SongAdapter(favouriteSongs)
//        }
//    }


}
