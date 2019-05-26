package database.android.melomanscollection.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Хранит комментарии к альбомам
 * @param id
 * @param albumId
 * @param text
 * */
@Entity(tableName = "comment")
data class Comment (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "album_id") var albumId: Int,
    @ColumnInfo(name = "text") var text: String
) {
}