package com.quiz.javi.quizapp;

import android.widget.Button;

/**
 * Created by Javi on 9/22/2016.
 */

public class QuizActivityPresenter {

    private QuizActivityView mView;

    public QuizActivityPresenter(QuizActivityView view) {
        mView = view;
    }

    public void updateQuestionText(String question) {
        mView.updateQuestionTextView(question);
    }

    public void updateCorrectAnswersText(int correctNumber) {
        mView.updateCorrectAnswersTextView(correctNumber);
    }

    public void updateAttempsText(int attemptsNumber) {
        mView.updateAttempsTextView(attemptsNumber);
    }

    public void updateButtonText(Button button, String answerText) {
        mView.updateButtonAnswerTextView(button, answerText);
    }

    public void updateButtonTextColor(Button button, int color) {
        mView.updateButtonTextColor(button, color);
    }

    public void updateButtonBackgroundDrawable(Button button, int drawable) {
        mView.updateButtonBackgroundDrawable(button, drawable);
    }

    public void updateButtonState(Button button, boolean enabled) {
        mView.updateButtonEnabledState(button, enabled);
    }

    public void updateButtonSelectState(Button button, boolean selected){
        mView.updateButtonSelectState(button, selected);
    }
}
