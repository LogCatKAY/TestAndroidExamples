package database.android.melomanscollection

import android.app.Application
import androidx.room.Room
import database.android.melomanscollection.database.MusicDatabase

class AppDelegate : Application() {

    lateinit var musicDatabase: MusicDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        musicDatabase = Room.databaseBuilder(
            applicationContext,
            MusicDatabase::class.java,
            "music_database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }
}