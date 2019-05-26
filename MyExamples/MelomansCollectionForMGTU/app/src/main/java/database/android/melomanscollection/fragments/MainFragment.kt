package database.android.melomanscollection.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.android.melomanscollection.AppDelegate
import database.android.melomanscollection.R
import database.android.melomanscollection.database.MusicDao
import database.android.melomanscollection.recyclerview.AuthorAdapter

//тут будут авторы песен
class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicDao: MusicDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        musicDao = (activity!!.applicationContext as AppDelegate).musicDatabase.getMusicDao()

        recyclerView = v.findViewById(R.id.main_recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AuthorAdapter(musicDao.getAuthors())


        return v
    }


}
