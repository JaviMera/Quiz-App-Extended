package com.quiz.javi.quizapp;

import android.os.Build;
import android.provider.MediaStore;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import teamtreehouse.quizapp.QuestionBank;

/**
 * Created by Javi on 9/24/2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class QuizActivityTest {

    private QuizActivity activity;

    @Before
    public void SetUp(){
        activity = Robolectric.setupActivity(QuizActivity.class);
    }

    @Test
    public void submitButtonClickDisplaysNextQuestion(){

        // Arrange
        Quiz quiz = new Quiz(new QuestionBank());
        quiz.getQuestion(); // display first question

        // Act
        activity.mSubmitButton.performClick();
        Question question = quiz.getQuestion();

        // Assert
        Assert.assertEquals(activity.mQuestionTextView.getText().toString(), question.getText());
        Assert.assertEquals(question.getAnswers(), getActualAnwsers(activity.mAnswersRadioGroup));
    }


    private List<Integer> getActualAnwsers(RadioGroup group){
        List<Integer> actualAnswers = new ArrayList<>();
        for(int i = 0 ; i < group.getChildCount(); i ++)
        {
            RadioButton rButton = (RadioButton)group.getChildAt(i);
            actualAnswers.add(Integer.parseInt(rButton.getText().toString()));
        }

        return actualAnswers;
    }
}
