package com.quiz.javi.quizapp;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import teamtreehouse.quizapp.QuestionBank;

public class QuizActivity extends AppCompatActivity implements QuizActivityView{

    private boolean mNextQuestionButtonClicked;
    private int mCurrentQuestion;

    private QuizActivityPresenter mPresenter;
    private Quiz mQuiz;
    private SparseArray<RadioButton> mAnswerButtons;

    TextView mQuestionTextView;
    RadioGroup mAnswersRadioGroup;
    AppCompatButton mNextQuestionButton;

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
                // Use this flag to not call the Toast message code, inside of CheckedChange, when the Next button is pressed
                mNextQuestionButtonClicked = true;

                mAnswersRadioGroup.clearCheck();
                mCurrentQuestion++;
                Question newQuestion = mQuiz.getQuestion(mCurrentQuestion);
                displayQuestion(newQuestion);

                if(newQuestion.getReviewed())
                {
                    for (int child = 0; child < mAnswersRadioGroup.getChildCount(); child++) {
                        RadioButton rButton = (RadioButton) mAnswersRadioGroup.getChildAt(child);
                        int rButtonAnswer = Integer.parseInt(rButton.getText().toString());

                        if(newQuestion.getAnswerSelected() == rButtonAnswer){
                            mAnswerButtons.get(rButton.getId()).setChecked(true);
                        }
                    }
                }

                mNextQuestionButtonClicked = false;
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

                // Avoid calling the Toast code, if this Listener has been fired by calling radioGroup.clearCheck();
                if(group.getCheckedRadioButtonId() == -1)
                    return;

                // Avoid calling the Toast code, if this Listener has been fired by pressing the Next Button
                if(mNextQuestionButtonClicked)
                    return;

                RadioButton rButton = mAnswerButtons.get(checkedId);
                Question question = mQuiz.getQuestion(mCurrentQuestion);
                int answerSelected = Integer.parseInt(rButton.getText().toString());
                question.setAnswerSelected(answerSelected);
                question.setReviewed(true);

                String toastMessage = getResultMessage(question);
                displayResult(group.getContext(), toastMessage);
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

    private String getResultMessage(Question question){

        if(question.isCorrect())
        {
            return "Correct Answer!!";
        }
        else
        {
            return "Wrong Answer :(, please don't cry";
        }
    }

    private void displayResult(Context ctx, String message){

        Toast.makeText(
                ctx,
                message,
                Toast.LENGTH_SHORT).show();
    }
}
