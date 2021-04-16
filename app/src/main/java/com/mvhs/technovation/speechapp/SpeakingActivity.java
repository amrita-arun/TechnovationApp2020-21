package com.mvhs.technovation.speechapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class SpeakingActivity extends AppCompatActivity {

    private ArrayList<SpeakingQuestion> questions = new ArrayList<>();
    private static final int REQUEST_CODE = 100;
    private final String TAG = "speakingActivity";
    private String spokenSentence;
    private TextView sentence1;
    private TextView goodJob;
    private Button nextQuestion;
    private Button startSpeaking;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    int questionIndex;
    int counter;
    SpeakingQuestion currentQuestion;
    private final String MyPREFERENCES = "QuestionsAnsweredPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking2);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("speakingQuestions").document("questions");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> map = document.getData();
                        if (map != null) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                System.out.println(entry.getValue().toString());
                                questions.add(new SpeakingQuestion(entry.getKey(), entry.getValue().toString()));
                            }
                        }
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        newQuestion();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        sentence1 = (TextView)findViewById(R.id.sentence);
        goodJob = (TextView) findViewById(R.id.goodjob);
        nextQuestion = (Button)findViewById(R.id.nextQuestion);
        startSpeaking = (Button) findViewById(R.id.startDictation);
    }

    public void newQuestion ()
    {
        if (questions == null || questions.size() == 0) {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show();
        }
        if (questionIndex >= questions.size()) {
            Toast.makeText(this, "You have answered all questions!", Toast.LENGTH_LONG).show();
            return;
        }
        SpeakingQuestion ques = questions.get(questionIndex);
        boolean validQ = true;
        Map<String,?> keys = sharedpreferences.getAll();

            if (!keys.containsKey(ques.getId()))
            {
                currentQuestion = ques;
                sentence1.setText(currentQuestion.getQuestion());
                goodJob.setVisibility(View.INVISIBLE);
                nextQuestion.setVisibility(View.INVISIBLE);
                startSpeaking.setVisibility(View.VISIBLE);
                counter = 0;
            }
            else {
                ++questionIndex;
                newQuestion();
            }
    }

    public void onClick(View v)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        goodJob.setVisibility(View.INVISIBLE);
        try
        {
            startActivityForResult(intent, REQUEST_CODE);
        }
        catch (ActivityNotFoundException a) {

        }
    }

    public void clickNextQuestion (View view)
    {
        questionIndex++;
        newQuestion();
    }

    private boolean sCompare(String s1, String s2)
    {
        System.out.println("in sCompare");
        System.out.println(s1 + " " + s2);
        s2 = s2.replaceAll("\\p{Punct}", "");
        s1 = s1.replaceAll("\\p{Punct}", "");
        System.out.println("modified s2 = " + s2);
        return (s1.equalsIgnoreCase(s2.toLowerCase().replaceAll(",","")));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    spokenSentence = result.get(0);
                }
                break;
            }

        }
        if (sCompare(spokenSentence, currentQuestion.getQuestion()))
        {
            System.out.println(questions.get(questionIndex));
            goodJob.setText("Good job!");
            goodJob.setVisibility(View.VISIBLE);
            startSpeaking.setVisibility(View.INVISIBLE);
            nextQuestion.setVisibility(View.VISIBLE);
            editor.putString(currentQuestion.getId(), currentQuestion.getId()).apply();
        }
        else
        {
            System.out.println(questions.get(questionIndex));
            goodJob.setText("Try again!");
            goodJob.setVisibility(View.VISIBLE);
            counter++;
            if (counter == 3)
            {
                nextQuestion.setVisibility(View.VISIBLE);
            }
        }
    }
}
