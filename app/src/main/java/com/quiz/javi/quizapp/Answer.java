package com.quiz.javi.quizapp;

/**
 * Created by Javi on 9/21/2016.
 */

public abstract class Answer {

    protected int mAnswer;

    protected Answer(int answer){
        mAnswer = answer;
    }

    public int getAnswer(){
        return mAnswer;
    }
}
