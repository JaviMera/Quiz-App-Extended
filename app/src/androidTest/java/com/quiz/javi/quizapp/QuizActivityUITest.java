package com.quiz.javi.quizapp;

import android.app.Application;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import junit.framework.Assert;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import teamtreehouse.quizapp.QuestionBank;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class QuizActivityUITest {

    @Rule
    public ActivityTestRule<QuizActivity> rule = new ActivityTestRule<QuizActivity>(QuizActivity.class);

    @Before
    public void setUp ()throws Exception {
    }

//    @Test
//    public void radioButtonAnswerClickGoesToNextQuestion() throws Exception{
//
//        // Arrange
//        Quiz quiz = new Quiz(new QuestionBank());
//        final Question question = quiz.getQuestion(1);
//
//        // Act
//        onView(withId(R.id.buttonAnswer1)).perform(click());
//
//        // Assert
//        onView(withId(R.id.questionTextView)).check(matches(withText(question.getText())));
//    }
//
//    private class buttonTextMatcher extends BaseMatcher{
//
//        int mValue;
//
//        buttonTextMatcher(int value)
//        {
//            mValue = value;
//        }
//
//        @Override
//        public boolean matches(Object item) {
//            Button button = (Button)item;
//            if(button.getText().toString().equals(Integer.toString(mValue)))
//                return true;
//
//            return false;
//        }
//
//        @Override
//        public void describeTo(Description description) {
//            description.appendText("Button should have " + mValue + " as text.");
//        }
//    }
}