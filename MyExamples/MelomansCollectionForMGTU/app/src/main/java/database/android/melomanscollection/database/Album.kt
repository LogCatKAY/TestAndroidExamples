package database.android.melomanscollection.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class Album(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "release") var releaseDate: String
) {

    override fun toString(): String {
        return "Album(id=$id, name=$name, releaseDate=$releaseDate)"
    }


}
