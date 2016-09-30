package com.quiz.javi.quizapp;

import android.widget.Button;

/**
 * Created by Javi on 9/22/2016.
 */

public interface QuizActivityView {

    void updateQuestionTextView(String text);
    void updateCorrectAnswersTextView(int correctNumber);
    void updateAttempsTextView(int attemptsNumber);
    void updateButtonAnswerTextView(Button button, String answerText);
    void updateButtonTextColor(Button button, int color);
    void updateButtonBackgroundDrawable(Button button, int drawable);
    void updateButtonEnabledState(Button button, boolean enabled);
    void updateButtonSelectState(Button button, boolean selected);
}
