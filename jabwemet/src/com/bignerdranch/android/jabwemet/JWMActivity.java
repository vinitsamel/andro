package com.bignerdranch.android.jabwemet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class JWMActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jwm);
		
		FragmentManager fMgr = getSupportFragmentManager();
		Fragment frag = fMgr.findFragmentById(R.id.fragmentContainer);
		
		if (frag == null) {
			frag = new JWMFragment();
			fMgr.beginTransaction().add(R.id.fragmentContainer, frag).commit();
		}
		
	}

}
