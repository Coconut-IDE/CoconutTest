package com.coconuttest.tyu91.coconuttest;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import me.tianshili.annotationlib.commons.Visibility;

/**
 * This activity serves to test Coconut's ability to annotate personal data
 * relating to capturing pictures by intent
 *
 * @author Elijah Neundorfer 6/10/19
 * @version 6/10/19
 */
public class CameraByIntentTestActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1776;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }

        setContentView(R.layout.activity_camera_by_intent_test);
        imageView = this.findViewById(R.id.imageView);

        Button photoButton = this.findViewById(R.id.takePictureBtn);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {

                    Intent intent;
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(CameraByIntentTestActivity.this,
                            "No third party available for capturing images.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}