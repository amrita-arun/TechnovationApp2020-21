package com.mvhs.technovation.speechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class SpeakingActivity extends AppCompatActivity {

    private ArrayList<String> questions = new ArrayList<String>();
//    private int numQuestions;
    private static final int REQUEST_CODE = 100;
    private TextView textOutput;
    private String spokenSentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textOutput= (TextView) findViewById(R.id.textOutput);
        questions.add("Today I went to the park and hung out with my friends.");
        questions.add("Today was my birthday, I ate cake and pizza.");
        questions.add("Reading is one of my favorite hobbies.");
        questions.add("My favorite color is blue.");
    }

//This method is called with the button is pressed//

    public void onClick(View v)

//Create an Intent with “RecognizerIntent.ACTION_RECOGNIZE_SPEECH” action//

    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        try
        {
            //Start the Activity and wait for the response
            startActivityForResult(intent, REQUEST_CODE);
        }
        catch (ActivityNotFoundException a) {

        }
    }

    @Override

    //Handle the results//
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textOutput.setText(result.get(0));
                    spokenSentence = result.get(0);
                }
                break;
            }

        }
    }

}
