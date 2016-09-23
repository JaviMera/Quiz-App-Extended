package com.quiz.javi.quizapp;

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
}
