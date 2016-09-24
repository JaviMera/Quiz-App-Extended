package com.quiz.javi.quizapp;

/**
 * Created by Javi on 9/21/2016.
 */

public abstract class Answer {

    protected int mAnswer;
    protected boolean mSelected;

    protected Answer(int value){
        mAnswer = value;
        mSelected = false;
    }

    public int getValue(){
        return mAnswer;
    }

    public void select() {
        mSelected = true;
    }

    public void deselect(){
        mSelected = false;
    }

    public boolean getSelected() {
        return mSelected;
    }
}
