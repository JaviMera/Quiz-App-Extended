package com.quiz.javi.quizapp;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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

public class QuizActivity extends AppCompatActivity implements QuizActivityView{

    private final Typeface OSWALD_REGULAR = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");

    private int mCorrectAnswers;
    private Question mCurrentQuestion;
    private SoundPlayer mSoundPlayer;
    private QuizActivityPresenter mPresenter;
    private Quiz mQuiz;
    private SparseArray<RadioButton> mAnswerButtons;

    public TextView mQuestionTextView;
    public RadioGroup mAnswersRadioGroup;
    public AppCompatButton mSubmitButton;
    public TextView mCorrectAnswersTextView;
    public TextView mAttemptsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mAnswerButtons = new SparseArray<>();
        mSoundPlayer = new SoundPlayer(
                MediaPlayer.create(this, R.raw.job_done),
                MediaPlayer.create(this, R.raw.brute_force)
        );


        mQuiz = new Quiz(-100, 100);
        mPresenter = new QuizActivityPresenter(this);

        mSubmitButton = getView(R.id.submitButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAnswersRadioGroup.getCheckedRadioButtonId() != -1) {

                    int attempts = mCurrentQuestion.getNumber();
                    mPresenter.updateAttempsText(attempts);

                    RadioButton rButton = mAnswerButtons.get(mAnswersRadioGroup.getCheckedRadioButtonId());
                    int answerSelected = Integer.parseInt(rButton.getText().toString());
                    boolean result = mCurrentQuestion.isCorrect(answerSelected);

                    mCorrectAnswers += result ? 1 : 0;
                    mPresenter.updateCorrectAnswersText(mCorrectAnswers);

                    String message = mCurrentQuestion.getAnswerMessage(result);
                    displayResult(v.getContext(), message);

                    mSoundPlayer.play(result);

                    mAnswersRadioGroup.clearCheck();
                    mCurrentQuestion = mQuiz.generateQuestion();
                    setQuestion(mCurrentQuestion);
                }
                else{

                    String noSelectionMessage = mCurrentQuestion.getErrorMessage();
                    displayResult(v.getContext(), noSelectionMessage);
                }
            }
        });

        mAnswersRadioGroup = getView(R.id.radioButtonGroup);

        for(int child = 0 ; child < mAnswersRadioGroup.getChildCount(); child++)
        {
            RadioButton rButton = (RadioButton)mAnswersRadioGroup.getChildAt(child);
            rButton.setTypeface(OSWALD_REGULAR);
            mAnswerButtons.append(rButton.getId(), rButton);
        }

        mCorrectAnswersTextView = getView(R.id.correctAnswersTextView);
        mCorrectAnswersTextView.setTypeface(OSWALD_REGULAR);
        mCorrectAnswers = 0;
        mPresenter.updateCorrectAnswersText(mCorrectAnswers);

        mAttemptsTextView = getView(R.id.attempsTextView);
        mAttemptsTextView.setTypeface(OSWALD_REGULAR);
        int attempts = 0;
        mPresenter.updateAttempsText(attempts);

        mQuestionTextView = getView(R.id.questionTextView);
        mQuestionTextView.setTypeface(OSWALD_REGULAR);
        mCurrentQuestion = mQuiz.generateQuestion();
        setQuestion(mCurrentQuestion);
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
    public void updateRadioButtonTextView(int index, String text) {
        RadioButton rButton = (RadioButton)mAnswersRadioGroup.getChildAt(index);
        rButton.setText(text);
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
    public void selectButtonOnAnsweredQuestion(int selectedAnswer) {
        for (int child = 0; child < mAnswersRadioGroup.getChildCount(); child++) {
            RadioButton rButton = (RadioButton) mAnswersRadioGroup.getChildAt(child);
            int rButtonAnswer = Integer.parseInt(rButton.getText().toString());

            if (selectedAnswer == rButtonAnswer) {
                mAnswerButtons.get(rButton.getId()).setChecked(true);
            }
        }
    }

    private void setQuestion(Question question){

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

    private void displayResult(Context ctx, String message){

        Toast.makeText(
                ctx,
                message,
                Toast.LENGTH_SHORT).show();
    }

    public Question getCurrentQuestion() {
        return mCurrentQuestion;
    }
}
