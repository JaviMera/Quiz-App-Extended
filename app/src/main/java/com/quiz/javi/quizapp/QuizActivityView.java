package com.quiz.javi.quizapp;

import android.widget.Button;

/**
 * Created by Javi on 9/22/2016.
 */

public interface QuizActivityView {

    void updateQuestionTextView(String text);
    void updateRadioButtonTextView(int index, String text);
    void updateQuestionNumberTextView(String questionNumber);
}
