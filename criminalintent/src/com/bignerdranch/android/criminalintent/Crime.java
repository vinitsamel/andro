package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Crime {
	
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mIsSolved;
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_DATE = "date";
	private static final String JSON_IS_SOLVED = "isSolved";
	
	public Crime() {
		mId = UUID.randomUUID();
		mDate = new Date();
	}
	
	public Crime (JSONObject jsonObj) throws JSONException {
		mId = UUID.fromString(jsonObj.getString(JSON_ID));
		mTitle = jsonObj.getString(JSON_TITLE);
		mIsSolved = jsonObj.getBoolean(JSON_IS_SOLVED);
		mDate = new Date(jsonObj.getLong(JSON_DATE));
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public UUID getId() {
		return mId;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public boolean isSolved() {
		return mIsSolved;
	}

	public void setIsSolved(boolean isSolved) {
		mIsSolved = isSolved;
	}
	
	@Override
	public String toString() {
		return mTitle;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject js = new JSONObject();
		js.put(JSON_ID, this.mId.toString());
		js.put(JSON_TITLE, this.mTitle);
		js.put(JSON_DATE, this.mDate.getTime());
		js.put(JSON_IS_SOLVED, this.mIsSolved);
		return js;
	}
}
