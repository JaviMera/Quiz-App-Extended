package com.quiz.javi.quizapp;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import teamtreehouse.quizapp.QuestionBank;

public class QuizActivity extends AppCompatActivity implements QuizActivityView{

    private QuizActivityPresenter mPresenter;
    private Quiz mQuiz;
    private int mCurrentQuestion;
    private SparseArray<RadioButton> mAnswerButtons;

    TextView mQuestionTextView;
    RadioGroup mAnswersRadioGroup;
    ImageButton mNextQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mAnswerButtons = new SparseArray<>();
        QuestionBank qBank = new QuestionBank();
        mQuiz = new Quiz(qBank);
        mCurrentQuestion = 0;
        mPresenter = new QuizActivityPresenter(this);

        mNextQuestionButton = getView(R.id.nextQuestionButton);
        mNextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentQuestion++;
                Question newQuestion = mQuiz.getQuestion(mCurrentQuestion);
                displayQuestion(newQuestion);

                mAnswersRadioGroup.setOnCheckedChangeListener(null);
                if(!newQuestion.getReviewed())
                {
                    for(int child = 0 ; child < mAnswersRadioGroup.getChildCount(); child++)
                    {
                        RadioButton rButton = (RadioButton)mAnswersRadioGroup.getChildAt(child);
                        rButton.setChecked(false);
                    }
                }

                mAnswersRadioGroup.setOnCheckedChangeListener(checkedListener());
            }
        });

        mQuestionTextView = getView(R.id.questionTextView);
        mAnswersRadioGroup = getView(R.id.radioButtonGroup);
        for(int child = 0 ; child < mAnswersRadioGroup.getChildCount(); child++)
        {
            RadioButton rButton = (RadioButton)mAnswersRadioGroup.getChildAt(child);
            mAnswerButtons.append(rButton.getId(), rButton);
        }

        mAnswersRadioGroup.setOnCheckedChangeListener(checkedListener());

        Question question = mQuiz.getQuestion(mCurrentQuestion);
        displayQuestion(question);
    }

    private RadioGroup.OnCheckedChangeListener checkedListener() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rButton = mAnswerButtons.get(checkedId);
                String buttonText = getButtonText(rButton);
                Question question = mQuiz.getQuestion(mCurrentQuestion);
                String toastMessage = getResultMessage(buttonText,question);

                Toast.makeText(
                        group.getContext(),
                        toastMessage,
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void setQuestionTextView(String text) {
        mQuestionTextView.setText(text);
    }

    @Override
    public void setRadioButtonText(int index, String text) {
        RadioButton rButton = (RadioButton)mAnswersRadioGroup.getChildAt(index);
        rButton.setText(text);
    }

    private void displayQuestion(Question question){

        mPresenter.updateQuestionTextView(question.getText());

        Queue<Integer> values = new LinkedList<>(question.getAnswers());
        for(int i = 0 ; i < mAnswerButtons.size() ; i++)
        {
            RadioButton rButton = mAnswerButtons.valueAt(i);

            rButton.setText(String.format(Locale.ENGLISH,"%d", values.poll()));
        }
    }

    private <T extends View> T getView(int id){
        return (T) findViewById(id);
    }

    private String getButtonText(RadioButton rb){

        return rb.getText().toString();
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
