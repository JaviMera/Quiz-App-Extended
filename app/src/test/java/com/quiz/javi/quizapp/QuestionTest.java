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
    public void questionInit() throws Exception {

        // Arrange
        question = createQuestion(1,3,4,7,6,8);

        // Act
        String actualText = question.getText();
        int actualNumber = question.getNumber();
        int actualSize = question.getAnswers().size();

        // Assert
        assertTrue(actualText.matches("[\\w ]+-?[\\d]+ \\+ \\(?-?[\\d]+\\)??\\?"));
        Assert.assertEquals(1, actualNumber);
        Assert.assertEquals(3, actualSize);
    }

    @Test
    public void isCorrectReturnsTrueOnCorrectSelection(){

        // Arrange
        question = createQuestion(1,3,4,7,6,8);
        int correctAnswer = question.getAnswers().get(0);

        // Act
        boolean result = question.isCorrect(correctAnswer);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isCorrectReturnsFalseOnIncorrectSelection(){

        // Arrange
        question = createQuestion(1,3,4,7,6,8);
        int correctAnswer = question.getAnswers().get(1);

        // Act
        boolean result = question.isCorrect(correctAnswer);

        // Assert
        Assert.assertFalse(result);
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