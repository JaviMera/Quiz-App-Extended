package com.quiz.javi.quizapp;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Javi on 9/21/2016.
 */
public class QuestionTest {

    private Question question;

    @Test
    public void getText() throws Exception {

        // Arrange
        question = createQuestion(1,3,4,7,6,8);

        // Act
        String text = question.getText();

        // Assert
        Assert.assertEquals("What is 3 + 4?", text);
    }

    @Test
    public void setRightAnswerSelectedReturnsCorrect() throws Exception {

        // Arrange
        Question question = createQuestion(1,2,3,5,4,6);

        // Act
        question.setAnswerSelected(5);

        // Assert
        Assert.assertTrue(question.isCorrect());
    }

    @Test
    public void setWrongAnswerSelectedReturnsIncorrect() throws Exception {

        // Arrange
        Question question = createQuestion(1,2,3,5,4,6);

        // Act
        question.setAnswerSelected(4);

        // Assert
        Assert.assertFalse(question.isCorrect());
    }

    @Test
    public void getAnswerSelected() throws Exception {

        // Arrange
        Question question = createQuestion(1,2,3,5,4,6);
        int expected = 4;

        // Act
        question.setAnswerSelected(expected);
        int actual = question.getAnswerSelected();

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAnswersReturnsMatchingAnswersToQuestion(){

        // Assert
        List<Integer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(7);
        expectedAnswers.add(6);
        expectedAnswers.add(8);

        question = createQuestion(1,3,4,7,6,8);

        // Act
        List<Integer> actualAnswers = question.getAnswers();

        // Assert
        assertEquals(expectedAnswers, actualAnswers);
    }

    @Test
    public void getReviewedReturnsFalseAtInitialization(){

        // Assert
        question = createQuestion(1,1,2,3,4,5);

        // Act
        boolean isReviewed = question.getReviewed();

        // Assert
        assertFalse(isReviewed);
    }

    @Test
    public void getReviewedReturnsTrueAfterSelectingAnswer(){

        // Assert
        question = createQuestion(1,1,2,3,4,5);

        // Act
        question.setAnswerSelected(4);
        question.setReviewed(true);

        // Assert
        assertTrue(question.getReviewed());
    }

    @Test
    public void getQuestionNumber(){

        // Assert
        question = createQuestion(1,1,2,3,4,5);

        // Act
        int expectedNumber = question.getNumber();

        // Assert
        Assert.assertEquals(1, expectedNumber);
    }

    private Question createQuestion(int questionNumber, int leftAdder, int rightAdder, int correct, int incorrect1, int incorrect2){

        return new Question(
                questionNumber,
                leftAdder,
                rightAdder,
                correct,
                incorrect1,
                incorrect2);
    }
}