package com.fgarcialainez.dailyselfie;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SelfiesFragment extends ListFragment 
{
	private SelfieRecord selectedSelfie;
	private SelfiesAdapter mSelfiesAdapter;
	
	public SelfiesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {	
		// Set custom adapter
		setListAdapter(mSelfiesAdapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mSelfiesAdapter = new SelfiesAdapter(getActivity(), null, 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		mSelfiesAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onDestroy () {
		mSelfiesAdapter.freeResources();
		
		super.onDestroy();
	}
	
	@Override  
	public void onListItemClick(ListView l, View v, int position, long id) {  
		//LAUNCH SELFIE DETAILS FRAGMENT  
		selectedSelfie = (SelfieRecord)mSelfiesAdapter.getItem(position);
		
		if(selectedSelfie != null) {
			SelfieDetailsFragment detailsFragment = new SelfieDetailsFragment(selectedSelfie);
			FragmentTransaction transaction = getFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack
			transaction.replace(R.id.container, detailsFragment);
			transaction.addToBackStack(null);

			// Commit the transaction
			transaction.commit();
		}
	}
	
	/**
	 * Public Methods
	 */
	
	public void addSelfie(SelfieRecord newSelfie) {
		if(mSelfiesAdapter != null) {
			mSelfiesAdapter.addSelfie(newSelfie);
		}
	}
	
	public void deleteSelectedSelfie() {
		if(mSelfiesAdapter != null) {
			mSelfiesAdapter.deleteSelfie(selectedSelfie);
		}
	}
	
	public void deleteAllSelfies() {
		if(mSelfiesAdapter != null) {
			mSelfiesAdapter.deleteAllSelfies();
		}
	}
}