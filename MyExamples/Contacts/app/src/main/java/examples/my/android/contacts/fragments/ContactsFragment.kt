package examples.my.android.contacts.fragments


import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import examples.my.android.contacts.R
import examples.my.android.contacts.recyclerview.ContactsAdapter
import android.support.v7.widget.SearchView
import examples.my.android.contacts.recyclerview.ItemDecorator


class ContactsFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private val SEARCH_QUERY = "searchQuery"
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_contacts, container, false)
        recyclerView = v.findViewById(R.id.contacts_recycler)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        whiteNotificationBar(recyclerView)
        contactsAdapter = ContactsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(ItemDecorator(activity!!, 36))
        recyclerView.adapter = contactsAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loaderManager.restartLoader(0, null, this)
    }

    override fun onCreateLoader(p0: Int, bundle: Bundle?): Loader<Cursor> {
        var searchString: String? = null
        var selectionArgs: Array<String>? = null
        if (bundle != null) {
            searchString = ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?"
            selectionArgs = arrayOf(bundle.getString(SEARCH_QUERY, null) + '%')
        }

        return CursorLoader(
            activity!!,
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf<String>(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI),
            searchString, selectionArgs, ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
//        return CursorLoader(
//            activity!!,
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            arrayOf<String>(
//                ContactsContract.CommonDataKinds.Phone._ID,
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                ContactsContract.CommonDataKinds.Phone.NUMBER,
//                ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI),
//            null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
//        )
    }

    override fun onLoadFinished(p0: Loader<Cursor>, data: Cursor?) {
        contactsAdapter.swapCursor(data)
    }

    override fun onLoaderReset(p0: Loader<Cursor>) {

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_contacts_fragment, menu)

        val searchItem = menu!!.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                val bundle = Bundle()
                bundle.putString(SEARCH_QUERY, s)
                loaderManager.restartLoader(0, bundle, this@ContactsFragment)
                return false
            }
        })
    }

    private fun whiteNotificationBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
            activity!!.window.statusBarColor = Color.WHITE
        }
    }
}
