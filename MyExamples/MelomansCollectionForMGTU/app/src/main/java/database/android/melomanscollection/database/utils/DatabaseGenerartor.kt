package database.android.melomanscollection.database.utils

import database.android.melomanscollection.database.*
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList

class DatabaseGenerartor() {

    private val authorsName : List<String> = listOf(
        "Black Sabbath",
        "Iron Maiden",
        "Metallica",
        "Judas Priest",
        "Motorhead",
        "Slayer",
        "Megadeth",
        "Venom",
        "Pantera",
        "Death",
        "Ozzy Osbourne",
        "Queensrcyhe",
        "Dream Theater",
        "Celtic Frost",
        "Manowar",
        "Dio",
        "Mercyful Fate",
        "Helloween",
        "Anthrax",
        "Bathory",
        "Napalm Death",
        "Carcass",
        "Alice in Chains",
        "Sepultura",
        "Scorpions",
        "Morbid Angel",
        "Tool",
        "Mayhem",
        "Korn",
        "Opeth",
        "Emperor",
        "Rainbow",
        "Soundgarden",
        "Exodus",
        "Possessed",
        "Blind Guardian",
        "Testament",
        "Faith No More",
        "King Diamond",
        "Accept"
    )

    private val beginning = arrayOf(
        "Kr",
        "Ca",
        "Ra",
        "Mrok",
        "Cru",
        "Ray",
        "Bre",
        "Zed",
        "Drak",
        "Mor",
        "Jag",
        "Mer",
        "Jar",
        "Mjol",
        "Zork",
        "Mad",
        "Cry",
        "Zur",
        "Creo",
        "Azak",
        "Azur",
        "Rei",
        "Cro",
        "Mar",
        "Luk"
    )
    private val middle = arrayOf(
        "air",
        "ir",
        "mi",
        "sor",
        "mee",
        "clo",
        "red",
        "cra",
        "ark",
        "arc",
        "miri",
        "lori",
        "cres",
        "mur",
        "zer",
        "marac",
        "zoir",
        "slamar",
        "salmar",
        "urak"
    )
    private val end = arrayOf("d", "ed", "ark", "arc", "es", "er", "der", "tron", "med", "ure", "zur", "cred", "mur")

    private val rand = Random()

    private fun generateNameSong(): String {
        return beginning[rand.nextInt(beginning.size)] +
                middle[rand.nextInt(middle.size)] +
                end[rand.nextInt(end.size)] + " " + beginning[rand.nextInt(beginning.size)] +
                middle[rand.nextInt(middle.size)] +
                end[rand.nextInt(end.size)]
    }

    private fun generateAlbumName(): String {
        return beginning[rand.nextInt(beginning.size)] +
                middle[rand.nextInt(middle.size)] +
                end[rand.nextInt(end.size)]
    }

    private fun generateAlbumDate(): String {
        return kotlin.random.Random.nextInt(1970, 2018).toString()
    }

    fun createAuthors(): List<Author> {
        val authors = ArrayList<Author>(authorsName.size)
        for (i in 0 until authorsName.size) {
            authors.add(Author(i, authorsName[i]))
        }
        return authors
    }

    fun createAlbums(): List<Album> {
        val capacity = 200
        val albums = ArrayList<Album>(capacity)
        for(i in 0 until capacity) {
            albums.add(Album(i, generateAlbumName(), generateAlbumDate()))
        }
        return albums
    }

    fun createSongs(): List<Song> {
        val capacity = 2000
        val songs = ArrayList<Song>(capacity)
        for (i in 0 until capacity) {
            songs.add(Song(i, generateNameSong(), "${(i * 0.05).toBigDecimal()
                .setScale(2, RoundingMode.HALF_UP)}", 0))
        }
        return songs
    }

    //в кадждом альбоме по 10 песен (всего 200 альбомов)
    fun createAlbumSongs(): List<AlbumSong> {
        val albumsongs = ArrayList<AlbumSong>()
        var offset = 0
        for (i in 0 until 200) {
            for (j in 0 until 10) {
                albumsongs.add(AlbumSong(j + offset, i, j + offset))
            }
            offset += 10
        }
        return albumsongs
    }

    //у каждого автора по 50 песен
    fun createAuthorSongs(): List<AuthorSong> {
        val authorsongs = ArrayList<AuthorSong>()
        var offset = 0
        for (i in 0 until authorsName.size) {
            for (j in 0 until 50) {
                authorsongs.add(AuthorSong(j + offset, i, j + offset))
            }
            offset += 50
        }
        return authorsongs
    }

}