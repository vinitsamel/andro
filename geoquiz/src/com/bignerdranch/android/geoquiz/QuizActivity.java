package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private TextView mQTextView;
	private static final String TAG = "QuizActivity";
	private static final String CURRENTINDEX = "CurrentIndex";
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.q_2, false),
			new TrueFalse(R.string.q_Google, true),
			new TrueFalse(R.string.q_SD, true),
			new TrueFalse(R.string.q_Sky, false)
	};
	
	private int mCurrentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		Log.d(TAG, "onCreate()");
		
		if (null != savedInstanceState) mCurrentIndex = savedInstanceState.getInt(CURRENTINDEX, 0);
		
		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mQuestionBank[mCurrentIndex].isTrueQuestion())
					Toast.makeText(QuizActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();	
				else
					Toast.makeText(QuizActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
			}
		});
		
		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!mQuestionBank[mCurrentIndex].isTrueQuestion())
					Toast.makeText(QuizActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();	
				else
					Toast.makeText(QuizActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
			}
		});
		
		mQTextView = (TextView) findViewById(R.id.question_text_view);
		updateQuestion();
		
		mNextButton = (Button) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex++;
				if (mCurrentIndex + 1 > mQuestionBank.length) mCurrentIndex = 0;
				updateQuestion();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_quiz, menu);
		return true;
	}
	
	private void updateQuestion() {
		int questionId = mQuestionBank[mCurrentIndex].getQuestion();
		mQTextView.setText(questionId);	
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy()");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart()");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop()");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume()");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.d(TAG, "onSaveInstanceState with (K,V) = (" + CURRENTINDEX + ", " + mCurrentIndex + ")");
		savedInstanceState.putInt(CURRENTINDEX, mCurrentIndex);
	}

}
