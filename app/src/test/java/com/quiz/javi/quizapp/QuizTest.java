package com.quiz.javi.quizapp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import teamtreehouse.quizapp.QuestionBank;

import static org.junit.Assert.*;

/**
 * Created by Javi on 9/22/2016.
 */
public class QuizTest {

    private Quiz quiz;
    private int min;
    private int max;

    @Before
    public void setUp() throws Exception {

        min = -10;
        max = 10;

        quiz = new Quiz(min, max);
    }

    @Test
    public void generateQuestion(){

        // Arrange
        Question question = quiz.generateQuestion();
        List<Integer> answers = question.getAnswers();
        Pattern p = Pattern.compile("(-?\\d+)"); // regex will search for all numbers contained in the question. Both positive and negative
        Matcher matcher = p.matcher(question.getText());

        // Act
        int leftAdder = Integer.parseInt(matcher.find() ? matcher.group(1) : ""); // the first time group is called, returns the first found number (left adder)
        int rightAdder = Integer.parseInt(matcher.find() ? matcher.group(1) : ""); // the second time group is called, returns the second found number (right adder)

        // Assert that at least every number generated, is constrained in the range provided in quiz constructor
        assertTrue(leftAdder >= min && leftAdder <= max);
        assertTrue(rightAdder >= min && rightAdder <= max);

        int correctAnswer = leftAdder + rightAdder;
        assertEquals(correctAnswer, (int)answers.get(0));
        assertTrue(answers.get(1) >= correctAnswer - 10 && answers.get(1) <= correctAnswer + 10);
        assertTrue(answers.get(2) >= correctAnswer - 10 && answers.get(2) <= correctAnswer + 10);
    }
}