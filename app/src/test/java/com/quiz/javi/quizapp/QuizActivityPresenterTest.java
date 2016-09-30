package com.quiz.javi.quizapp;

import android.graphics.Color;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Javi on 9/22/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuizActivityPresenterTest {

    QuizActivityPresenter presenter;

    @Mock
    QuizActivityView view;

    @Before
    public void setUp() throws Exception {
        presenter = new QuizActivityPresenter(view);
    }

    @Test
    public void updateQuestionTextView(){

        // Arrange
        String question = "All Harambe lives matter";

        // Act
        presenter.updateQuestionText(question);

        // Assert
        Mockito.verify(view).updateQuestionTextView(question);
    }

    @Test
    public void updateButtonAnswerText(){

        // Arrange
        Button button = new Button(null);
        String answerText = "100";

        // Act
        presenter.updateButtonText(button, answerText);

        // Assert
        Mockito.verify(view).updateButtonAnswerTextView(button, answerText);
    }

    @Test
    public void updateCorrectAnswerText() throws Exception {

        // Arrange
        int correctNumber = 0;

        // Act
        presenter.updateCorrectAnswersText(correctNumber);

        // Assert
        Mockito.verify(view).updateCorrectAnswersTextView(correctNumber);
    }

    @Test
    public void updateAttempsText() throws Exception {

        // Arrange
        int attemptsNumber = 0;

        // Act
        presenter.updateAttempsText(attemptsNumber);

        // Assert
        Mockito.verify(view).updateAttempsTextView(attemptsNumber);
    }

    @Test
    public void updateButtonTextColor() throws Exception {

        // Arrange
        Button button = new Button(null);
        int color = Color.RED;

        // Act
        presenter.updateButtonTextColor(button, color);

        // Assert
        Mockito.verify(view).updateButtonTextColor(button, color);
    }

    @Test
    public void updateButtonBackgroundDrawable(){

        // Assert
        Button button = new Button(null);
        int someDrawable = 123;

        // Act
        presenter.updateButtonBackgroundDrawable(button, someDrawable);

        // Assert
        Mockito.verify(view).updateButtonBackgroundDrawable(button, someDrawable);
    }

    @Test
    public void updateButtonState() throws Exception {

        // Assert
        Button button = new Button(null);
        boolean enabled = false;

        // Act
        presenter.updateButtonState(button, enabled);

        // Assert
        Mockito.verify(view).updateButtonEnabledState(button, enabled);
    }

    @Test
    public void updateButtonSelect() throws Exception {

        // Assert
        Button button = new Button(null);
        boolean selected = false;

        // Act
        presenter.updateButtonSelectState(button, selected);

        // Assert
        Mockito.verify(view).updateButtonSelectState(button, selected);
    }
}