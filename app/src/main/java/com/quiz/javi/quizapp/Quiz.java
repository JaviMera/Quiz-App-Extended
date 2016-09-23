package com.quiz.javi.quizapp;

import java.util.ArrayList;
import java.util.List;

import teamtreehouse.quizapp.QuestionBank;

/**
 * Created by Javi on 9/22/2016.
 */

public class Quiz {

    private List<Question> mQuestions;

    public Quiz(QuestionBank qBank){
        mQuestions = new ArrayList<>();

        // Get the length of only one of the arrays, as all of them have the same number of elements
        int size = qBank.leftAdders.length;

        for(int index = 0 ; index < size ; index++)
        {
            mQuestions.add(new Question(
               qBank.leftAdders[index],
               qBank.rightAdders[index],
               qBank.correctAnswers[index],
               qBank.firstIncorrectAnswers[index],
               qBank.secondIncorrectAnswers[index]
            ));
        }
    }

    public Question getQuestion(int questionNumber) {
        return mQuestions.get(questionNumber);
    }

    public int totalQuestions() {
        return mQuestions.size();
    }
}
