package com.coconuttest.tyu91.coconuttest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import static android.Manifest.permission;

/**
 * This activity serves to test Annotations on three different APIs -
 *
 * MediaRecorder
 * AudioRecord
 * and Intent as it relates to Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
 *
 * In order to accomplish this, the activity provides the ability to records using each of these APIs
 * and play them back. Keep in mind that this activity is designed to test the plugin Coconut, and
 * not designed to be the most effective recorder.
 *
 * @author Elijah Neundorfer
 * @version 6/7/19
 *
 */
public class MicrophoneTestActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {

    //General use variables
    Button recordWithMediaRecorder, stopRecording, recordWithAudioRecord, recordWithIntent, play;
    private static final String TAG = "MicrophoneDevFeedback";

    //For storing the audio. The different recorders require that we make use of these three different variables.
    File mAudioFile;
    Uri mAudioFileURI;
    FileOutputStream fos;

    //This variable is used only for the MediaRecorder functionality
    private MediaRecorder mRecorder;

    //The below variables are needed for the AudioRecord class to function properly
    private boolean threadActive;
    private int sampleRate = 44100;
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);

    //For recording audio with intent
    public static final int RECORD_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microphone_test);

        recordWithMediaRecorder = this.findViewById(R.id.recordWithMediaRecorderBtn);
        recordWithMediaRecorder.setOnClickListener(this);

        recordWithAudioRecord = this.findViewById(R.id.recordWithAudioRecordBtn);
        recordWithAudioRecord.setOnClickListener(this);

        recordWithIntent = this.findViewById(R.id.recordWithIntentBtn);
        recordWithIntent.setOnClickListener(this);

        stopRecording = this.findViewById(R.id.stopRecordingBtn);
        stopRecording.setOnClickListener(this);
        stopRecording.setEnabled(false);

        play = this.findViewById(R.id.playBtn);
        play.setOnClickListener(this);
        play.setEnabled(false);

        if (ActivityCompat.checkSelfPermission(this, permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission.RECORD_AUDIO},
                    1);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == recordWithMediaRecorder) {
            //Records audio with the MediaRecorder
            disableAllButtons();
            recordWithMediaRecorder();
            stopRecording.setEnabled(true);
        } else if (v == recordWithAudioRecord) {
            //Records audio with the AudioRecord
            disableAllButtons();
            recordWithAudioRecord();
            stopRecording.setEnabled(true);
        } else if (v == recordWithIntent) {
            //Records audio using a third party app. Data is returned via a listener
            disableAllButtons();
            try {
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent,  RECORD_REQUEST );
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No third party available for recording.", Toast.LENGTH_SHORT).show();
                enableAllButtonsButStop();
            }
        } else if (v == stopRecording) {
            //Stops all active recordings
            stopRecording();
            enableAllButtonsButStop();

        } else if (v == play) {
            //Plays back the recording using one of two methods.
            disableAllButtons();
            try {
                MediaPlayer mediaPlayer = MediaPlayer.create(this, mAudioFileURI);
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.start();
            } catch (NullPointerException e) {
                Log.e("MediaPlayer", "Null Pointer Exception" + e);

                // AudioRecord does not work with the MediaPlayer API and therefore we have to use the AudioTrack API.
                // TODO: While this plays back the audio, it's doing so at higher speeds than it was recorded at. Elijah tried for ~1.5 hours and couldn't resolve this. Marking it an not urgent and will come back to it at a later time.

                byte[] audioData;

                int musicLength = (int) (mAudioFile.length() / 2);
                short[] music = new short[musicLength];

                try {
                    InputStream inputStream = new FileInputStream(mAudioFileURI.getPath());
                    audioData = new byte[bufferSize];

                    AudioTrack audioTrack = new AudioTrack.Builder()
                            .setAudioAttributes(new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .build())
                            .setAudioFormat(new AudioFormat.Builder()
                                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                    .setSampleRate(sampleRate)
                                    .setChannelMask(AudioFormat.CHANNEL_IN_STEREO)
                                    .build())
                            .setBufferSizeInBytes(musicLength)
                            .build();
                    audioTrack.play();
                    int i;

                    while ((i = inputStream.read(audioData)) != -1) {
                        audioTrack.write(audioData, 0, i);
                    }
                } catch (FileNotFoundException fe) {
                    Log.e("AudioTrack", "File not found");
                } catch (IOException io) {
                    Log.e("AudioTrack", "IO Exception");
                }
                enableAllButtonsButStop();
            }
        }
    }

    /**
     * Used as a quick shortcut for disabling all the buttons in the app
     */
    private void disableAllButtons() {
        recordWithMediaRecorder.setEnabled(false);
        recordWithAudioRecord.setEnabled(false);
        recordWithIntent.setEnabled(false);
        stopRecording.setEnabled(false);
        play.setEnabled(false);
    }

    /**
     * Used as a quick shortcut for enabling all buttons in the app.
     *
     * We leave the stop button off of this list because it's typically left disabled.
     */
    private void enableAllButtonsButStop() {
        recordWithMediaRecorder.setEnabled(true);
        recordWithAudioRecord.setEnabled(true);
        recordWithIntent.setEnabled(true);
        stopRecording.setEnabled(false);
        play.setEnabled(true);
    }


    /**
     * Records audio using a MediaRecorder object.
     */
    public void recordWithMediaRecorder() {
        //sets our global file variable to the proper parameters
        try {
            mAudioFile = File.createTempFile("MediaRecorder", ".3gp", this.getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mAudioFile == null) {
            Log.d(TAG,"ERROR - No File found to record to");
            return;
        }

        //Creating the MediaRecorder to the proper parameters
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        /**
         * In the MediaRecorder API, there are three methods with matching names but different parameters that we are
         * analyzing with Coconut. These methods are called setOutputFile. We call a random version of this method
         * every time we set up the recorder. That is displayed below.
         */
        int toUse = ThreadLocalRandom.current().nextInt(0, 3);
        switch (toUse) {
            case 0:
                String mAudioFileString = mAudioFile.getAbsolutePath();
                mRecorder.setOutputFile(mAudioFileString);
                break;
            case 1:
                mRecorder.setOutputFile(mAudioFile);
                break;
            case 2:
                FileDescriptor fd;
                try {
                    fos = new FileOutputStream(mAudioFile);
                    fd = fos.getFD();
                    mRecorder.setOutputFile(fd);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Cannot Set File", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Cannot Set File", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        try {
            //Preparing and starting the recording
            mRecorder.prepare();
            mRecorder.start();
        } catch (IllegalStateException e) {
            // start: it is called before prepare()
            // prepare: it is called after start() or before setOutputFormat()
            Log.e("Audio", "recording failed");
            Toast.makeText(this, "Cannot Record Audio", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // prepare() fails
            Log.e("Audio", "prepare() failed");
            Toast.makeText(this, "Cannot Record Audio", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Records audio using a AudioRecord object.
     */
    public void recordWithAudioRecord() {
        //sets our global file variable to the proper parameters
        try {
            mAudioFile = File.createTempFile("AudioRecord", ".pcm", this.getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mAudioFile == null) {
            Log.d(TAG,"ERROR - No File found to record to");
            return;
        }

        //AudioRecorder requires a thread to be run properly
        Thread mAudioRecordThread = new Thread(new Runnable() {
            public void run() {

                //Creating our AudioRecord object
                AudioRecord mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, bufferSize);

                //Verifying our object was created successfully
                if (mAudioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                    Log.d("AudioRecord", "Unable to initialize AudioRecord");
                    throw new RuntimeException("Unable to initialize AudioRecord");
                }

                //Start the recording
                mAudioRecord.startRecording();

                //Creating the FileOutputStream we will write to
                FileOutputStream os = null;
                try {
                    os = new FileOutputStream(mAudioFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //Creating the buffer we will read to
                byte[] buffer = new byte[bufferSize];

                //This code loops until the recording is stopped by the user
                while(threadActive) {

                    //Reading from the AudioRecord object. Data goes to the buffer parameter.
                    mAudioRecord.read(buffer, 0, buffer.length);

                    try {
                        //Writing data from our buffer to the FileOutputStream
                        assert os != null;
                        os.write(buffer, 0, buffer.length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //In order to test Coconut on all versions of AudioRecord.read, we initialize all the necessary variables and then call the method with every possible parameter combination.
                    //If this is not commented out, however, the recording feature will not work.
                    /*
                    short[] bufferInShort = new short[bufferElements2Rec * bytesPerElement / 4];
                    float[] bufferInFloat = new float[bufferElements2Rec * bytesPerElement / 4];
                    byte[] bufferInBytes = new byte[bufferElements2Rec * bytesPerElement];
                    ByteBuffer byteBuffer = ByteBuffer.allocate(bufferElements2Rec * bytesPerElement);

                    mAudioRecord.read(bufferInShort, 0, bufferElements2Rec * bytesPerElement / 4, AudioRecord.READ_NON_BLOCKING);
                    mAudioRecord.read(byteBuffer, bufferElements2Rec * bytesPerElement, AudioRecord.READ_NON_BLOCKING);
                    mAudioRecord.read(bufferInShort, 0, bufferElements2Rec * bytesPerElement / 4);
                    mAudioRecord.read(bufferInFloat, 0, bufferElements2Rec * bytesPerElement / 4, AudioRecord.READ_NON_BLOCKING);
                    mAudioRecord.read(bufferInBytes, 0, bufferElements2Rec * bytesPerElement, AudioRecord.READ_NON_BLOCKING);
                    mAudioRecord.read(byteBuffer, bufferElements2Rec * bytesPerElement);
                    mAudioRecord.read(bufferInBytes, 0, bufferElements2Rec * bytesPerElement);
                    */
                }

                //Clean up
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mAudioRecord.stop();
                mAudioRecord.release();
            }
        }, "AudioRecorder Thread");

        //Setting the thread to active so it will loop and starting the thread
        threadActive = true;
        mAudioRecordThread.start();
    }

    /**
     * As we have multiple possible ways to record in this activity, the stopRecording method checks every associated variable with each and stops everything.
     */
    public void stopRecording() {
        //Halting the MediaRecording if it exists
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }
        //If a FileOutputStream was created, we close it.
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Closing the thread that's linked to the AudioTrack recording
        threadActive = false;
        //Syncing data from the audiofile to the URI
        if (mAudioFile != null) {
            mAudioFileURI = Uri.fromFile(mAudioFile);
        }
    }

    /**
     * Overrides the onPause method so that when the activity is left the player and recorder will always stop.
     */
    @Override
    protected void onPause() {
        stopRecording();
        super.onPause();
    }

    /**
     * Overrides the onDestroy method so that when the activity is destroyed the player and recorder will always stop.
     */
    @Override
    public void onDestroy() {
        stopRecording();
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
        enableAllButtonsButStop();
    }

    /**
     * Used for getting data from the third party activity we use to record audio, called by intent
     *
     * @param requestCode The request code we sent with the intent for verification
     * @param resultCode
     * @param data The data from the third party activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RECORD_REQUEST) {
            mAudioFileURI = data.getData();
            enableAllButtonsButStop();
        }
    }
}