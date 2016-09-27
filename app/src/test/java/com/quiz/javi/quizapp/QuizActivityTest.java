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
import java.util.Collection;
import java.util.Collections;
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
    public void activityInit(){

        // Arrange
        String answerChoicesRegex = "-?\\d+";
        String questionTextRegex = "[\\w ]+[\\d]+ \\+ \\(?-?[\\d]+\\)??\\?";
        String questionText = activity.mQuestionTextView.getText().toString();
        String rButton1Text = ((RadioButton)activity.mAnswersRadioGroup.getChildAt(0)).getText().toString();
        String rButton2Text = ((RadioButton)activity.mAnswersRadioGroup.getChildAt(1)).getText().toString();
        String rButton3Text = ((RadioButton)activity.mAnswersRadioGroup.getChildAt(2)).getText().toString();

        // Act
        boolean matchesQuestion = questionText.matches(questionTextRegex);
        boolean matchesAnswer2 = rButton2Text.matches(answerChoicesRegex);
        boolean matchesAnswer1 = rButton1Text.matches(answerChoicesRegex);
        boolean matchesAnswer3 = rButton3Text.matches(answerChoicesRegex);

        // Assert question displays correct text format
        Assert.assertTrue(matchesQuestion);

        // Assert score text views
        Assert.assertTrue(activity.mCorrectAnswersTextView.getText().toString().contains("0"));
        Assert.assertTrue(activity.mAttemptsTextView.getText().toString().contains("0"));

        // Assert all radio buttons contain any positive or negative number
        Assert.assertTrue(matchesAnswer1);
        Assert.assertTrue(matchesAnswer2);
        Assert.assertTrue(matchesAnswer3);
    }

    @Test
    public void questionRemainsIfNoAnswerIsSelectedAfterSubmit(){

        // Act
        activity.mSubmitButton.performClick();

        // Assert that activity still shows question #1
        Assert.assertTrue(activity.mCorrectAnswersTextView.getText().toString().contains("0"));
        Assert.assertTrue(activity.mAttemptsTextView.getText().toString().contains("0"));
    }

    @Test
    public void anyAnswerUpdatesScoreSystemAppropriately() throws Exception {

        // Arrange
        String regex = "[01]"; // regex will check either if a 0 or 1 is present in the score textviews

        // Act
        activity.mSubmitButton.performClick();
        ((RadioButton)activity.mAnswersRadioGroup.getChildAt(0)).setChecked(true); // select the first radio button
        // Assert

        Assert.assertTrue(activity.mCorrectAnswersTextView.getText().toString().matches(regex));
        Assert.assertTrue(activity.mAttemptsTextView.getText().toString().matches(regex));
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
