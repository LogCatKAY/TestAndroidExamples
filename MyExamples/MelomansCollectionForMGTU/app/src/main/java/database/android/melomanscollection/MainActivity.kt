package database.android.melomanscollection

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import database.android.melomanscollection.database.MusicDao
import database.android.melomanscollection.database.utils.DatabaseGenerartor
import database.android.melomanscollection.fragments.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import database.android.melomanscollection.fragments.FavouriteFragment
import android.content.Context
import android.view.View
import androidx.core.content.edit
import com.google.android.material.tabs.TabLayout
import database.android.melomanscollection.fragments.AlbumFragment


//в таблицы Album, Song, AlbumSong добавить автогенерацию данных

//в MusicDao добавить методы query, insert, update, delete по примеру для таблицы Album

//показывать тост с содержимым всех трех таблиц 3*3

//добавить таблицы, Comment (комментарий к песне), CommentSong(какой комментарий к какой песне относится),
//CommentAlbum(какой комментарий к какому альбому относится), *Format (формат хранения песни)

class MainActivity : AppCompatActivity() {

    private lateinit var musicDao: MusicDao
    private lateinit var baseGenerator: DatabaseGenerartor
    private var isFirstTime: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        musicDao = (applicationContext as AppDelegate).musicDatabase.getMusicDao()
        baseGenerator = DatabaseGenerartor()

        if (isFirstTime() == true) {
            generateBase()
        }


        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_main, MainFragment())
                .commit()
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        nav_view.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawer_layout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            replaceDrawerFragment(menuItem.itemId)
            true
        }

        val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                changeTabs(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        }
        tab_layout.addOnTabSelectedListener(tabSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawers()
                } else {
                    drawer_layout.openDrawer(GravityCompat.START)
                }
                true
            }
            R.id.menu_generate_base -> {
                generateBase()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun generateBase() {
        musicDao.insertAlbums(baseGenerator.createAlbums())
        musicDao.insertSongs(baseGenerator.createSongs())
        musicDao.setLinksAlbumSongs(baseGenerator.createAlbumSongs())
        musicDao.insertAuthors(baseGenerator.createAuthors())
        musicDao.setLinksAuthorSongs(baseGenerator.createAuthorSongs())
    }

    private fun isFirstTime(): Boolean? {
        if (isFirstTime == null) {
            val preferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE)
            isFirstTime = preferences.getBoolean("firstTime", true)
            if (isFirstTime == true) {
                preferences.edit{
                    putBoolean("firstTime", false)
                }
            }
        }
        return isFirstTime
    }

    private fun replaceDrawerFragment(position: Int) {

        val fragment: Fragment
        when (position) {
            R.id.nav_first -> {
                fragment = changeTabs(tab_layout.selectedTabPosition)
                tab_layout.visibility = View.VISIBLE
            }
            R.id.nav_second -> {
                fragment = FavouriteFragment()
                tab_layout.visibility = View.GONE
            }
            else ->
                fragment = MainFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .commit()

    }

    private fun changeTabs(tabPosition: Int) : Fragment {
        val fragment: Fragment
        when (tabPosition) {
            0 -> {
                fragment = MainFragment()
            }
            1 -> {
                fragment = AlbumFragment()
            }
            else ->
                fragment = MainFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .commit()
        return fragment
    }
}
