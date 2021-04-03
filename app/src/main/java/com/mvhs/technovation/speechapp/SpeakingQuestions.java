package com.mvhs.technovation.speechapp;

public class SpeakingQuestions
{
    private String question;
    private boolean done;

    public void setQuestion (String q)
    {
        question = q;
    }

    public String getQuestion ()
    {
        return question;
    }

    public void setDone (boolean d)
    {
        done = d;
    }

    public boolean getDone ()
    {
        return done;
    }
}
