package database.android.melomanscollection

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.android.melomanscollection.database.MusicDao
import database.android.melomanscollection.database.Song
import database.android.melomanscollection.recyclerview.SongAdapter

class SongsActivity : AppCompatActivity() {

    private lateinit var musicDao: MusicDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var songsList: List<Song>

    companion object {
        val AUTHOR_ID = "authorId"
        val ALBUM_ID = "albumId"
        fun newAuthorIntent(context: Context, authorId: Int) : Intent {
            val intent = Intent(context, SongsActivity::class.java)
            intent.putExtra(AUTHOR_ID, authorId)
            return intent
        }
        fun newAlbumIntent(context: Context, albumId: Int) : Intent {
            val intent = Intent(context, SongsActivity::class.java)
            intent.putExtra(ALBUM_ID, albumId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        musicDao = (applicationContext as AppDelegate).musicDatabase.getMusicDao()

        val authorId = intent.getIntExtra(AUTHOR_ID, -1)
        val albumId = intent.getIntExtra(ALBUM_ID, -1)

        songsList = when {
            authorId != -1 -> musicDao.getSongsFromAuthor(authorId)
            albumId != -1 -> musicDao.getSongsFromAlbum(albumId)
            else -> listOf(
                Song(0, "Empty", "0", 0)
            )
        }


        recyclerView = findViewById(R.id.main_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SongAdapter(songsList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
