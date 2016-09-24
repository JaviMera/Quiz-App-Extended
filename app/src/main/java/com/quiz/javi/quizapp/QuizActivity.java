package com.quiz.javi.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamtreehouse.quizapp.QuestionBank;

public class QuizActivity extends AppCompatActivity implements QuizActivityView{

    private Map<Integer, AppCompatButton> mButtonMap;
    private QuizActivityPresenter mPresenter;
    private Quiz mQuiz;
    private int mCurrentQuestion;

    TextView mQuestionTextView;
    AppCompatButton mButtonAnswer1;
    AppCompatButton mButtonAnswer2;
    AppCompatButton mButtonAnswer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        QuestionBank qBank = new QuestionBank();
        mQuiz = new Quiz(qBank);
        mCurrentQuestion = 0;

        mButtonMap = new HashMap<>();
        mPresenter = new QuizActivityPresenter(this);

        mQuestionTextView = getView(R.id.questionTextView);

        mButtonAnswer1 = getView(R.id.buttonAnswer1);
        mButtonAnswer2 = getView(R.id.buttonAnswer2);
        mButtonAnswer3 = getView(R.id.buttonAnswer3);

        mButtonMap.put(0, mButtonAnswer1);
        mButtonMap.put(1, mButtonAnswer2);
        mButtonMap.put(2, mButtonAnswer3);

        Question question = mQuiz.getQuestion(mCurrentQuestion);
        nextQuestion(question);
    }

    public void buttonAnswerOnClick(View v){

        Question question = mQuiz.getQuestion(mCurrentQuestion);
        String buttonText = getButtonText(v);
        String toastMessage = getResultMessage(buttonText,question);

        Toast.makeText(
                this,
                toastMessage,
                Toast.LENGTH_SHORT).show();

        mCurrentQuestion++;
        question = mQuiz.getQuestion(mCurrentQuestion);
        nextQuestion(question);
    }

    @Override
    public void setQuestionTextView(String text) {
        mQuestionTextView.setText(text);
    }

    @Override
    public void setButtonAnswerText(Button button, String text) {
        button.setText(text);
    }

    private void nextQuestion(Question question){

        mPresenter.updateQuestionTextView(question.getText());

        List<Integer> answers = question.getAnswers();
        for(Map.Entry<Integer, AppCompatButton> entry : mButtonMap.entrySet())
        {
            int value = answers.get(entry.getKey());
            mPresenter.updateButtonAnswerText(entry.getValue(), String.valueOf(value));
        }
    }

    private <T extends View> T getView(int id){
        return (T) findViewById(id);
    }

    private String getButtonText(View v){

        String text = "";
        switch(v.getId())
        {
            case R.id.buttonAnswer1:
                text = mButtonAnswer1.getText().toString();
                break;
            case R.id.buttonAnswer2:
                text = mButtonAnswer2.getText().toString();
                break;
            case R.id.buttonAnswer3:
                text = mButtonAnswer3.getText().toString();
                break;
        }

        return text;
    }

    private String getResultMessage(String buttonText, Question question){

        int buttonValue = Integer.parseInt(buttonText);
        if(question.isCorrect(buttonValue))
        {
            return "Correct Answer!!";
        }
        else
        {
            return "Wrong Answer :(, please don't cry";
        }
    }
}
