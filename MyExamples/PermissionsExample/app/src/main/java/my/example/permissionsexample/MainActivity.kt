package my.example.permissionsexample

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    companion object {
        const val WRITE_PERMISSION_RC = 1
    }

    private lateinit var mEditText: EditText
    private lateinit var mSaveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mEditText = findViewById<EditText>(R.id.et_write)
        mSaveButton = findViewById<Button>(R.id.btn_save)

        mSaveButton.setOnClickListener {
            val text = mEditText.text.toString()
            saveTextToFile(text)

        }
    }

    private fun saveTextToFile(text: String?) {
        if (TextUtils.isEmpty(text)) {
            toast("EditText пустой!")
        } else {
            requestPermissionAndSave(text)
        }
    }

    private fun requestPermissionAndSave(text: String?) {
        if (isWritePermissionGranted()) {
            writeToFile(text)
        } else {
            requestWritePermission()
        }
    }

    private fun requestWritePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            //show
            AlertDialog.Builder(this)
                .setMessage("Без этого разрешения работать не будет!")
                .setPositiveButton("Понятно") { dialog, which ->

                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        WRITE_PERMISSION_RC
                    )

                }.show()

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_PERMISSION_RC
            )
        }
    }

    private fun writeToFile(text: String?) {
        toast("$text записано в файл!")
    }

    private fun isWritePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != WRITE_PERMISSION_RC) return
        if (grantResults.size != 1) return

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val text = mEditText.text.toString()
            saveTextToFile(text)
        } else {
            AlertDialog.Builder(this)
                .setMessage("Вы можете предоставить разрешение в настройках устройства.")
                .setPositiveButton("Понятно", null)
                .show()
        }
    }
}
