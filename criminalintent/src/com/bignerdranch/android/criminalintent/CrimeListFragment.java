package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {
	private ArrayList<Crime> mCrimes;
	private static final String TAG = "CrimeListFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.getInstance(getActivity()).getCrimes();
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);

	}
	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
//		return getListView().setEmptyView(getListView().findViewById(R.id.empty_vw));
//	}
	
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//Crime c = (Crime)(getListAdapter().getItem(position));
		Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
		Log.d(TAG, c.getTitle() + " was clicked.");
		
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		startActivity(i);
	}

	
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			}
			
			Crime c = getItem(position);
			
			TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			
			TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(c.getDate().toString());
			
			CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			
			return convertView;
			
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_crime:
			Crime cr = new Crime();
			CrimeLab.getInstance(getActivity()).addCrime(cr);
			Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
			intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, cr.getId());
			startActivityForResult(intent, 0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
