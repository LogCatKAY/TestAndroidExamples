package database.android.melomanscollection.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import database.android.melomanscollection.OneSongActivity
import database.android.melomanscollection.R
import database.android.melomanscollection.database.Song



class SongAdapter(
    private val songsList: List<Song>
) : RecyclerView.Adapter<SongAdapter.ViewHolder>(){


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val songIdTextView: TextView
        val songNameTextView: TextView
        val songDurationTextView: TextView
        val songFavouriteTextView: TextView

        init {
            songIdTextView = v.findViewById(R.id.tv_song_item_id)
            songNameTextView = v.findViewById(R.id.tv_song_item_name)
            songDurationTextView = v.findViewById(R.id.tv_song_item_duration)
            songFavouriteTextView = v.findViewById(R.id.tv_song_item_favourite)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_song_item, viewGroup, false)

        return ViewHolder(v)
    }



    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.songIdTextView.text = songsList[position].id.toString()
        viewHolder.songNameTextView.text = songsList[position].name.toString()
        viewHolder.songDurationTextView.text = songsList[position].duration.toString()
        viewHolder.songFavouriteTextView.text = songsList[position].isFavourite.toString()
        viewHolder.itemView.setOnClickListener {
            val intent = OneSongActivity.newIntent(it.context, songsList[position].id)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = songsList.size
}