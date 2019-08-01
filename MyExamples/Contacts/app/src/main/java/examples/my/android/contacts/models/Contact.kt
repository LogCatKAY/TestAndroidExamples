package examples.my.android.contacts.models

import android.net.Uri

data class Contact (
    var name: String,
    var numbers: ArrayList<String>,
    var thumbnail: Uri
)