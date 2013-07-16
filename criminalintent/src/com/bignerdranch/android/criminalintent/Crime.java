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
	private Photo mPhoto;
	private String mSuspect;
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_DATE = "date";
	private static final String JSON_IS_SOLVED = "isSolved";
	private static final String JSON_PHOTO = "photo";
	private static final String JSON_SUSPECT = "suspect";
	
	public Crime() {
		mId = UUID.randomUUID();
		mDate = new Date();
		mPhoto = null;
	}
	
	public Crime (JSONObject jsonObj) throws JSONException {
		mId = UUID.fromString(jsonObj.getString(JSON_ID));
		mTitle = jsonObj.getString(JSON_TITLE);
		mIsSolved = jsonObj.getBoolean(JSON_IS_SOLVED);
		mDate = new Date(jsonObj.getLong(JSON_DATE));
		if (jsonObj.has(JSON_PHOTO)) mPhoto = new Photo(jsonObj.getJSONObject(JSON_PHOTO));
		if (jsonObj.has(JSON_PHOTO)) mSuspect = jsonObj.getString(JSON_SUSPECT);
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
		if (mPhoto != null) js.put(JSON_PHOTO, this.mPhoto.toJSON());
		js.put(JSON_SUSPECT, this.mSuspect);
		return js;
	}
	
	public Photo getPhoto() {
		return mPhoto;
	}
	
	public void setPhoto(Photo p) {
		mPhoto = p;
	}
	
	public String getSuspect() {
		return mSuspect;
	}
	
	public void setSuspect(String s){
		mSuspect = s;
	}
}
