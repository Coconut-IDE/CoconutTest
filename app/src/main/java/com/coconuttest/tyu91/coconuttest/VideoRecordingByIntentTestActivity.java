package com.coconuttest.tyu91.coconuttest;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * This activity serves to test Coconut's ability to annotate personal data
 * relating to capturing video by intent
 *
 * @author Elijah Neundorfer 6/13/19
 * @version 6/13/19
 */
public class VideoRecordingByIntentTestActivity extends AppCompatActivity {

    private static final int VIDEO_REQUEST = 4242;
    private VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }

        setContentView(R.layout.activity_video_recording_by_intent_test);
        videoView = this.findViewById(R.id.videoView);

        Button photoButton = this.findViewById(R.id.recordVideoBtn);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent, VIDEO_REQUEST);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(VideoRecordingByIntentTestActivity.this, "No third party available for capturing videos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == VIDEO_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Uri videoUri = intent.getData();
            videoView.setVideoURI(videoUri);
        }
    }


}
