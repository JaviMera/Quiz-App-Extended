package com.quiz.javi.quizapp;

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

    public void updateRadioButtonText(int index, String text){
        mView.updateRadioButtonTextView(index, text);
    }

    public void updateQuestionNumberText(String questionNumber) {
        mView.updateQuestionNumberTextView(questionNumber);
    }
}
