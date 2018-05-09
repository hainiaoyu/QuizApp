package com.example.yimingli.quizapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionView;
    private TextView mScoreView;
    private ProgressBar mProgressBar;
    int index;
    int score;

    // Create questions and defined their answer
    private TrueFalse[] questionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, true)
    };

    // In order to calculate the round up number near an integer
    final int PROGRESS_BAR_INCREASE = (int) Math.ceil(100.00 / questionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If rotate the screen the savedInstanceState is not null
        // Restore the state of app when screen rotation
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("ScoreKey");
            index = savedInstanceState.getInt("IndexKey");
        } else {
            score = 0;
            index = 0;
        }

        mTrueButton = findViewById(R.id.button_true);
        mFalseButton = findViewById(R.id.button_false);
        mQuestionView = findViewById(R.id.question);
        mScoreView = findViewById(R.id.score);
        mProgressBar = findViewById(R.id.progress_bar);

        // By default, the question view should show the first question at first
        mQuestionView.setText(questionBank[index].getQuestionId());

        mScoreView.setText("Score " + score + "/" + questionBank.length);

        // Add on click listener to the two buttons
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateQuestion();
            }
        });

    }

    private void updateQuestion() {
        index = (index + 1) % questionBank.length;

        // Present an alert dialog if we reach the end.
        if (index == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + score + " points");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
        mQuestionView.setText(questionBank[index].getQuestionId());
        mScoreView.setText("Score " + score + "/" + questionBank.length);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREASE);
    }

    private void checkAnswer(boolean userSelection) {
        boolean answer = questionBank[index].isAnswer();
        if (userSelection == answer) {
            Toast.makeText(getApplicationContext(), R.string.toast_correct, Toast.LENGTH_SHORT).show();
            score = score + 1;
        } else {
            Toast.makeText(getApplicationContext(), R.string.toast_incorrect, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", score);
        outState.putInt("IndexKey", index);
    }
}
