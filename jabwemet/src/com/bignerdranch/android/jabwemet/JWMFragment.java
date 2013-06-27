package com.bignerdranch.android.jabwemet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class JWMFragment extends Fragment {
	private Button mPlayButton;
	private Button mStopButton;
	private AudioPlayer aPlayer = new AudioPlayer();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_jwm, null, false);
		
		mPlayButton = (Button) v.findViewById(R.id.jwm_playButton);
		mPlayButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				aPlayer.play(getActivity());
			}
		});
		
		mStopButton = (Button) v.findViewById(R.id.jwm_stopButton);
		mStopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				aPlayer.stop();
			}
		});
		
		return v;
	}

}
