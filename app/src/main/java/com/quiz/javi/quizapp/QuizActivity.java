package com.quiz.javi.quizapp;

import android.provider.MediaStore;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
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

    TextView mQuestionTextView;
    RadioGroup mAnswersRadioGroup;
    SparseArray<RadioButton> mAnswerButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mAnswerButtons = new SparseArray<>();
        QuestionBank qBank = new QuestionBank();
        mQuiz = new Quiz(qBank);
        mCurrentQuestion = 0;

        mPresenter = new QuizActivityPresenter(this);

        mQuestionTextView = getView(R.id.questionTextView);
        mAnswersRadioGroup = getView(R.id.radioButtonGroup);
        for(int child = 0 ; child < mAnswersRadioGroup.getChildCount(); child++)
        {
            RadioButton rButton = (RadioButton)mAnswersRadioGroup.getChildAt(child);
            mAnswerButtons.append(rButton.getId(), rButton);
        }

        mAnswersRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

                mCurrentQuestion++;
                Question newQuestion = mQuiz.getQuestion(mCurrentQuestion);
                nextQuestion(newQuestion);
            }
        });

        Question question = mQuiz.getQuestion(mCurrentQuestion);
        nextQuestion(question);
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

    private void nextQuestion(Question question){

        mPresenter.updateQuestionTextView(question.getText());

        Queue<Integer> values = new LinkedList<>(question.getAnswers());
        for(int i = 0 ; i < mAnswerButtons.size() ; i++)
        {
            RadioButton rButton = (RadioButton)mAnswerButtons.valueAt(i);

            rButton.setText(String.format(Locale.ENGLISH,"%d", values.poll()));

            if(!question.getReviewed())
            {
                rButton.setChecked(false);
            }
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
