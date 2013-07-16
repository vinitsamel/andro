package com.bignerdranch.android.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo {
	private String fileName;
	private static final String JSON_FILENAME = "fileName";
	
	public Photo(String filName) {
		fileName = filName;
	}
	
	public Photo(JSONObject jsonOb) throws JSONException{
		fileName = jsonOb.getString(JSON_FILENAME);
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject js = new JSONObject();
		js.put(JSON_FILENAME, fileName);
		return js;
	}
	
	public String getFileName() {
		return fileName;
	}
}
