package database.android.melomanscollection.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import database.android.melomanscollection.R
import database.android.melomanscollection.SongsActivity
import database.android.melomanscollection.database.Album
import kotlinx.android.synthetic.main.list_album_item.view.*
import kotlinx.android.synthetic.main.list_author_item.view.*

class AlbumAdapter(
    private val albumList: List<Album>
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>(){

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val albumIdTextView: TextView
        val albumNameTextView: TextView
        val albumDateTextView: TextView

        init {
            v.setOnClickListener {
                val intent = SongsActivity.newAlbumIntent(it.context, Integer.parseInt(it.tv_album_item_id.text.toString()))
                it.context.startActivity(intent)
            }
            albumIdTextView = v.findViewById(R.id.tv_album_item_id)
            albumNameTextView = v.findViewById(R.id.tv_album_item_name)
            albumDateTextView = v.findViewById(R.id.tv_album_item_date)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_album_item, viewGroup, false)

        return ViewHolder(v)
    }



    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.albumIdTextView.text = albumList[position].id.toString()
        viewHolder.albumNameTextView.text = albumList[position].name.toString()
        viewHolder.albumDateTextView.text = albumList[position].releaseDate.toString()
    }

    override fun getItemCount() = albumList.size
}