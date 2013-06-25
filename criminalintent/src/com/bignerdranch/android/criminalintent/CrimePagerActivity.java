package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class CrimePagerActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mCrimes = CrimeLab.getInstance(this).getCrimes();
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mCrimes.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Crime c = mCrimes.get(pos);
				return CrimeFragment.newInstance(c.getId());
			}
		});
		
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				Crime cr = mCrimes.get(pos);
				if (cr.getTitle() != null) setTitle(cr.getTitle());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < mCrimes.size(); ++i) {
			if (mCrimes.get(i).getId().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		
	}
	
	

}
