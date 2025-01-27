/*
 */

package com.apps.gtorettirsm

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import com.google.android.gms.ads.MobileAds
import com.apps.gtorettirsm.compose.ReceiptApp
import com.apps.gtorettirsm.ui.ReceiptTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReceiptActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Displaying edge-to-edge
        enableEdgeToEdge()
        setContent {
            ReceiptTheme {
                ReceiptApp()
            }
        }

        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
//            MobileAds.initialize(this@ReceiptActivity) {}
        }
        
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode === 1 && data != null) {
            var contactData: Uri? = data.data

            var cursor: Cursor = managedQuery(contactData, null, null, null, null)

            if (cursor!=null && !cursor.isClosed)
            cursor?.use {
                if (it.moveToFirst()) {

                    val name: String = cursor.getString(27)
                    Log.w("xxxxxxx name",name)

                    val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                    val hasPhoneNumber =
                        it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                            .toInt()
                    if (hasPhoneNumber > 0) {
                        val phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                            arrayOf(id),
                            null
                        )
                        phoneCursor?.use { pc ->
                            if (pc.moveToFirst()) {
                                var contactNumber =
                                    pc.getString(pc.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                Log.w("xxxxxxx contactNumber",contactNumber)
                            }
                        }
                    }
                }
            }
            cursor.close()
        }
    }
}
