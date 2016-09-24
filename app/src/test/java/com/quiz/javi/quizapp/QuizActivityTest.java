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
    public void answerSelectGoesToNextQuestion(){

        // Arrange
        int randomChild = new Random().nextInt(activity.mAnswersRadioGroup.getChildCount());
        Quiz quiz = new Quiz(new QuestionBank());
        Question question = quiz.getQuestion(1);

        // Act
        activity
            .mAnswersRadioGroup
            .getChildAt(randomChild)
            .performClick();

        // Arrange
        Assert.assertEquals(question.getText(), activity.mQuestionTextView.getText().toString());
    }

    @Test
    public void questionNotReviewedHasNoButtonsChecked() throws Exception {

        // Arrange
        RadioButton rButton = (RadioButton)activity.mAnswersRadioGroup.getChildAt(0);
        
        // Act
        rButton.performClick();

        // Assert
        Assert.assertFalse(rButton.isChecked());
    }
}
