package com.quiz.javi.quizapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

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
        presenter.updateQuestionTextView(question);

        // Assert
        Mockito.verify(view).setQuestionTextView(question);
    }
}