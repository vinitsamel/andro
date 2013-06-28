package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedChkBx;
	public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.crimeintent.crime_id";
	private static final String DIALOG_DATE = "date";
	private static final int DATE_REQ_CODE = 0;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
		
		setHasOptionsMenu(true);
	}
	
	public static CrimeFragment newInstance(UUID uuid) {
		Bundle bun = new Bundle();
		bun.putSerializable(EXTRA_CRIME_ID, uuid);
		
		CrimeFragment cFrag = new CrimeFragment();
		cFrag.setArguments(bun);
		
		return cFrag;
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_crime, parent, false);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
		mTitleField = (EditText) view.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mCrime.setTitle(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub		
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		
		mDateButton = (Button) view.findViewById(R.id.crime_date);
		mDateButton.setText(DateFormat.format("EEEE, MMMM dd, yyyy" ,mCrime.getDate()));
		//mDateButton.setEnabled(false);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				FragmentManager fMgr = getActivity().getSupportFragmentManager();
				DatePickerFragment dateFrag = DatePickerFragment.newInstance(mCrime.getDate());
				dateFrag.setTargetFragment(CrimeFragment.this, DATE_REQ_CODE);
				dateFrag.show(fMgr, DIALOG_DATE);
				
			}
		});
		
		mSolvedChkBx = (CheckBox) view.findViewById(R.id.crime_solved);
		mSolvedChkBx.setChecked(mCrime.isSolved());
		mSolvedChkBx.setOnCheckedChangeListener(new OnCheckedChangeListener() {	
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mCrime.setIsSolved(isChecked);
			}
		});
		
		return view;
	}
	
	
	@Override
	public void onActivityResult(int reqCode, int resCode, Intent data) {
		if (resCode != Activity.RESULT_OK) return;
		if (reqCode == DATE_REQ_CODE) {
			Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setDate(date);
			mDateButton.setText(mCrime.getDate().toString());
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

}
