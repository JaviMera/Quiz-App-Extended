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
    public void activityCreationDisplaysFirstQuestionInformation(){

        // Arrange
        Quiz quiz = new Quiz(new QuestionBank());
        quiz.getQuestion(); // display first question

        // Act
        activity.mSubmitButton.performClick();
        Question question = quiz.getQuestion();

        // Assert Layout displays the correct question
        String expectedQuestion = activity.mQuestionTextView.getText().toString();
        String actualQuestion = question.getText();

        Assert.assertEquals(
                expectedQuestion,
                actualQuestion);

        // Assert Layout displays the correct question number
        String expectedQuestionNumber = activity.mQuestionNumberTextView.getText().toString();
        String actualQuestionNumber = Integer.toString(question.getNumber());

        Assert.assertTrue(expectedQuestionNumber.contains(actualQuestionNumber));

        // Assert Layout displays the correct question answer options
        List<Integer> expectedAnswers = question.getAnswers();
        List<Integer> actualAnswers = getActualAnwsers(activity.mAnswersRadioGroup);
        Collections.sort(expectedAnswers);
        Collections.sort(actualAnswers);

        Assert.assertEquals(
                expectedAnswers,
                actualAnswers);
    }

    @Test
    public void trackingSystemDisplaysZeroAtInit() throws Exception {

        // Assert
        Assert.assertTrue(activity.mCorrectAnswersTextView.getText().toString().contains("0"));
        Assert.assertTrue(activity.mAttemptsTextView.getText().toString().contains("0"));
    }

    @Test
    public void incorrectAnswerUpdatesTrackingSystem() throws Exception {

        // Arrange
        Quiz quiz = new Quiz(new QuestionBank());
        Question question = quiz.getQuestion(); // display first question
        List<Integer> answerChoices = question.getAnswers();

        // Act
        question.setAnswerSelected(answerChoices.get(1)); // select the incorrect answer from question #1
        activity.mSubmitButton.performClick();

        // Assert
        Assert.assertTrue(activity.mCorrectAnswersTextView.getText().toString().contains("0"));
        Assert.assertTrue(activity.mAttemptsTextView.getText().toString().contains("1"));
    }

    @Test
    public void correctAnswerUpdatesTrackingSystem() throws Exception {

        // Arrange
        Quiz quiz = new Quiz(new QuestionBank());
        Question question = quiz.getQuestion(); // display first question
        List<Integer> answerChoices = question.getAnswers();
        int answer = answerChoices.get(0);

        // Act

        // quiz object will always have the correct answer at position 0 for each question
        question.setAnswerSelected(answerChoices.get(0));

        // Because Quiz Activity displays the answers in a shuffled manner
        // Loop through each answer until you find the one that has the correct answer,
        for(int i = 0 ; i < activity.mAnswersRadioGroup.getChildCount() ; i++)
        {
            RadioButton rb = (RadioButton)activity.mAnswersRadioGroup.getChildAt(i);
            if(Integer.parseInt(rb.getText().toString()) == question.getAnswerSelected())
            {
                rb.setChecked(true);
                activity.mSubmitButton.performClick();
                break;
            }

        }

        // Assert
        Assert.assertTrue(activity.mCorrectAnswersTextView.getText().toString().contains("1"));
        Assert.assertTrue(activity.mAttemptsTextView.getText().toString().contains("1"));
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
