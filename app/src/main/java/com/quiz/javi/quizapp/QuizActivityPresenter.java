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

    public void updateQuestionTextView(String question) {
        mView.setQuestionTextView(question);
    }

    public void updateButtonAnswerText(Button button, String answerText) {
        mView.setButtonAnswerText(button, answerText);
    }
}
