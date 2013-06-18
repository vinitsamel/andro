package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	private boolean mAnswerIsTrue;
	private TextView mAnswerTextView;
	private Button mShowAnswerButton;
	private boolean mIsAnswerShown = false;
	public final static String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
	private final static String CURRENT_ANSWER_TEXTSTRING = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(QuizActivity.EXTRA_ANSWER_IS_TRUE, false);
		
		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
		
		if (savedInstanceState != null) {
			mIsAnswerShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN);
			mAnswerTextView.setText(savedInstanceState.getString(CURRENT_ANSWER_TEXTSTRING));
		}
		
		setAnswerShown(mIsAnswerShown);
		mShowAnswerButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				}
				else {
					mAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShown(true);
			}
		});
	}
	
	private void setAnswerShown(boolean isAnswerShown) {
		mIsAnswerShown = isAnswerShown;
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString(CURRENT_ANSWER_TEXTSTRING, mAnswerTextView.getText().toString());
		savedInstanceState.putBoolean(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
	}

}
