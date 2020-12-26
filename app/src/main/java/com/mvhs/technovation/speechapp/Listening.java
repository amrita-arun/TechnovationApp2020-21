package com.mvhs.technovation.speechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Listening extends AppCompatActivity{
    MediaPlayer mySong;
    private TextView txt;
    private String answer = "BLUE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
        txt = (TextView)findViewById(R.id.textView2);
    }
    /*
    public Listening() {
       txt = findViewById(R.id.textView3);
        System.out.println("In here");
    }

     */

    protected void onCreate() {
        //setContentView(R.layout.activity_listening);
        mySong = MediaPlayer.create(Listening.this, R.raw.practice);
    }

    public void playIT(View y)
    {
        mySong.start();
    }

    protected void onPause() {
        super.onPause();
        mySong.release();
    }

    public void onClick(View view){
        Button b = (Button)view;
        String text = b.getText().toString();
        if(text.equals(answer))
        {
            txt.setVisibility(View.VISIBLE);
            //set a value in the textview
            txt.setText("Correct!");
            System.out.println("hiya");
        }
        else
        {
            txt.setVisibility(View.VISIBLE);
            //set a value in the textview
            txt.setText("Sorry but you are wrong. Try Again!");
        }
    }
}