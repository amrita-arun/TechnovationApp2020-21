package com.mvhs.technovation.speechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SpeakingActivity extends AppCompatActivity {

    private ArrayList<String> questions = new ArrayList<String>();
//    private int numQuestions;
    private static final int REQUEST_CODE = 100;
    private TextView textOutput;
    private String spokenSentence;
    private TextView sentence1;
    private TextView goodJob;
    int question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking2);
        textOutput= (TextView) findViewById(R.id.textOutput);
        questions.add("Today I went to the park and hung out with my friends.");
        questions.add("Today was my birthday, I ate cake and pizza.");
        questions.add("Reading is one of my favorite hobbies.");
        questions.add("My favorite color is blue.");

        newQuestion();

        /*
        int question = (int)(Math.random() * questions.size());
        sentence1 = (TextView)findViewById(R.id.sentence);
        sentence1.setText(questions.get(question));

        goodJob = (TextView) findViewById(R.id.goodjob);
        goodJob.setVisibility(View.INVISIBLE);
        
         */

    }

    public void newQuestion ()
    {
        int question = (int)(Math.random() * questions.size());
        sentence1 = (TextView)findViewById(R.id.sentence);
        sentence1.setText(questions.get(question));

        goodJob = (TextView) findViewById(R.id.goodjob);
        goodJob.setVisibility(View.INVISIBLE);
    }

//This method is called with the button is pressed//

    public void onClick(View v)

//Create an Intent with “RecognizerIntent.ACTION_RECOGNIZE_SPEECH” action//

    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        goodJob.setVisibility(View.INVISIBLE);
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
                 //   textOutput.setText(result.get(0));
                    spokenSentence = result.get(0);
                }
                break;
            }

        }
        if (spokenSentence.equals("my favorite color is blue"))
        {
            goodJob.setVisibility(View.VISIBLE);
        }
        else
        {
            goodJob.setText("Try again!");
            goodJob.setVisibility(View.VISIBLE);
        }
    }

}
