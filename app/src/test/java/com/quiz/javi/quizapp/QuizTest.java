package com.quiz.javi.quizapp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import teamtreehouse.quizapp.QuestionBank;

import static org.junit.Assert.*;

/**
 * Created by Javi on 9/22/2016.
 */
public class QuizTest {

    Quiz quiz;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void showFirstQuestion(){

        // Arrange
        QuestionBank qBank = new QuestionBank();
        int questionNumber = 0;
        String question = "What is " +
                qBank.leftAdders[questionNumber] +
                " + " +
                qBank.rightAdders[questionNumber] +
                "?";

        // Act
        quiz = new Quiz(qBank);
        Question actualQuestion = quiz.getQuestion(questionNumber);

        // Assert
        Assert.assertEquals(actualQuestion.getText(), question);
    }

    @Test
    public void showRandomQuestion(){

        // Arrange
        QuestionBank qBank = new QuestionBank();
        int questionNumber = new Random().nextInt(qBank.leftAdders.length);
        String question = "What is " +
                qBank.leftAdders[questionNumber] +
                " + " +
                qBank.rightAdders[questionNumber] +
                "?";

        // Act
        quiz = new Quiz(qBank);
        Question actualQuestion = quiz.getQuestion(questionNumber);

        // Assert
        Assert.assertEquals(actualQuestion.getText(), question);
    }

    @Test
    public void totalQuestionsReturnsRightAmountOfQuestions(){

        // Assert
        QuestionBank qBank = new QuestionBank();
        int expectedLength = qBank.correctAnswers.length;

        // Act
        quiz = new Quiz(qBank);
        int actualLength = quiz.totalQuestions();

        // Assert
        Assert.assertEquals(expectedLength, actualLength);
    }
}