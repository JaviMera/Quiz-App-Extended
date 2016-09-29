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

    public void updateCorrectAnswersText(int correctNumber) {
        mView.updateCorrectAnswersTextView(correctNumber);
    }

    public void updateAttempsText(int attemptsNumber) {
        mView.updateAttempsTextView(attemptsNumber);
    }

    public void updateButtonAnswerText(int id, String answerText) {
        mView.updateButtonAnswerTextView(id, answerText);
    }
}
