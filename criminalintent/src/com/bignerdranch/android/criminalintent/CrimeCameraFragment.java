package com.bignerdranch.android.criminalintent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class CrimeCameraFragment extends Fragment {
	private static final String TAG = "CrimeCameraFragment";
	public static final String EXTRA_PHOTO_FILENAME = 
			"com.bignerdranch.android.criminalintent.photo_filename";
	
	private Camera mCam;
	private SurfaceView mSurfaceView;
	private View mProgressContainer;
	
	private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		
		@Override
		public void onShutter() {
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	};
	
	private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			String filename = UUID.randomUUID().toString() + ".jpg";
			FileOutputStream fos = null;
			boolean success = true;
			try {
				fos = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
				fos.write(data);
			}
			catch (Exception e){
				Log.e(TAG, "Error writing to file: "+ filename, e);
				success = false;
			}
			finally {
				try{
					if (fos != null) fos.close();
				}
				catch (Exception e){
					Log.e(TAG, "Error writing to file: "+ filename, e);
					success = false;
				}
			}
			if (success) {
				Log.i(TAG, "File: " + filename + " successfully saved.");
				Intent i = new Intent();
				i.putExtra(EXTRA_PHOTO_FILENAME, filename);
				getActivity().setResult(Activity.RESULT_OK, i);
			}
			else {
				getActivity().setResult(Activity.RESULT_CANCELED);
			}
			getActivity().finish();
		}
	};
	
	@Override
	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime_camera, parent, false);
		
		Button takePicButton = (Button) v.findViewById(R.id.crime_camera_snapButton);
		takePicButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View vw) {
				//getActivity().finish();
				if (mCam != null) mCam.takePicture(mShutterCallback, null, mPictureCallback);
				
			}
		});
		
		mSurfaceView = (SurfaceView) v.findViewById(R.id.crime_camera_surfaceView);
		SurfaceHolder surfHolder = mSurfaceView.getHolder();
		surfHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		surfHolder.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if (mCam != null) mCam.stopPreview();
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					if (mCam != null) {
						mCam.setPreviewDisplay(holder);
					}
				}
				catch (IOException ex) {
					Log.e(TAG, "Error setting up preview display " + ex);
				}
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {
				if (mCam == null) return;
				
				Camera.Parameters camParams = mCam.getParameters();
				Size s = getBestSupportedSize(camParams.getSupportedPreviewSizes(), width, height);
				camParams.setPreviewSize(s.width, s.height);
				s = getBestSupportedSize(camParams.getSupportedPictureSizes(), width, height);
				camParams.setPictureSize(s.width, s.height);
				mCam.setParameters(camParams);
				try {
					mCam.startPreview();
				}
				catch (Exception ex) {
					Log.e(TAG, "Could not start preview ", ex);
					mCam.release();
					mCam = null;
				}
				
			}
		});
		
		mProgressContainer =  v.findViewById(R.id.crime_camera_progressContainer);
		mProgressContainer.setVisibility(View.INVISIBLE);
		
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			mCam = Camera.open(0);
		} 
		else {
			mCam = Camera.open();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if (mCam != null) {
			mCam.release();
			mCam = null;
		}
	}
	
	private Size getBestSupportedSize(List<Size> sizes, int width, int height) {
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		for (Size s : sizes) {
			int area = s.width * s.height;
			if (area > largestArea) {
				bestSize = s;
				largestArea = area;
			}		
		}
		return bestSize;
	}
	
	

}
