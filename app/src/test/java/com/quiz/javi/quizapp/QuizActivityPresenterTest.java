package com.quiz.javi.quizapp;

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
        String answerText = "How about no";
        int index = 0;

        // Act
        presenter.updateRadioButtonText(index, answerText);

        // Assert
        Mockito.verify(view).updateRadioButtonTextView(index, answerText);
    }

    @Test
    public void updateQuestionNumberText(){

        // Arrange
        String questionNumber = "1";

        // Act
        presenter.updateQuestionNumberText(questionNumber);

        // Assert
        Mockito.verify(view).updateQuestionNumberTextView(questionNumber);
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
}