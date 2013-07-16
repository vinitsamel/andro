package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CrimeFragment extends Fragment {
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private Button mSuspectButton;
	private CheckBox mSolvedChkBx;
	public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.crimeintent.crime_id";
	private static final String DIALOG_DATE = "date";
	private static final int DATE_REQ_CODE = 0;
	private static final int PHOTO_REQ_CODE = 1;
	private static final int CONTACT_REQ_CODE = 2;
	private ImageButton mPhotoButton;
	private ImageView mPhotoView;
	private Callbacks mCallbacks;
	
	public interface Callbacks {
		void onCrimeUpdated(Crime crime);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallbacks = (Callbacks) activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	private static final String TAG = "CrimeFragment";
	private static final String DIALOG_IMAGE = "image";

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
				mCallbacks.onCrimeUpdated(mCrime);
				getActivity().setTitle(mCrime.getTitle());
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
				mCallbacks.onCrimeUpdated(mCrime);
			}
		});
		
		mPhotoButton = (ImageButton) view.findViewById(R.id.crime_imageButton);
		mPhotoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
				startActivityForResult(i, PHOTO_REQ_CODE);
				
			}
		});
		
		mPhotoView = (ImageView) view.findViewById(R.id.crime_imageView);
		mPhotoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Photo p = mCrime.getPhoto();
				if (p == null) return;
				FragmentManager fm = getActivity().getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(p.getFileName()).getAbsolutePath();
				
				ImageFragment.newInstance(path).show(fm, DIALOG_IMAGE);				
			}
		});
		
		Button reportButton = (Button) view.findViewById(R.id.crime_reportButton);
		reportButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
				i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
				i = Intent.createChooser(i, getString(R.string.send_report));
				startActivity(i);
			}
		});
		
		mSuspectButton = (Button) view.findViewById(R.id.crime_suspectButton);
		mSuspectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(i, CONTACT_REQ_CODE);
			}
		});
		
		if (mCrime.getSuspect() != null) {
			mSuspectButton.setText(mCrime.getSuspect());
		}
		
		PackageManager pm = getActivity().getPackageManager();
		if ((!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) && (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT))) {
			mPhotoButton.setEnabled(false);
		}
		
		return view;
	}
	
	
	@Override
	public void onActivityResult(int reqCode, int resCode, Intent data) {
		if (resCode != Activity.RESULT_OK) return;
		if (reqCode == DATE_REQ_CODE) {
			Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setDate(date);
			mDateButton.setText(mCrime.getDate().toString());
			mCallbacks.onCrimeUpdated(mCrime);
		}
		else if (reqCode == PHOTO_REQ_CODE) {
			String fileName = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
			if (fileName != null) {
				Photo p = new Photo(fileName);
				mCrime.setPhoto(p);
				mCallbacks.onCrimeUpdated(mCrime);
				showPhoto();
				Log.i(TAG, "Photo added: " + fileName + " to Crime: " + mCrime.getTitle());
			}
		}
		else if (reqCode == CONTACT_REQ_CODE){
			Uri contactUri = data.getData();
			
			String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME };
			
			Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);
			
			if (c.getCount() == 0) {
				c.close();
				return;
			}
			
			c.moveToFirst();
			String suspect = c.getString(0);
			mCrime.setSuspect(suspect);
			mCallbacks.onCrimeUpdated(mCrime);
			mSuspectButton.setText(suspect);
			c.close();
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
	
	@Override
	public void onPause() {
		super.onPause();
		CrimeLab.getInstance(getActivity()).saveCrimes();
	}
	
	private void showPhoto() {
		Photo p = mCrime.getPhoto();
		BitmapDrawable b = null;
		if (p != null) {
			String path = getActivity().getFileStreamPath(p.getFileName()).getAbsolutePath();
			b= PictureUtils.getScaledDrawable(getActivity(), path);
		}
		mPhotoView.setImageDrawable(b);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		showPhoto();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		PictureUtils.cleanImageView(mPhotoView);
	}
	
	private String getCrimeReport() {
		String solvedString = null;
		if (mCrime.isSolved()) {
			solvedString = getString(R.string.crime_report_solved);
		}
		else {
			solvedString = getString(R.string.crime_report_unsolved);			
		}
		
		String dateFormat = "EEE, MMM dd";
		String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();
		
		String suspect = mCrime.getSuspect();
		if (suspect == null) {
			suspect = getString(R.string.crime_report_nosuspect);
		}
		else {
			suspect = getString(R.string.crime_report_suspect);
		}
		
		String report = getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);
		
		return report;
	}
	
	

}
