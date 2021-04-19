package com.mvhs.technovation.speechapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 300;
    private Button listening;
    private Button realWorld;
    private Button speaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_listening);

        listening = findViewById(R.id.listening);
        realWorld = findViewById(R.id.realWorld);
        speaking = findViewById(R.id.speaking);

        if (!arePermissionsGranted()) {
            requestNeededPermissions();
        }
    }

    private void requestNeededPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    private boolean arePermissionsGranted() {
        int recordAudioPermResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        int writeExternalStoragePermResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return recordAudioPermResult == PackageManager.PERMISSION_GRANTED && writeExternalStoragePermResult == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 300:

                boolean recordAudioPermissionAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                boolean writeExtStoragePermissionAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;

                if (recordAudioPermissionAccepted && writeExtStoragePermissionAccepted) {
                    listening.setEnabled(true);
                    realWorld.setEnabled(true);
                    speaking.setEnabled(true);
                }
                else {
                    listening.setEnabled(false);
                    realWorld.setEnabled(false);
                    speaking.setEnabled(false);
                    Toast.makeText(this, "Permissions need to be granted for features to work", Toast.LENGTH_LONG).show();
                }
                break;

        }

    }

    public void onClickListening (View view)
    {
        Intent intent = new Intent(this, Listening.class);
        startActivity(intent);
    }

    public void onClickRealWorld (View view)
    {

        Intent intent = new Intent(this, RealWorld.class);
        startActivity(intent);
    }

    public void onClickGame (View view)
    {
        Intent intent = new Intent(this, PongMain.class);
        startActivity(intent);
    }



    public void onClickSpeaking (View view)
    {
        Intent intent = new Intent(this, SpeakingActivity.class);
        startActivity(intent);
    }

}
