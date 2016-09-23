package com.quiz.javi.quizapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javi on 9/21/2016.
 */

public class Question {

    private int mLeftAdder;
    private int mRightAdder;
    private List<Answer> mAnswers;

    public Question(int leftAdder,
                    int rightAdder,
                    int correctAnswer,
                    int incorrectAnswer1,
                    int incorrectAnswer2)
    {
        mLeftAdder = leftAdder;
        mRightAdder = rightAdder;
        mAnswers = new ArrayList();
        mAnswers.add(new CorrectAnswer(correctAnswer));
        mAnswers.add(new IncorrectAnswer(incorrectAnswer1));
        mAnswers.add(new IncorrectAnswer(incorrectAnswer2));
    }

    public String getText(){
        return "What is " + mLeftAdder + " + " + mRightAdder + "?";
    }

    public boolean isCorrect(int choice) {

        for(Answer answer : mAnswers)
        {
            if(answer instanceof CorrectAnswer)
            {
                return answer.getAnswer() == choice;
            }
        }

        return false;
    }


    public int getAnswer(int index)
    {
        return mAnswers
                .get(index)
                .getAnswer();
    }
}
