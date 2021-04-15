package com.mvhs.technovation.speechapp;

public class SpeakingQuestion
{
    private String question;
    private String id;

    public SpeakingQuestion(String i, String q)
    {
        id = i;
        question = q;
    }
    public void setQuestion (String q)
    {
        question = q;
    }

    public String getQuestion ()
    {
        return question;
    }

    public void setId (String i)
    {
        id = i;
    }

    public String getId ()
    {
        return id;
    }
}
