package com.coconuttest.tyu91.coconuttest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    //private String[] projection;
    private String androidID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        String displayName = CalendarContract.Calendars.CALENDAR_DISPLAY_NAME;

        String[] projection = new String[]
                {displayName,
                        CalendarContract.Calendars.OWNER_ACCOUNT,
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.DESCRIPTION,
                        CalendarContract.Events.EVENT_LOCATION,
                        CalendarContract.Events.ORGANIZER,
                        CalendarContract.Attendees.ATTENDEE_NAME,
                        CalendarContract.Attendees.ATTENDEE_RELATIONSHIP,
                };

        String[] projection1;

        projection1 = projection;

        projection = new String[]{CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
        };

        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";

        selection = "((" + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";

        String[] selectionArgs = new String[]{"hera@example.com", "com.example",
                "hera@example.com"};

        androidID = Settings.Secure.ANDROID_ID;
        Cursor c;

        c = this.getBaseContext().getContentResolver().query(CalendarContract.CONTENT_URI, projection1, selection, selectionArgs, null);
//        String[] columnNames = c.getColumnNames();

        projection = new String[]
                {CalendarContract.Events.ORGANIZER,
                        CalendarContract.Attendees.ATTENDEE_NAME,
                        CalendarContract.Attendees.ATTENDEE_RELATIONSHIP
                };
        String id = Settings.Secure.getString(getContentResolver(), androidID);
    }
}
