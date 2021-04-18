package com.mvhs.technovation.speechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Random;
import android.widget.TextView;
import java.util.Scanner;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import static java.lang.String.format;

import android.os.Handler;
public class RealWorld extends AppCompatActivity {

    private Button play, stop, record;
    private MediaRecorder myAudioRecorder;
    private String outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_world);

       TextView textView = (TextView) findViewById(R.id.totua);


        String[] questions = new String[15];

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("RealQuestions.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            int i=0;
            while ((mLine = reader.readLine()) != null) {
                questions[i] = mLine;
                i++;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }


        Random randomQuests = new Random();
        String randomQuestion = questions[randomQuests.nextInt(15)];
        textView.setText(randomQuestion);

        play = (Button) findViewById(R.id.play);
       stop = (Button) findViewById(R.id.stop);
        record = (Button) findViewById(R.id.record);
        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

       record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException ise) {
                    // make something ...
                } catch (IOException ioe) {
                    // make something
                }
                record.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

               final int interval = 2000; // 1 Second
                Handler handler = new Handler();
                Runnable runnable = new Runnable(){
                    public void run() {
                        Toast.makeText(RealWorld.this, "5 more seconds !", Toast.LENGTH_SHORT).show();
                        stop.setVisibility(View.VISIBLE);
                    }
                };
                handler.postAtTime(runnable, System.currentTimeMillis()+interval);
                handler.postDelayed(runnable, interval);


            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myAudioRecorder != null){
                    myAudioRecorder.reset();
                    myAudioRecorder.release();
                    myAudioRecorder  = null;
                }
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorder successfully", Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    play.setEnabled(false);
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // make something
                }
            }
        });


    }


}
