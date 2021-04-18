package com.mvhs.technovation.speechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button listening;
    private Button realWorld;
    private Button speaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_listening);

        listening = (Button)findViewById(R.id.listening);
        realWorld = (Button)findViewById(R.id.realWorld);
        speaking = (Button)findViewById(R.id.speaking);

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
