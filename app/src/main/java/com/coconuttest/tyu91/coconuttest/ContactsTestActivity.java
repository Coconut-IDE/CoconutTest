package com.coconuttest.tyu91.coconuttest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Build;

import java.util.ArrayList;

public class ContactsTestActivity extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 2;
    private String[] projection;
    private String selection;
    private RecyclerView rvContacts;
    private ContactsTestAdapter contactsTestAdapter;
    private ArrayList<String> contactsResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_test);

        //set up Contacts recycler view and adapter
        contactsResults = new ArrayList<String>();
        rvContacts = findViewById(R.id.rvContacts);
        contactsTestAdapter = new ContactsTestAdapter(contactsResults);
        rvContacts.setAdapter(contactsTestAdapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));


        //set up SMS permission requests
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        //define projection query field
        String[] projection = new String[] {
//                ContactsContract.Data.RAW_CONTACT_ID,
//                ContactsContract.Data._ID,
//                ContactsContract.Data.LOOKUP_KEY,
//                ContactsContract.Data.NAME_RAW_CONTACT_ID,
//                ContactsContract.Data.PHOTO_FILE_ID,
//                ContactsContract.Data.PHOTO_ID,
//                ContactsContract.Data.PHOTO_THUMBNAIL_URI,
//                ContactsContract.Data.PHOTO_URI,
//                ContactsContract.Data.DISPLAY_NAME,
//                ContactsContract.Data.DISPLAY_NAME_ALTERNATIVE,
//                ContactsContract.Data.DISPLAY_NAME_PRIMARY,
//                ContactsContract.Data.DISPLAY_NAME_SOURCE,
//
//                ContactsContract.Contacts._ID,
//                ContactsContract.Contacts.LOOKUP_KEY,
//                ContactsContract.Contacts.NAME_RAW_CONTACT_ID,
//                ContactsContract.Contacts.PHOTO_FILE_ID,
//                ContactsContract.Contacts.PHOTO_ID,
//                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
//                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.Contacts.DISPLAY_NAME,
//                ContactsContract.Contacts.DISPLAY_NAME_ALTERNATIVE,
//                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
//                ContactsContract.Contacts.DISPLAY_NAME_SOURCE,
//
//
//                ContactsContract.Profile._ID,
//                ContactsContract.Profile.LOOKUP_KEY,
//                ContactsContract.Profile.NAME_RAW_CONTACT_ID,
//                ContactsContract.Profile.PHOTO_FILE_ID,
//                ContactsContract.Profile.PHOTO_ID,
//                ContactsContract.Profile.PHOTO_THUMBNAIL_URI,
//                ContactsContract.Profile.PHOTO_URI,
//               ContactsContract.Profile.DISPLAY_NAME,
//               ContactsContract.Profile.DISPLAY_NAME_ALTERNATIVE,
//                ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
//                ContactsContract.Profile.DISPLAY_NAME_SOURCE,
//
//                ContactsContract.RawContacts._ID,
//                ContactsContract.RawContacts.ACCOUNT_NAME,
//                ContactsContract.RawContacts.DISPLAY_NAME_ALTERNATIVE,
//                ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,
//                ContactsContract.RawContacts.DISPLAY_NAME_SOURCE,
//                ContactsContract.RawContacts.SOURCE_ID

        };

        Cursor d;

        //query for Contacts results
        d = this.getBaseContext().getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                null);
        while (d.moveToNext()) {
            contactsResults.add(d.getString(0));
        }
        d.close();

    }

    //Contacts requires additional permissions code to work properly
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


}


