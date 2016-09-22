package com.quiz.javi.quizapp;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Javi on 9/21/2016.
 */
public class QuestionTest {

    private Question question;

    @Test
    public void getText() throws Exception {

        // Arrange
        question = createQuestion(3,4,7,6,8);

        // Act
        String text = question.getText();

        // Assert
        Assert.assertEquals("What is 3 + 4?", text);
    }

    @Test
    public void isCorrect() throws Exception {

        // Arrange
        question = createQuestion(3,4,7,6,8);

        // Act
        boolean passed = question.isCorrect(7);

        // Assert
        Assert.assertTrue(passed);
    }

    @Test
    public void getAnswer() throws Exception {

        // Arrange
        question = createQuestion(3,4,7,6,8);

        // Act
        int answer = question.getAnswer(1);

        // Assert
        Assert.assertEquals(6, answer);
    }

    private Question createQuestion(int leftAdder, int rightAdder, int correct, int incorrect1, int incorrect2){

        return new Question(
                leftAdder,
                rightAdder,
                correct,
                incorrect1,
                incorrect2);
    }
}