package examples.my.android.contacts.recyclerview

import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import examples.my.android.contacts.R
import examples.my.android.contacts.models.Contact
import android.content.Context
import android.widget.*



class ContactsAdapter: RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private lateinit var cursor: Cursor

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val contactName: TextView
        val contactNumber: TextView
        val contactImage: ImageView

        init {
            contactName = v.findViewById(R.id.tv_name)
            contactNumber = v.findViewById(R.id.tv_number)
            contactImage = v.findViewById(R.id.iv_thumbnail)
        }

        fun bind(contact: Contact) {
            contactName.text = contact.name
            val numbersSize = contact.numbers.size
            contactNumber.text = ""
            for (i in 0 until numbersSize) {
                contactNumber.append(contact.numbers[i])
                if (i != (numbersSize - 1)) {
                    contactNumber.append(",")
                }
            }
            contactImage.setImageURI(contact.thumbnail)
        }

        fun getMessage() : String {
            return "Selected: ${contactName.text}, ${contactNumber.text}"
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.contact_item, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (cursor.moveToPosition(position)) {
            val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val name: String = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            val numbers: ArrayList<String> = getAllNumbers(cursor, id, holder.itemView.context)
            val imageUriString: String? = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))

            var imageUri: Uri = Uri.parse("")
            if (imageUriString != null) {
                imageUri = Uri.parse(imageUriString)
            }
            holder.bind(Contact(name, numbers, imageUri))
            holder.itemView.setOnClickListener {
                Toast.makeText(it.context, holder.getMessage(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (::cursor.isInitialized){
            cursor.count
        } else {
            0
        }
    }

    fun swapCursor(newCursor: Cursor?) {
        val isInitCursor = ::cursor.isInitialized
        if (isInitCursor) {
            if (newCursor != null && newCursor != cursor) {
                if (isInitCursor) {
                    cursor.close()
                }
                cursor = newCursor
            }
        } else {
            if (newCursor != null) {
                if (isInitCursor) {
                    cursor.close()
                }
                cursor = newCursor
            }
        }
        notifyDataSetChanged()
    }

    private fun getAllNumbers(cursor: Cursor, id: String, context: Context) : ArrayList<String> {
        var numbers: ArrayList<String> = ArrayList<String>()
        val phoneCursor: Cursor? = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(id), null
            )
        while (phoneCursor!!.moveToNext()) {
            numbers.add(phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
        }
        phoneCursor.close()
        return numbers
    }
}