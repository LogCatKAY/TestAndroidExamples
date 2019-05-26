package database.android.melomanscollection.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import database.android.melomanscollection.R
import database.android.melomanscollection.SongsActivity
import database.android.melomanscollection.database.Author
import kotlinx.android.synthetic.main.list_author_item.view.*

class AuthorAdapter(
    private val authorsList: List<Author>
) : RecyclerView.Adapter<AuthorAdapter.ViewHolder>(){

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val authorIdTextView: TextView
        val authorNameTextView: TextView

        init {
            v.setOnClickListener {
                val intent = SongsActivity.newAuthorIntent(it.context, Integer.parseInt(it.tv_author_item_id.text.toString()))
                it.context.startActivity(intent)
            }
            authorIdTextView = v.findViewById(R.id.tv_author_item_id)
            authorNameTextView = v.findViewById(R.id.tv_author_item_name)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_author_item, viewGroup, false)

        return ViewHolder(v)
    }



    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.authorIdTextView.text = authorsList[position].id.toString()
        viewHolder.authorNameTextView.text = authorsList[position].name.toString()
    }

    override fun getItemCount() = authorsList.size
}