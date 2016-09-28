package com.quiz.javi.quizapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
        String initialRadioButtonRegex = "-?\\d+";

        // Assert
        onView(withId(R.id.questionTextView)).check(matches(new RegexMatcher(initialQuestionRegex)));
        onView(withId(R.id.correctAnswersTextView)).check(matches(new RegexMatcher(initialScoreRegex)));
        onView(withId(R.id.attempsTextView)).check(matches(new RegexMatcher(initialScoreRegex)));
        onView(withId(R.id.radioButton1)).check(matches(new RegexMatcher(initialRadioButtonRegex)));
        onView(withId(R.id.radioButton2)).check(matches(new RegexMatcher(initialRadioButtonRegex)));
        onView(withId(R.id.radioButton3)).check(matches(new RegexMatcher(initialRadioButtonRegex)));
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
    }

    @Test
    public void anyAnswerUpdatesScoreSystemAppropriately() throws Exception {

        // Arrange
        String expectedAttemptsScore = "1";
        String correctScoreRegex = "[\\w]+\\S[ ]+[01]";

        // Act
        onView(withId(R.id.radioButton1)).perform(click()); // select first radio button to see if we get it right or wrong
        onView(withId(R.id.submitButton)).perform(click());

        // Assert
        onView(withId(R.id.attempsTextView)).check(matches(new ContainsMatcher(expectedAttemptsScore)));
        onView(withId(R.id.correctAnswersTextView)).check(matches(new RegexMatcher(correctScoreRegex)));
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

        private String mValue;

        public ContainsMatcher(String value){

            mValue = value;
        }

        @Override
        public boolean matches(Object item) {
            TextView textView = (TextView)item;
            String text = textView.getText().toString();

            return text.contains(mValue);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Does not contain " + mValue);
        }
    }
}
