package com.fgarcialainez.modernartui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_more_information)
		{
			//Show dialog
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	        alertDialog.setMessage(getString(R.string.dialog_message));  
	        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.dialog_button_not_now), new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {     
		        }
		    });
	        
	        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.dialog_button_visit_moma), new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {  
	        		//Launch browser
	        		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moma.org"));
	        		startActivity(browserIntent);
	        	}
	        });
	       
	        alertDialog.show();
	        
	        //Center text message in dialog
	        TextView textView = ((TextView) alertDialog.findViewById(android.R.id.message));  
	        
	        if(textView != null)
	        	textView.setGravity(Gravity.CENTER);  
	        
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment 
	{
		private int firstNonWhiteGrayColor;
		private int secondNonWhiteGrayColor;
		private int thirdNonWhiteGrayColor;
		private int fourthNonWhiteGrayColor;
		
		private View firstNonWhiteGrayView;
		private View secondNonWhiteGrayView;
		private View thirdNonWhiteGrayView;
		private View fourthNonWhiteGrayView;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,	false);
			
			//Get colors
			firstNonWhiteGrayColor = getResources().getColor(R.color.first_non_white_gray_view_color);
			secondNonWhiteGrayColor = getResources().getColor(R.color.second_non_white_gray_view_color);
			thirdNonWhiteGrayColor = getResources().getColor(R.color.third_non_white_gray_view_color);
			fourthNonWhiteGrayColor = getResources().getColor(R.color.fourth_non_white_gray_view_color);
			
			//Get references to UI elements
			firstNonWhiteGrayView = (View)rootView.findViewById(R.id.first_non_white_gray_view);
			secondNonWhiteGrayView = (View)rootView.findViewById(R.id.second_non_white_gray_view);
			thirdNonWhiteGrayView = (View)rootView.findViewById(R.id.third_non_white_gray_view);
			fourthNonWhiteGrayView = (View)rootView.findViewById(R.id.fourth_non_white_gray_view);
			
			final SeekBar seekBar = (SeekBar)rootView.findViewById(R.id.seek_bar);
			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
			{
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					// TODO Auto-generated method stub
					updateColors(1 - ((float)progress / 100));
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}
			});
			
			return rootView;
		}
		
		private void updateColors(float percentage)
		{
			firstNonWhiteGrayView.setBackgroundColor(getUpdatedColor(firstNonWhiteGrayColor, percentage));
			secondNonWhiteGrayView.setBackgroundColor(getUpdatedColor(secondNonWhiteGrayColor, percentage));
			thirdNonWhiteGrayView.setBackgroundColor(getUpdatedColor(thirdNonWhiteGrayColor, percentage));
			fourthNonWhiteGrayView.setBackgroundColor(getUpdatedColor(fourthNonWhiteGrayColor, percentage));
		}
		
		private int getUpdatedColor(int color, float percentage)
		{
			//Modify red and blue components depending on the current progress
			int red = (int)((float)Color.red(color) * percentage);
	        int green = Color.green(color);
	        int blue = (int)((float)Color.blue(color) * percentage);
	        int alpha = Color.alpha(color);
	        
	        return Color.argb(alpha, red, green, blue);
		}
	}
}
