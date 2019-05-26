package database.android.melomanscollection.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Связующее звено между песней и альбомом - какая песня к какому альбому относится.
 * Связь - многий ко многим.
 * */
@Entity(
    tableName = "albumsong",
    foreignKeys =[
    ForeignKey(entity = Album::class, parentColumns = ["id"], childColumns = ["album_id"]),
    ForeignKey(entity = Song::class, parentColumns = ["id"], childColumns = ["song_id"])
])
data class AlbumSong(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "album_id") var albumId: Int,
    @ColumnInfo(name = "song_id") var songId: Int
)