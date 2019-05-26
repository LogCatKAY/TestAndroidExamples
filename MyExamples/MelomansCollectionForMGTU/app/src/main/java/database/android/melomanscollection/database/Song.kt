package database.android.melomanscollection.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class  Song(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "duration") var duration: String,
    @ColumnInfo(name = "isFavourite") var isFavourite: Int
) {
    override fun toString(): String {
        return "Song(id=$id, name='$name', duration='$duration', isFavourite=$isFavourite)"
    }
}
