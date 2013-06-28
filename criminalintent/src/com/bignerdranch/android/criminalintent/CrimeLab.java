package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
	private static CrimeLab sCrimeLab;
	private Context context;
	private ArrayList<Crime> mCrimes;
	
	private CrimeLab(Context cx) {
		context = cx;
		mCrimes = new ArrayList<Crime>();
//		// Statically generated crimes 
//		for (int i = 0; i < 100; i++) {
//			Crime c = new Crime();
//			c.setTitle("Crime #" + i);
//			c.setIsSolved(i % 2 == 0);
//			mCrimes.add(c);
//		}
	}
	
	public static CrimeLab getInstance(Context cx) {
		if (null == sCrimeLab) {
			sCrimeLab = new CrimeLab(cx.getApplicationContext());
		}
		return sCrimeLab;
	}
	
	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}
	
	public Crime getCrime(UUID crimeID) {
		for(Crime cr: mCrimes) {
			if (cr.getId().equals(crimeID)) {
				return cr;
			}
		}
		return null;
	}
	
	public void addCrime(Crime cr) {
		mCrimes.add(cr);
	}

}
