package database.android.melomanscollection

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import database.android.melomanscollection.database.MusicDao
import database.android.melomanscollection.database.Song
import kotlinx.android.synthetic.main.activity_one_song.*

class OneSongActivity : AppCompatActivity() {

    private lateinit var song: Song
    private lateinit var musicDao: MusicDao

    companion object {
        val SONG_ID = "songId"
        fun newIntent(context: Context, songId: Int) : Intent {
            val intent = Intent(context, OneSongActivity::class.java)
            intent.putExtra(SONG_ID, songId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_song)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        musicDao = (applicationContext as AppDelegate).musicDatabase.getMusicDao()

        val songId = intent.getIntExtra(SONG_ID, 0)
        song = musicDao.getSong(songId)

        initControls(song)

        cb_is_favourite.setOnCheckedChangeListener{
            button: CompoundButton?, b: Boolean ->

            val newSong = song
            newSong.isFavourite = when(button!!.isChecked) {
                true -> {
                    Toast.makeText(this, R.string.favourite_yes, Toast.LENGTH_SHORT).show()
                    1
                }
                false -> {
                    Toast.makeText(this, R.string.favourite_no, Toast.LENGTH_SHORT).show()
                    0
                }
            }
            musicDao.updateSong(newSong)
            initControls(newSong)
        }
    }

    private fun initControls(song: Song) {
        tv_one_song_id.text = song.id.toString()
        tv_one_song_name.text = song.name.toString()
        tv_one_song_duration.text = song.duration.toString()
        cb_is_favourite.isChecked = when(song.isFavourite) {
            0 -> false
            1 -> true
            else -> false
        }
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
