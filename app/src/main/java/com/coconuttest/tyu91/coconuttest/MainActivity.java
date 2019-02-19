package com.coconuttest.tyu91.coconuttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //TODO: add back buttons for each test activity back to this main activity
    //TODO: app crashes initially if permissions not enabled. This behavior is expected; for now let the app crash, give permission, and reopen app.

    Button calendarBtn, contactsBtn, smsBtn, callLogsBtn, microphoneBtn, camera2Btn, cameraByIntentBtn, recordVideoBtn, sensorsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //go to calendar test activity
        calendarBtn = findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //go to contacts test activity
        contactsBtn = findViewById(R.id.contactsBtn);
        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //go to sms test activity
        smsBtn = findViewById(R.id.smsBtn);
        smsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SmsTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //go to call logs test activity
        callLogsBtn = findViewById(R.id.callLogsBtn);
        callLogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CallLogsTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //go to microphone test activity
        microphoneBtn = findViewById(R.id.microphoneBtn);
        microphoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MicrophoneTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //go to camera2 test activity
        camera2Btn = findViewById(R.id.cameraBtn);
        camera2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Camera2APITestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //go to camera by intent test activity
        cameraByIntentBtn = findViewById(R.id.cameraByIntentBtn);
        cameraByIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraByIntentTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //go to video recording test activity
        recordVideoBtn = findViewById(R.id.recordVideoBtn);
        recordVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoRecordingTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //go to sensors test activity
        sensorsBtn = findViewById(R.id.sensorsBtn);
        sensorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorTestActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
