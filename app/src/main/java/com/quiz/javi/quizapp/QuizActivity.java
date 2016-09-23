package com.quiz.javi.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private Map<Button, Integer> mButtonMap;

    TextView mQuestionTextView;
    Button mButtonAnswer1;
    Button mButtonAnswer2;
    Button mButtonAnswer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mButtonMap = new HashMap<>();

        mButtonAnswer1 = getButton(R.id.buttonAnswer1);
        mButtonAnswer2 = getButton(R.id.buttonAnswer2);
        mButtonAnswer3 = getButton(R.id.buttonAnswer3);

        mButtonMap.put(mButtonAnswer1, 0);
        mButtonMap.put(mButtonAnswer2, 1);
        mButtonMap.put(mButtonAnswer3, 2);
    }

    private Button getButton(int id){
        return (Button) findViewById(id);
    }
}
