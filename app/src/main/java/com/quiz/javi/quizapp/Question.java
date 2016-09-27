package com.quiz.javi.quizapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javi on 9/21/2016.
 */

public class Question {

    private int mNumber;
    private int mLeftAdder;
    private int mRightAdder;
    private List<Answer> mAnswers;

    public static final String CORRECT_MESSAGE = "Correct Answer!!";
    public static final String INCORRECT_MESSAGE = "Incorrect answer :(";
    public static final String NO_SELECTION_MESSAGAE = "Please select an answer before submitting :)";

    public Question(
            int questionNumber,
            int leftAdder,
            int rightAdder,
            int correctAnswer,
            int incorrectAnswer1,
            int incorrectAnswer2)
    {
        mNumber = questionNumber;
        mLeftAdder = leftAdder;
        mRightAdder = rightAdder;
        mAnswers = new ArrayList();
        mAnswers.add(new CorrectAnswer(correctAnswer));
        mAnswers.add(new IncorrectAnswer(incorrectAnswer1));
        mAnswers.add(new IncorrectAnswer(incorrectAnswer2));
    }

    public String getText(){

        if(mRightAdder < 0) {
            return "What is " + mLeftAdder + " + " + "(" + mRightAdder + ")" + "?";
        }

        return "What is " +mLeftAdder + " + " + mRightAdder + "?";
    }

    public boolean isCorrect(int answerSelected) {

        for(Answer answer : mAnswers)
        {
            if(answerSelected == answer.getValue())
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

    public int getNumber() {
        return mNumber;
    }

    public String getAnswerMessage(boolean result) {

        if(result)
        {
            return CORRECT_MESSAGE;
        }

        return INCORRECT_MESSAGE;
    }

    public String getErrorMessage() {
        return NO_SELECTION_MESSAGAE;
    }
}
