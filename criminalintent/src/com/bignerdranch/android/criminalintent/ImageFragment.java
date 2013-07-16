package com.bignerdranch.android.criminalintent;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends DialogFragment {
	public static final String EXTRA_IMAGE_PATH = "com.bignerdranch.android.criminalintent.image_path";
	
	private ImageView imgView;
	
	public static ImageFragment newInstance(String imagePath) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_IMAGE_PATH, imagePath);
		
		ImageFragment imgF = new ImageFragment();
		imgF.setArguments(args);
		imgF.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		
		return imgF;
	}
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup vg, Bundle savedInstanceState) {
		imgView = new ImageView(getActivity());
		String path = (String) getArguments().getSerializable(EXTRA_IMAGE_PATH);
		BitmapDrawable image = PictureUtils.getScaledDrawable(getActivity(), path);
		
		imgView.setImageDrawable(image);
		
		return imgView;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		PictureUtils.cleanImageView(imgView);
	}

}
