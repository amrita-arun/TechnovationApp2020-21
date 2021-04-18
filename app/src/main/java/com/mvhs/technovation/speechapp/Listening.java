package com.mvhs.technovation.speechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
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
    private String [] solution = {"She did not do her homework", "Owns a book shop", "Play tag", "Bad",
    "Sportive", "Lunch", "Because of covid", "His foot", "Fish", "A friend"};
    private String[] possible1 = {"She did not eat breakfast", "Is a librarian", "Shop", "Easy",
    "Sportive", "A watch", "Because of covid", "His glasses", "Dog", "A friend"};
    private String[] possible2 = {"She hated the video game she bought", "Owns a book shop", "Play tag", "Fun",
    "Smart", "A purse", "Because she had no friends", "His arm", "Cat", "A brother"};
    private String[] possible3 = {"She did not do her homework", "Works at school", "Read", "Good",
    "Stylish", "Lunch", "Because she wanted alone", "His foot", "Fish", "A neighbor"};
    private String[] possible4 = {"Her mom hated her", "Works hard", "Bike", "Bad", "Pretty", "A shirt"
    , "Because she liked staying indoors", "His teeth", "Parrot", "A stranger"};
    private int index;
    private boolean change;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private final String MyPrefs = "QuestionsList";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
        sharedPref = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        index = -1;
        change = true;

        txt = (TextView)findViewById(R.id.textView3);
        answer1  = (Button)findViewById(R.id.button2);
        answer2  = (Button)findViewById(R.id.button3);
        answer3  = (Button)findViewById(R.id.button4);
        answer4  = (Button)findViewById(R.id.button5);
        if(change == true)
        {
            txt.setVisibility(View.INVISIBLE);
            newQuestion();
            change = false;
        }
        answer1.setText(possible1[index]);
        answer2.setText(possible2[index]);
        answer3.setText(possible3[index]);
        answer4.setText(possible4[index]);
        mySong = MediaPlayer.create(Listening.this, R.raw.practice+index);
    }

    public void newQuestion()
    {
        Map<String,?> keys = sharedPref.getAll();

        if(index == 9)
        {
            index = -1;
            keys.clear();
            getSharedPreferences(MyPrefs, Context.MODE_PRIVATE).edit().clear().apply();
        }
        index++;
        if (!keys.containsKey("Question Number" + index))
        {
            editor = sharedPref.edit();
            editor.putInt("Question Number" + index, index).apply();
        }
        else
        {
            newQuestion();
        }
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
            newQuestion();
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
            }, 1500);
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