package database.android.melomanscollection.database

import androidx.room.*

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbums(albums: List<Album>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(songs: List<Song>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setLinksAlbumSongs(linksAlbumSongs: List<AlbumSong>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthors(authors: List<Author>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setLinksAuthorSongs(linksAuthorSongs: List<AuthorSong>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(comments: List<Comment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: Comment)

    @Query("SELECT * FROM album")
    fun getAlbums(): List<Album>

    @Query("SELECT * FROM song")
    fun getSongs(): List<Song>

    @Query("SELECT * FROM song WHERE song.id = :songId")
    fun getSong(songId: Int): Song

    @Delete
    fun deleteAlbum(album: Album)

    @Query("SELECT * FROM song INNER JOIN albumsong ON song.id = albumsong.song_id WHERE album_id = :albumId")
    fun getSongsFromAlbum(albumId: Int): List<Song>

    @Query("SELECT * FROM author")
    fun getAuthors(): List<Author>

    @Query("SELECT * FROM author INNER JOIN authorsong ON author.id = authorsong.author_id WHERE song_id = :songId")
    fun getAuthorsFromSong(songId: Int): List<Author>

    @Query("SELECT * FROM song INNER JOIN authorsong on song.id = authorsong.song_id WHERE author_id = :authorId")
    fun getSongsFromAuthor(authorId: Int): List<Song>

    @Query("SELECT * FROM song WHERE isFavourite = 1")
    fun getFavouriteSongs(): List<Song>

    @Update
    fun updateSong(song: Song)
}