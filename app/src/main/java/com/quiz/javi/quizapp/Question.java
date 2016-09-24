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
    private boolean mReviewed;
    private int mAnswerSelected;
    private int answerSelected;

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

    public boolean isCorrect() {

        for(Answer answer : mAnswers)
        {
            if(answer.getSelected())
            {
                return answer instanceof CorrectAnswer;
            }
        }

        return false;
    }


    public List<Integer> getAnswers()
    {
        List<Integer> answers = new ArrayList<>();
        for(Answer answer : mAnswers)
        {
            answers.add(answer.getValue());
        }

        return answers;
    }

    public boolean getReviewed() {
        return mReviewed;
    }

    public void setReviewed(boolean reviewed) {
        mReviewed = reviewed;
    }

    public void setAnswerSelected(int answerSelected){

        for(Answer answer : mAnswers)
        {
            if(answer.getValue() == answerSelected)
            {
                answer.select();
            }
            else
            {
                answer.deselect();
            }
        }
    }

    public int getAnswerSelected() {

        for(Answer answer : mAnswers)
        {
            if(answer.getSelected())
            {
                return answer.getValue();
            }
        }

        return -1;
    }
}
