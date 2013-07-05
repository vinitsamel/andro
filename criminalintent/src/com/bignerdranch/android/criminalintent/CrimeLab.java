package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.widget.Toast;

public class CrimeLab {
	private static CrimeLab sCrimeLab;
	private Context context;
	private ArrayList<Crime> mCrimes;
	
	private static final String JSONFILENAME = "crimes.json";
	private CriminalIntentJSONSerializer crimeJSONSerzer;
	
	private CrimeLab(Context cx) {
		context = cx;
//		// Statically generated crimes 
//		for (int i = 0; i < 100; i++) {
//			Crime c = new Crime();
//			c.setTitle("Crime #" + i);
//			c.setIsSolved(i % 2 == 0);
//			mCrimes.add(c);
//		}
		crimeJSONSerzer = new CriminalIntentJSONSerializer(cx, JSONFILENAME);
		try {
			mCrimes = crimeJSONSerzer.loadCrimes();
		}
		catch (Exception ex) {
			mCrimes = new ArrayList<Crime>();
			//Toast.makeText(cx, "Failed to Load Existing Crimes...", Toast.LENGTH_SHORT).show();
		}
		//Toast.makeText(cx, "Existing crimes have been loaded...", Toast.LENGTH_SHORT).show();
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
	
	public boolean saveCrimes() {
		try {
			Toast.makeText(context, "Saving Crimes...", Toast.LENGTH_SHORT).show();
			crimeJSONSerzer.saveCrimes(mCrimes);
			Toast.makeText(context, "Crimes Saved!", Toast.LENGTH_SHORT).show();
			return true;
		}
		catch (Exception e) {
			Toast.makeText(context, "Fail! Could not Save Crimes!!", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	public void deleteCrime(Crime c) {
		mCrimes.remove(c);
	}

}
