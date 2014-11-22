package com.fgarcialainez.dailyselfie;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

public class SelfiesPictureHelper
{
	private static final String APP_DIR = "dailyselfie/pictures";
	
	public static String getBitmapStoragePath(Context context)
	{
		String bitmapStoragePath = "";
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				String root = context.getExternalFilesDir(null).getCanonicalPath();
				if (null != root) {
					File bitmapStorageDir = new File(root, APP_DIR);
					bitmapStorageDir.mkdirs();
					bitmapStoragePath = bitmapStorageDir.getCanonicalPath();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return bitmapStoragePath;
	}

	public static boolean storeBitmapToFile(Bitmap bitmap, String filePath) 
	{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
				bitmap.compress(CompressFormat.PNG, 50, bos);
				bos.flush();
				bos.close();
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		
		return false;
	}
	
	public static Bitmap getScaledBitmap(String picturePath, int width, int height)
	{
		// Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(picturePath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;
	
	    // Determine how much to scale down the image
	    int scaleFactor;
	    
	    if(width > 0 && height > 0) {
	    	scaleFactor = Math.min(photoW/width, photoH/height);
	    }
	    else {
	    	scaleFactor = 1;
	    }
	
	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;
	
	    return BitmapFactory.decodeFile(picturePath, bmOptions);
	}
	
	public static File getTempFile(Context context) {
		//It will return /sdcard/image.tmp
		final File path = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
		if(!path.exists()){
			path.mkdir();
		}
		return new File(path, "image.tmp");
	}
	
	public static Bitmap getTempBitmap(Context context) {
		Bitmap capturedBitmap = null;
		final File file = getTempFile(context);
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4;

			AssetFileDescriptor fileDescriptor = context.getContentResolver().openAssetFileDescriptor(Uri.fromFile(file), "r");
			capturedBitmap  = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		return capturedBitmap;
	}
}
