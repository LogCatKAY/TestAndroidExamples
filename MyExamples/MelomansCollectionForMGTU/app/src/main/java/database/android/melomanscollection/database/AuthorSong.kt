package database.android.melomanscollection.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Связующее звено между автором и песней - какой автор к какой песни относится.
 * Связь - многий ко многим.
 * */
@Entity(
    tableName = "authorsong",
    foreignKeys = [
        ForeignKey(entity = Author::class, parentColumns = ["id"], childColumns = ["author_id"]),
        ForeignKey(entity = Song::class, parentColumns = ["id"], childColumns = ["song_id"])
    ])
data class AuthorSong(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "author_id") var authorId: Int,
    @ColumnInfo(name = "song_id") var songId: Int
) {

    override fun toString(): String {
        return "AuthorSong(id=$id, authorId=$authorId, songId=$songId)"
    }
}