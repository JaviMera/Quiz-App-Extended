package com.quiz.javi.quizapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import teamtreehouse.quizapp.QuestionBank;

public class QuizActivity extends AppCompatActivity implements QuizActivityView{

    private boolean mNextQuestionButtonClicked;
    private Question mCurrentQuestion;

    private QuizActivityPresenter mPresenter;
    private Quiz mQuiz;
    private SparseArray<RadioButton> mAnswerButtons;

    TextView mQuestionTextView;
    RadioGroup mAnswersRadioGroup;
    AppCompatButton mSubmitButton;
    TextView mQuestionNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mAnswerButtons = new SparseArray<>();
        QuestionBank qBank = new QuestionBank();
        mQuiz = new Quiz(qBank);
        mPresenter = new QuizActivityPresenter(this);

        mSubmitButton = getView(R.id.submitButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCurrentQuestion.getAnswerSelected() != -1)
                {
                    String toastMessage = getResultMessage(mCurrentQuestion);
                    displayResult(v.getContext(), toastMessage);
                }

                // Use this flag to not call the Toast message code, inside of CheckedChange, when the Next button is pressed
                mNextQuestionButtonClicked = true;

                mAnswersRadioGroup.clearCheck();
                mCurrentQuestion = mQuiz.getQuestion();
                setQuestion(mCurrentQuestion);

                if(mCurrentQuestion.getAnswerSelected() != -1)
                {
                    for (int child = 0; child < mAnswersRadioGroup.getChildCount(); child++) {
                        RadioButton rButton = (RadioButton) mAnswersRadioGroup.getChildAt(child);
                        int rButtonAnswer = Integer.parseInt(rButton.getText().toString());

                        if(mCurrentQuestion.getAnswerSelected() == rButtonAnswer){
                            mAnswerButtons.get(rButton.getId()).setChecked(true);
                        }
                    }
                }

                mNextQuestionButtonClicked = false;
            }
        });

        mQuestionTextView = getView(R.id.questionTextView);
        mQuestionNumberTextView = getView(R.id.questionNumberTextView);

        mAnswersRadioGroup = getView(R.id.radioButtonGroup);

        for(int child = 0 ; child < mAnswersRadioGroup.getChildCount(); child++)
        {
            RadioButton rButton = (RadioButton)mAnswersRadioGroup.getChildAt(child);
            mAnswerButtons.append(rButton.getId(), rButton);
        }

        mAnswersRadioGroup.setOnCheckedChangeListener(checkedListener());

        mCurrentQuestion = mQuiz.getQuestion();
        setQuestion(mCurrentQuestion);
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
                int answerSelected = Integer.parseInt(rButton.getText().toString());
                mCurrentQuestion .setAnswerSelected(answerSelected);
            }
        };
    }

    @Override
    public void updateQuestionTextView(String text) {
        mQuestionTextView.setText(text);
    }

    @Override
    public void updateRadioButtonTextView(int index, String text) {
        RadioButton rButton = (RadioButton)mAnswersRadioGroup.getChildAt(index);
        rButton.setText(text);
    }

    @Override
    public void updateQuestionNumberTextView(String questionNumber) {
        mQuestionNumberTextView.setText(questionNumber);
    }

    private void setQuestion(Question question){

        mPresenter.updateQuestionNumberText(String.format(Locale.ENGLISH, "%d )", question.getNumber()));
        mPresenter.updateQuestionText(question.getText());

        List<Integer> values = new ArrayList<>(question.getAnswers());
        Collections.shuffle(values);
        for(int i = 0 ; i < mAnswerButtons.size() ; i++)
        {
            mPresenter.updateRadioButtonText(i, String.format(Locale.ENGLISH,"%d", values.get(i)));
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
