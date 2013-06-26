package com.bignerdranch.android.jabwemet;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer extends MediaPlayer {
	private MediaPlayer mPlayer;
	
	@Override
	public void stop() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
	
	public void play(Context c) {
		stop();
		mPlayer = MediaPlayer.create(c, R.raw.thermo);
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer arg0) {
				stop();
			}
		});
		mPlayer.start();
	}
	
	public void onDestroy() {
		//super.onDestroy();
		mPlayer.stop();
	}

}
