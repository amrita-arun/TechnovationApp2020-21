package com.mvhs.technovation.speechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Listening extends AppCompatActivity{
    MediaPlayer mySong;
    Timer timer = new Timer();
    private TextView txt;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    private String [] solution = {"Blue", "Dog"};
    private String[] possible1 = {"RED", "CAT"};
    private String[] possible2 = {"ORANGE", "DOG"};
    private String[] possible3 = {"BLUE", "FISH"};
    private String[] possible4 = {"PINK", "ZEBRA"};
    private int index = (int)(Math.random()*2);
    private boolean change;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
        txt = (TextView)findViewById(R.id.textView3);
        answer1  = (Button)findViewById(R.id.button2);
        answer2  = (Button)findViewById(R.id.button3);
        answer3  = (Button)findViewById(R.id.button4);
        answer4  = (Button)findViewById(R.id.button5);
        if(change == true)
        {
            txt.setVisibility(View.INVISIBLE);
            index = (int)(Math.random()*2);
            change = false;
        }
        answer1.setText(possible1[index]);
        answer2.setText(possible2[index]);
        answer3.setText(possible3[index]);
        answer4.setText(possible4[index]);
        mySong = MediaPlayer.create(Listening.this, R.raw.practice+index);
    }

    public void calledAgain()
    {
        txt = (TextView)findViewById(R.id.textView3);
        answer1  = (Button)findViewById(R.id.button2);
        answer2  = (Button)findViewById(R.id.button3);
        answer3  = (Button)findViewById(R.id.button4);
        answer4  = (Button)findViewById(R.id.button5);
        if(change == true)
        {
            index = (int)(Math.random()*2);
            change = false;
        }
        answer1.setText(possible1[index]);
        answer2.setText(possible2[index]);
        answer3.setText(possible3[index]);
        answer4.setText(possible4[index]);
        mySong = MediaPlayer.create(Listening.this, R.raw.practice+index);
        txt.setVisibility(View.INVISIBLE);
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
        if(text.equalsIgnoreCase(solution[index]))
        {
            txt.setVisibility(View.VISIBLE);
            //set a value in the textview
            txt.setText("Correct!");
            change = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run()
                {
                    calledAgain();
                }
            }, 5000);
        }
        else
        {
            txt.setVisibility(View.VISIBLE);
            //set a value in the textview
           // txt.setText(text);
            txt.setText("Sorry but you are wrong. Try Again!");
        }
    }
}