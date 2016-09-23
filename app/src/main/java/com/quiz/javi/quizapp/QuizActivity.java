package com.quiz.javi.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import teamtreehouse.quizapp.QuestionBank;

public class QuizActivity extends AppCompatActivity implements QuizActivityView{

    private Map<Integer, Button> mButtonMap;
    private QuizActivityPresenter mPresenter;
    private Quiz mQuiz;

    TextView mQuestionTextView;
    Button mButtonAnswer1;
    Button mButtonAnswer2;
    Button mButtonAnswer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        QuestionBank qBank = new QuestionBank();
        mQuiz = new Quiz(qBank);

        mButtonMap = new HashMap<>();
        mPresenter = new QuizActivityPresenter(this);

        mQuestionTextView = getView(R.id.questionTextView);



        mButtonAnswer1 = getView(R.id.buttonAnswer1);
        mButtonAnswer2 = getView(R.id.buttonAnswer2);
        mButtonAnswer3 = getView(R.id.buttonAnswer3);

        mButtonMap.put(0, mButtonAnswer1);
        mButtonMap.put(1, mButtonAnswer2);
        mButtonMap.put(2, mButtonAnswer3);

        Question question = mQuiz.getQuestion(0);
        mPresenter.updateQuestionTextView(question.getText());

        List<Integer> answers = question.getAnswers();
        Collections.shuffle(answers);
        for(Map.Entry<Integer, Button> entry : mButtonMap.entrySet())
        {
            int value = answers.get(entry.getKey());
            entry
                .getValue()
                .setText(Integer.toString(value));
        }
    }

    @Override
    public void setQuestionTextView(String text) {
        mQuestionTextView.setText(text);
    }

    @Override
    public void setButtonAnswerText(Button button, String text) {
        button.setText(text);
    }

    private <T extends View> T getView(int id){
        return (T) findViewById(id);
    }
}
