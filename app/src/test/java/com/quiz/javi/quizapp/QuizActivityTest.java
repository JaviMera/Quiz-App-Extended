package com.quiz.javi.quizapp;

import android.os.Build;
import android.widget.RadioButton;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import teamtreehouse.quizapp.QuestionBank;

/**
 * Created by Javi on 9/24/2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class QuizActivityTest {

    QuizActivity activity;

    @Before
    public void SetUp(){
        activity = Robolectric.setupActivity(QuizActivity.class);
    }

    @Test
    public void nextButtonClickShowsNextQuestion(){

        // Arrange
        Quiz quiz = new Quiz(new QuestionBank());
        Question question = quiz.getQuestion(1);

        // Act
        activity.mNextQuestionButton.performClick();

        // Arrange
        Assert.assertEquals(question.getText(), activity.mQuestionTextView.getText().toString());

        List<Integer> actualAnswers = new ArrayList<>();
        actualAnswers.add(Integer.parseInt(((RadioButton)activity.mAnswersRadioGroup.getChildAt(0)).getText().toString()));
        actualAnswers.add(Integer.parseInt(((RadioButton)activity.mAnswersRadioGroup.getChildAt(1)).getText().toString()));
        actualAnswers.add(Integer.parseInt(((RadioButton)activity.mAnswersRadioGroup.getChildAt(2)).getText().toString()));

        Assert.assertEquals(question.getAnswers(), actualAnswers);
    }

    @Test
    public void questionNotReviewedHasNoButtonsChecked() throws Exception {

        // Arrange
        RadioButton rButton = (RadioButton)activity.mAnswersRadioGroup.getChildAt(0);
        rButton.performClick();

        // Act
        activity.mNextQuestionButton.performClick();

        // Assert
        Assert.assertFalse(rButton.isChecked());
    }
}
