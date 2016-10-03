package com.quiz.javi.quizapp;

import java.util.Random;

/**
 * Created by Javi on 9/22/2016.
 */

public class Quiz {
    private Question mCurrentQuestion;
    private int mMin;
    private int mMax;
    private int mQuestionNumber;

    public Quiz(int leftAdderRange, int rightAdderRange){
        mMin = leftAdderRange;
        mMax = rightAdderRange;
    }

    public Question generateQuestion(){

        mQuestionNumber++;
        int leftAdder = getAdderRandom(mMin, mMax);
        int rightAdder = getAdderRandom(mMin, mMax);
        int correctAnswer = leftAdder + rightAdder;
        int firstIncorrectAnswer = getIncorrectRandom(correctAnswer - 10, correctAnswer + 10, correctAnswer);
        int secondIncorrectAnswer = getIncorrectRandom(correctAnswer - 10, correctAnswer + 10, correctAnswer, firstIncorrectAnswer);

        mCurrentQuestion = new Question(
                mQuestionNumber,
                leftAdder,
                rightAdder,
                correctAnswer,
                firstIncorrectAnswer,
                secondIncorrectAnswer
        );

        return mCurrentQuestion;
    }

    private int getAdderRandom(int min, int max){
        int random = 0;

        do{
            random = new Random().nextInt(max + 1 - min) + min;
        }while(random == 0);

        return random;
    }

    private int getIncorrectRandom(int min, int max, int... conditions)
    {
        int random = 0;
        boolean canExit;

        do {
            canExit = true;
            random = new Random().nextInt(max + 1 - min) + min;

            for (Integer condition : conditions)
            {
                if(condition == random)
                {
                    canExit = false;
                    break;
                }
            }

        }while(!canExit);

        return random;
    }
}