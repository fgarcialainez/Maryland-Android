package com.fgarcialainez.dailyselfie;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

public class SelfiesActivity extends Activity 
{
	private PendingIntent pendingIntent;
	private SelfiesFragment mSelfiesFragment;
	
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selfies_activity);
		
		mSelfiesFragment = new SelfiesFragment();
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, mSelfiesFragment).commit();
		}
		
		// Setup alarm
        setupAlarm();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selfies_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_new_selfie) {
			dispatchTakePictureIntent();
			return true;
		}
		else if (id == R.id.action_delete_all_selfies) {
			deleteAllSelfies();
			return true;
		}
		else if (id == R.id.action_delete_selfie) {
			deleteSelfie();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        
	        String selfieName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());	        
	        SelfieRecord newSelfie = new SelfieRecord(selfieName, null);
	        newSelfie.setPictureBitmap(SelfiesPictureHelper.getTempBitmap(this));
	        
	        mSelfiesFragment.addSelfie(newSelfie);
	    }
	}
	
	/**
	 * Setup a repeating alarm with an interval of two minutes
	 */
	private void setupAlarm() {
	
		// Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, SelfiesAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        
		// Setup a repeating alarm each two minutes
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 120000; //Two minutes

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pendingIntent);
	}
	
	/**
	 * Action Methods
	 */
	
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SelfiesPictureHelper.getTempFile(this))); 
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	private void deleteAllSelfies() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.dialog_delete_all_selfies));
		builder.setPositiveButton(getString(R.string.dialog_yes), dialogDeleteAllSelfiesClickListener);
		builder.setNegativeButton(getString(R.string.dialog_no), dialogDeleteAllSelfiesClickListener).show();
	}
	
	private void deleteSelfie() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.dialog_delete_selfie));
		builder.setPositiveButton(getString(R.string.dialog_yes), dialogDeleteSelfieClickListener);
		builder.setNegativeButton(getString(R.string.dialog_no), dialogDeleteSelfieClickListener).show();
	}
	
	/**
	 * Listeners
	 */
	
	DialogInterface.OnClickListener dialogDeleteAllSelfiesClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	            //Yes button clicked
	        	mSelfiesFragment.deleteAllSelfies();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	            break;
	        }
	    }
	};
	
	DialogInterface.OnClickListener dialogDeleteSelfieClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	            //Yes button clicked
	        	mSelfiesFragment.deleteSelectedSelfie();
	        	
	        	getFragmentManager().popBackStack();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	            break;
	        }
	    }
	};
}