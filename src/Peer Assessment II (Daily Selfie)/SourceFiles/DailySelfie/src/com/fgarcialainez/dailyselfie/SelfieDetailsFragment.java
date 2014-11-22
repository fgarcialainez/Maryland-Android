package com.fgarcialainez.dailyselfie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SelfieDetailsFragment extends Fragment
{
	private SelfieRecord mSelfie;
	
	public SelfieDetailsFragment(SelfieRecord selfie) {
		mSelfie = selfie;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {	
		// Display options menu
		setHasOptionsMenu(true);
		
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.selfie_details_fragment, container, false);
		
		ImageView imageView = (ImageView)view.findViewById(R.id.selfie_details_picture_image_view);
		imageView.setImageBitmap(SelfiesPictureHelper.getScaledBitmap(mSelfie.getPicturePath(), 0, 0));
		
		// Set title bar
	    getActivity().getActionBar().setTitle(mSelfie.getName());
		
        return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Remove items of the SelfiesFragment 
		menu.clear();
		
		// Inflate selfie details mnu
		inflater.inflate(R.menu.selfie_details_menu, menu);
	}
}