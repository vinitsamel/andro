package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
	protected abstract Fragment createFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		
		FragmentManager fragMgr = getSupportFragmentManager();
		Fragment frag = fragMgr.findFragmentById(R.id.fragmentContainer);
		
		if (null == frag) {
			frag = createFragment();
			fragMgr.beginTransaction().add(R.id.fragmentContainer, frag).commit();
		}
	}

}
