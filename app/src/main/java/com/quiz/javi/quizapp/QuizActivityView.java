package com.quiz.javi.quizapp;

import android.widget.Button;

/**
 * Created by Javi on 9/22/2016.
 */

public interface QuizActivityView {

    void setQuestionTextView(String text);
    void setRadioButtonText(int index, String text);
}
