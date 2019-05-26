package database.android.melomanscollection.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Album::class, Song::class, AlbumSong::class, Comment::class, Author::class, AuthorSong::class], version = 1
)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun getMusicDao() : MusicDao

}