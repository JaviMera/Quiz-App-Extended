package com.quiz.javi.quizapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Locale;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Javi on 9/27/2016.
 */

@RunWith(AndroidJUnit4.class)
public class QuizActivityUITest {

    @Rule
    public ActivityTestRule<QuizActivity> activityTestRule =
            new ActivityTestRule<>(QuizActivity.class);

    @Test
    public void activityInit() throws Exception {

        // Arrange
        String initialQuestionRegex = "[\\w ]+-?[\\d]+ \\+ \\(?-?[\\d]+\\)??\\?";
        String initialScoreRegex = "[\\w]+\\S[ ]+0";
        String buttonAnswerRegex = "-?\\d+";

        // Assert
        onView(withId(R.id.questionTextView)).check(matches(new RegexMatcher(initialQuestionRegex)));
        onView(withId(R.id.correctAnswersTextView)).check(matches(new RegexMatcher(initialScoreRegex)));
        onView(withId(R.id.attempsTextView)).check(matches(new RegexMatcher(initialScoreRegex)));
        onView(withId(R.id.buttonAnswer1)).check(matches(new RegexMatcher(buttonAnswerRegex)));
        onView(withId(R.id.buttonAnswer2)).check(matches(new RegexMatcher(buttonAnswerRegex)));
        onView(withId(R.id.buttonAnswer3)).check(matches(new RegexMatcher(buttonAnswerRegex)));
    }

    @Test
    public void questionRemainsIfNoAnswerIsSelectedAfterSubmit() throws Exception{

        // Arrange
        String scoreValue = "0";

        // Act
        onView(withId(R.id.submitButton)).perform(click());

        // Assert
        onView(withId(R.id.correctAnswersTextView)).check(matches(new ContainsMatcher(scoreValue)));
        onView(withId(R.id.attempsTextView)).check(matches(new ContainsMatcher(scoreValue)));

        // Assert all button answers are enabled after clickign next
        onView(withId(R.id.buttonAnswer1)).check(matches(isEnabled()));
        onView(withId(R.id.buttonAnswer2)).check(matches(isEnabled()));
        onView(withId(R.id.buttonAnswer3)).check(matches(isEnabled()));
    }

    @Test
    public void submitButtonPressUpdatesScoreAndResultMessage() throws Exception {

        // Arrange
        String expectedAttemptsScore = "1";
        String correctScoreRegex = "[\\w]+\\S[ ]+[01]";
        String correctButtonText = "Correct";
        String incorrectButtonText = "LOL NOOB INCORRECT";
        int buttonGreen = Color.parseColor("#00A550");
        int buttonOrange = Color.parseColor("#F7931D");

        // Act
        onView(withId(R.id.buttonAnswer1)).perform(click()); // select first button to see if we get it right or wrong
        onView(withId(R.id.submitButton)).perform(click());

        // Assert the score has been updated correctly depending on the result
        onView(withId(R.id.attempsTextView)).check(matches(new ContainsMatcher(expectedAttemptsScore)));
        onView(withId(R.id.correctAnswersTextView)).check(matches(new RegexMatcher(correctScoreRegex)));

        // Assert all button answers are disabled after submitting an answer
        onView(withId(R.id.buttonAnswer1)).check(matches(not(isEnabled())));
        onView(withId(R.id.buttonAnswer2)).check(matches(not(isEnabled())));
        onView(withId(R.id.buttonAnswer3)).check(matches(not(isEnabled())));

        // Assert Submit button is disabled after submitting an answer and also changes color accordingly
        onView(withId(R.id.submitButton)).check(matches(not(isEnabled())));
        onView(withId(R.id.submitButton)).check(matches(new BackgroundMatcher(buttonGreen, buttonOrange)));
        onView(withId(R.id.submitButton)).check(matches(new ContainsMatcher(correctButtonText, incorrectButtonText)));

        // Assert Next button is enabled after submitting an answer
        onView(withId(R.id.nextQuestionButton)).check(matches(isEnabled()));
    }

    @Test
    public void nextButtonPressDisplaysNextQuestion() throws Exception {

        // Arrange
        String questionText = activityTestRule.getActivity().mQuestionTextView.getText().toString();
        int buttonEnabledColor = Color.parseColor("#182B78");

        // Act
        onView(withId(R.id.buttonAnswer1)).perform(click()); // select first button to see if we get it right or wrong
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.nextQuestionButton)).perform(click());

        // Assert
        onView(withId(R.id.questionTextView)).check(matches(not(withText(questionText))));

        // Assert all button answers are enabled after clickign next
        onView(withId(R.id.buttonAnswer1)).check(matches(isEnabled()));
        onView(withId(R.id.buttonAnswer2)).check(matches(isEnabled()));
        onView(withId(R.id.buttonAnswer3)).check(matches(isEnabled()));

        onView(withId(R.id.submitButton)).check(matches(withText("Submit")));
        onView(withId(R.id.submitButton)).check(matches(isEnabled()));
        onView(withId(R.id.submitButton)).check(matches(new BackgroundMatcher(buttonEnabledColor)));

        onView(withId(R.id.nextQuestionButton)).check(matches(not(isEnabled())));
    }

    private class RegexMatcher extends BaseMatcher<View> {

        private String mRegex;

        public RegexMatcher(String regex){

            mRegex = regex;
        }

        @Override
        public boolean matches(Object item) {
            TextView questionTextView = (TextView)item;
            String text = questionTextView.getText().toString();

            return text.matches(mRegex);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Question is in wrong format.");
        }
    };

    private class ContainsMatcher extends BaseMatcher<View>{

        private String[] mValue;

        public ContainsMatcher(String... values){

            mValue = values;
        }

        @Override
        public boolean matches(Object item) {
            TextView textView = (TextView)item;
            String text = textView.getText().toString();

            for(int i = 0 ; i < mValue.length ; i++)
            {
                if(text.contains(mValue[i]))
                    return true;
            }

            return false;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Does not contain " + mValue);
        }
    }

    private class BackgroundMatcher extends BaseMatcher<View> {

        private int[] mGivenColors;

        public BackgroundMatcher(int... colors)
        {
            mGivenColors = colors;
        }

        @Override
        public boolean matches(Object item) {
            AppCompatButton button = (AppCompatButton)item;
            Drawable buttonBackground = button.getBackground();
            ColorDrawable backgroundAsColorDrawable = new ColorDrawable();

            if(buttonBackground instanceof StateListDrawable)
            {
                StateListDrawable backgroundAsStateListDrawable = (StateListDrawable)buttonBackground;
                backgroundAsColorDrawable = (ColorDrawable)backgroundAsStateListDrawable.getCurrent();
            }
            else if(buttonBackground instanceof ColorDrawable)
            {
                backgroundAsColorDrawable = (ColorDrawable)button.getBackground();
            }

            for(Integer color : mGivenColors)
            {
                if(backgroundAsColorDrawable.getColor() == color)
                    return true;
            }

            return false;
        }

        @Override
        public void describeTo(Description description) {

            String desc = "";
            for(Integer color : mGivenColors)
            {
                desc += color + " ";
            }

            description.appendText("Color of button must be " + desc);
        }
    }
}
