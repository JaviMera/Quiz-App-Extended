package com.quiz.javi.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity implements QuizActivityView{

    private int mCorrectAnswers;
    private Question mCurrentQuestion;
    private SoundPlayer mSoundPlayer;
    private QuizActivityPresenter mPresenter;
    private Quiz mQuiz;
    private SparseArray<AppCompatButton> mAnswerButtons;

    public TextView mQuestionTextView;
    public TextView mCorrectAnswersTextView;
    public TextView mAttemptsTextView;
    public AppCompatButton mSubmitButton;
    public AppCompatButton mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");

        mAnswerButtons = new SparseArray<>();

        mSoundPlayer = new SoundPlayer(
                MediaPlayer.create(this, R.raw.job_done),
                MediaPlayer.create(this, R.raw.brute_force)
        );


        mQuiz = new Quiz(-100, 100);
        mPresenter = new QuizActivityPresenter(this);

        mCorrectAnswersTextView = getView(R.id.correctAnswersTextView);
        mCorrectAnswersTextView.setTypeface(typeFace);
        mCorrectAnswers = 0;
        mPresenter.updateCorrectAnswersText(mCorrectAnswers);

        mAttemptsTextView = getView(R.id.attempsTextView);
        mAttemptsTextView.setTypeface(typeFace);
        int attempts = 0;
        mPresenter.updateAttempsText(attempts);

        mQuestionTextView = getView(R.id.questionTextView);
        mQuestionTextView.setTypeface(typeFace);

        initializeButtonsArray(
                mAnswerButtons,
                R.id.buttonAnswer1,
                R.id.buttonAnswer2,
                R.id.buttonAnswer3);

        mSubmitButton = getView(R.id.submitButton);
        mSubmitButton.setTypeface(typeFace);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            AppCompatButton button = null;
            for(int i = 0 ; i < mAnswerButtons.size(); i++)
            {
                int key = mAnswerButtons.keyAt(i);
                if(mAnswerButtons.get(key).isSelected())
                {
                    button = mAnswerButtons.get(key);
                    break;
                }
            }

            if(button == null){
                String noSelectionMessage = mCurrentQuestion.getErrorMessage();
                displayResult(v.getContext(), noSelectionMessage);

                return;
            }

            for(int i = 0 ; i < mAnswerButtons.size() ; i++)
            {
                int key = mAnswerButtons.keyAt(i);
                mAnswerButtons.get(key).setEnabled(false);
            }

            int answerSelected = Integer.parseInt(button.getText().toString());
            boolean result = mCurrentQuestion.isCorrect(answerSelected);

            mNextButton.setEnabled(true);
            mSubmitButton.setEnabled(false);

            if(result)
            {
                button.setBackgroundResource(R.drawable.selected_correct_answer_button_background);
                button.setTextColor(Color.parseColor(getString(R.string.correct_color)));
                mSubmitButton.setBackgroundResource(R.drawable.submit_correct_background);
                mSubmitButton.setText(getString(R.string.submit_button_correct));
                mCorrectAnswers++;
                mPresenter.updateCorrectAnswersText(mCorrectAnswers);
            }
            else
            {
                button.setBackgroundResource(R.drawable.selected_incorrect_answer_button_background);
                button.setTextColor(Color.parseColor(getString(R.string.incorrect_color)));
                mSubmitButton.setBackgroundResource(R.drawable.submit_incorrect_background);
                mSubmitButton.setText(getString(R.string.submit_button_incorrect));
            }

            int attempts = mCurrentQuestion.getNumber();
            mPresenter.updateAttempsText(attempts);

            mSoundPlayer.play(result);
        }});

        mNextButton = getView(R.id.nextQuestionButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentQuestion = mQuiz.generateQuestion();
                setQuestion(mCurrentQuestion, mAnswerButtons);

                for(int i = 0 ; i < mAnswerButtons.size() ; i++)
                {
                    int key = mAnswerButtons.keyAt(i);
                    mAnswerButtons.get(key).setEnabled(true);
                    mAnswerButtons.get(key).setSelected(false);
                }

                mSubmitButton.setText(getString(R.string.submit_button));
                mSubmitButton.setEnabled(true);
                mSubmitButton.setBackgroundResource(R.drawable.submit_button_background);

                // disable this view which is the next button
                v.setEnabled(false);
            }
        });


        mCurrentQuestion = mQuiz.generateQuestion();
        setQuestion(mCurrentQuestion, mAnswerButtons);
    }

    private void initializeButtonsArray(SparseArray<AppCompatButton> buttons, int...ids){

        for(int id : ids)
        {
            AppCompatButton button = getView(id);
            buttons.append(id, button);
        }
    }

    public void onButtonAnswerListener(View view) {

        for(int i = 0 ; i < mAnswerButtons.size() ; i++)
        {
            AppCompatButton button = mAnswerButtons.valueAt(i);
            button.setSelected(false);
            button.setTextColor(Color.parseColor(getString(R.string.button_answer_unselected)));
        }

        AppCompatButton button = mAnswerButtons.get(view.getId());
        button.setSelected(true);
        button.setTextColor(Color.parseColor(getString(R.string.button_answer_selected)));
    }

    @Override
    protected void onStop() {
        super.onStop();

        mSoundPlayer.dispose();
    }

    @Override
    public void updateQuestionTextView(String text) {
        mQuestionTextView.setText(text);
    }

    @Override
    public void updateCorrectAnswersTextView(int correctNumber) {
        mCorrectAnswersTextView.setText(String.format(Locale.ENGLISH, "Correct: %d", correctNumber));
    }

    @Override
    public void updateAttempsTextView(int attemptsNumber) {
        mAttemptsTextView.setText(String.format(Locale.ENGLISH, "Attempts: %d", attemptsNumber));
    }

    @Override
    public void updateButtonAnswerTextView(int id, String answerText) {

        mAnswerButtons
            .get(id)
            .setText(answerText);
    }

    private void setQuestion(Question question, SparseArray<AppCompatButton> answerButtons){

        mPresenter.updateQuestionText(question.getText());

        List<Integer> values = new ArrayList<>(question.getAnswers());
        Collections.shuffle(values);

        for(int i = 0 ; i < answerButtons.size(); i++)
        {
            int buttonId = answerButtons.keyAt(i);
            AppCompatButton button = answerButtons.get(buttonId);
            button.setSelected(false);
            button.setTextColor(Color.parseColor(getString(R.string.button_answer_unselected)));
            button.setBackgroundResource(R.drawable.selector_answer_button_background);
            mPresenter.updateButtonAnswerText(buttonId, String.format(Locale.ENGLISH, "%d", values.get(i)));
        }
    }

    private <T extends View> T getView(int id){
        return (T) findViewById(id);
    }

    private void displayResult(Context ctx, String message){

        Toast.makeText(
                ctx,
                message,
                Toast.LENGTH_SHORT).show();
    }
}
