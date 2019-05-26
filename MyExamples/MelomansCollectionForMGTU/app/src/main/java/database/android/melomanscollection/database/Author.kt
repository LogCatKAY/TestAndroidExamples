package database.android.melomanscollection.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "author")
data class Author (
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String
) {
    override fun toString(): String {
        return "Author(id=$id, name='$name')"
    }
}