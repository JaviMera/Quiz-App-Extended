package com.quiz.javi.quizapp;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

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
    public void getAnswersReturnsMatchingAnswersToQuestion(){

        // Assert
        List<Integer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(7);
        expectedAnswers.add(6);
        expectedAnswers.add(8);

        question = createQuestion(3,4,7,6,8);

        // Act
        List<Integer> actualAnswers = question.getAnswers();

        // Assert
        assertEquals(expectedAnswers, actualAnswers);
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