package com.quiz.javi.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity implements QuizActivityView{

    private Map<Button, Integer> mButtonMap;
    private QuizActivityPresenter mPresenter;

    TextView mQuestionTextView;
    Button mButtonAnswer1;
    Button mButtonAnswer2;
    Button mButtonAnswer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mButtonMap = new HashMap<>();
        mPresenter = new QuizActivityPresenter(this);

        mQuestionTextView = getView(R.id.questionTextView);
        mPresenter.updateQuestionTextView("sup");

        mButtonAnswer1 = getView(R.id.buttonAnswer1);
        mButtonAnswer2 = getView(R.id.buttonAnswer2);
        mButtonAnswer3 = getView(R.id.buttonAnswer3);

        mButtonMap.put(mButtonAnswer1, 0);
        mButtonMap.put(mButtonAnswer2, 1);
        mButtonMap.put(mButtonAnswer3, 2);
    }

    @Override
    public void setQuestionTextView(String text) {
        mQuestionTextView.setText(text);
    }

    private <T extends View> T getView(int id){
        return (T) findViewById(id);
    }
}
