package com.quiz.javi.quizapp;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Javi on 9/26/2016.
 */

public class SoundPlayer {

    private MediaPlayer mCorrectAnswerSound;
    private MediaPlayer mIncorrectAnswerSound;

    public SoundPlayer(MediaPlayer correctPlayer, MediaPlayer incorrectPlayer){

        mCorrectAnswerSound = correctPlayer;
        mIncorrectAnswerSound = incorrectPlayer;
    }

    public void play(boolean isCorrect){

        // Have this check so the Activity Test with Robolectric does not fail
        // For a reason I don't know, the Test will not have the instances created for a Media Player object
        if(mCorrectAnswerSound == null || mIncorrectAnswerSound == null)
            return;

        if(isCorrect)
        {
            mCorrectAnswerSound.start();
        }
        else
        {
            mIncorrectAnswerSound.start();
        }
    }

    public void dispose(){

        mCorrectAnswerSound.release();
        mCorrectAnswerSound = null;
        mIncorrectAnswerSound.release();
        mIncorrectAnswerSound = null;
    }
}
