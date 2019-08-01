package examples.my.android.contacts

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.SearchView
import examples.my.android.contacts.fragments.ContactsFragment
import examples.my.android.contacts.recyclerview.ContactsAdapter
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.os.Build
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val CONTACTS_PERMISSION_RC = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionAndContinue(savedInstanceState)
    }

    private fun addFragment(savedState: Bundle?) {
        if(savedState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_main, ContactsFragment())
                .commit()
        }
    }

    private fun requestPermissionAndContinue(savedState: Bundle?) {
        if (isContactsPermissionGranted()) {
            addFragment(savedState)
        } else {
            requestContactsPermission()
        }
    }

    private fun requestContactsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {

            //show
            AlertDialog.Builder(this)
                .setMessage("Без этого разрешения работать не будет!")
                .setPositiveButton("Понятно") { dialog, which ->

                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        CONTACTS_PERMISSION_RC
                    )

                }.show()

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                CONTACTS_PERMISSION_RC
            )
        }
    }

    fun isContactsPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != CONTACTS_PERMISSION_RC) return
        if (grantResults.size != 1) return

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_main, ContactsFragment())
                .commit()
        } else {
            AlertDialog.Builder(this)
                .setMessage("Вы можете предоставить разрешение в настройках устройства.")
                .setPositiveButton("Понятно") { _, _ -> finish() }
                .show()
        }
    }


}
