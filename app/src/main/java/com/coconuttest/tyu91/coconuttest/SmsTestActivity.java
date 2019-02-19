package com.coconuttest.tyu91.coconuttest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SmsTestActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    private RecyclerView rvSms;
    private SmsTestAdapter smsTestAdapter;
    private ArrayList<String> smsResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_test);

        //set up SMS recycler view and adapter
        smsResults = new ArrayList<String>();
        rvSms = findViewById(R.id.rvSms);
        smsTestAdapter = new SmsTestAdapter(smsResults);
        rvSms.setAdapter(smsTestAdapter);
        rvSms.setLayoutManager(new LinearLayoutManager(this));

        //set up SMS permission request
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);
        }

        //define projection query field
        String[] projectionSms = new String[]{
//                Telephony.BaseMmsColumns.SEEN,
//                Telephony.BaseMmsColumns.CONTENT_LOCATION,
//                Telephony.BaseMmsColumns.CREATOR,
//                Telephony.BaseMmsColumns.DATE,
//                Telephony.BaseMmsColumns.DATE_SENT,
//                Telephony.BaseMmsColumns.DELIVERY_TIME,
//                Telephony.BaseMmsColumns.MESSAGE_ID,
//                Telephony.BaseMmsColumns.READ,
//                Telephony.BaseMmsColumns.READ_STATUS,
//                Telephony.BaseMmsColumns.RESPONSE_STATUS,
//                Telephony.BaseMmsColumns.RESPONSE_TEXT,
//                Telephony.BaseMmsColumns.READ_REPORT,
//                Telephony.BaseMmsColumns.RETRIEVE_TEXT,
//                Telephony.BaseMmsColumns.RETRIEVE_TEXT_CHARSET,
//                Telephony.BaseMmsColumns.SUBJECT,
//                Telephony.BaseMmsColumns.SUBJECT_CHARSET,
//                Telephony.BaseMmsColumns.MESSAGE_BOX,
//
//                Telephony.CanonicalAddressesColumns.ADDRESS,
//
//                Telephony.Mms.SEEN,
//                Telephony.Mms.CONTENT_LOCATION,
//                Telephony.Mms.CREATOR,
//                Telephony.Mms.DATE,
//                Telephony.Mms.DATE_SENT,
//                Telephony.Mms.DELIVERY_TIME,
//                Telephony.Mms.MESSAGE_ID,
//                Telephony.Mms.READ,
//                Telephony.Mms.READ_STATUS,
//                Telephony.Mms.RESPONSE_STATUS,
//                Telephony.Mms.RESPONSE_TEXT,
//                Telephony.Mms.READ_REPORT,
//                Telephony.Mms.RETRIEVE_TEXT,
//                Telephony.Mms.RETRIEVE_TEXT_CHARSET,
//                Telephony.Mms.SUBJECT,
//                Telephony.Mms.SUBJECT_CHARSET,
//                Telephony.Mms.MESSAGE_BOX,
//
//                String.valueOf(Telephony.MmsSms.CONTENT_CONVERSATIONS_URI),
//                String.valueOf(Telephony.MmsSms.SEARCH_URI),
//                String.valueOf(Telephony.MmsSms.CONTENT_CONVERSATIONS_URI),
//                String.valueOf(Telephony.MmsSms.CONTENT_DRAFT_URI),
//                String.valueOf(Telephony.MmsSms.CONTENT_FILTER_BYPHONE_URI),
//                String.valueOf(Telephony.MmsSms.CONTENT_LOCKED_URI),
//                String.valueOf(Telephony.MmsSms.CONTENT_UNDELIVERED_URI),
//                String.valueOf(Telephony.MmsSms.CONTENT_URI),
//
//                Telephony.Sms.SEEN,
//                Telephony.Sms.CREATOR,
//                Telephony.Sms.DATE,
//                Telephony.Sms.DATE_SENT,
//                Telephony.Sms.READ,
//                Telephony.Sms.SUBJECT,
//
//                Telephony.TextBasedSmsColumns.SEEN,
                Telephony.TextBasedSmsColumns.CREATOR,
//                Telephony.TextBasedSmsColumns.DATE,
//                Telephony.TextBasedSmsColumns.DATE_SENT,
//                Telephony.TextBasedSmsColumns.READ,
//                Telephony.TextBasedSmsColumns.SUBJECT,
                Telephony.TextBasedSmsColumns.BODY,

//                Telephony.Threads.DATE,


        };

        Cursor cursor;

        //query for SMS results
        cursor = this.getBaseContext().getContentResolver().query(
                Telephony.Sms.CONTENT_URI,
                projectionSms,
                null,
                null,
                null);

        //populate SMS recycler view with query results
        while (cursor.moveToNext()) {
            smsResults.add(cursor.getString(1));
        }
        cursor.close();

        //TODO: figure out a way to parse Uri.parse instead of CONTENT_URI
        Cursor uriCursor;
        uriCursor = this.getBaseContext().getContentResolver().query(Uri.parse("content://sms/inbox"), projectionSms, null, null, null);

    }


}
