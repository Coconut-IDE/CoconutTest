package com.coconuttest.tyu91.coconuttest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CallLogsTestActivity extends AppCompatActivity {
    private int MY_PERMISSIONS_REQUEST_READ_CALL_LOGS = 1;
    private RecyclerView rvCallLogs;
    private CallLogsTestAdapter callLogsTestAdapter;
    private ArrayList<String> callLogsResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_logs_test);

        //set up CallLogs recycler view and adapter
        callLogsResults = new ArrayList<String>();
        rvCallLogs = findViewById(R.id.rvCallLogs);
        callLogsTestAdapter = new CallLogsTestAdapter(callLogsResults);
        rvCallLogs.setAdapter(callLogsTestAdapter);
        rvCallLogs.setLayoutManager(new LinearLayoutManager(this));

        //set up CallLogs permission request
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG},
                    MY_PERMISSIONS_REQUEST_READ_CALL_LOGS);
        }

        //define query projection field
        String[] projectionCallLog = new String[]{
//                CallLog.Calls.getLastOutgoingCall(this),
                CallLog.Calls.CACHED_NAME,
//                CallLog.Calls.DATE,
//                CallLog.Calls.DURATION,
                CallLog.Calls.NUMBER,
//                CallLog.Calls.VOICEMAIL_URI,
//                CallLog.Calls.TRANSCRIPTION
        };

        Cursor f;

        //query call logs for results
        f = this.getBaseContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, projectionCallLog, null, null, null);

        //populate call logs recycler view with query results
        while (f.moveToNext()) {
            callLogsResults.add(f.getString(1));
        }
        f.close();
    }
}
