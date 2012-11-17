package com.FindIt.engine.features;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.widget.Toast;

public class Location extends Activity
{
	private LocationManager lm;
	private Context context;
	private FILocationService locationListener;
		
	public Location(Context context)
	{
		this.context = context;
	}
	
	public boolean initLocate() 
	{
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new FILocationService(context);
		
		//TODO: remove, debugging
		Toast.makeText(context, "LOCATE Command", Toast.LENGTH_LONG).show();
		
		lm.requestLocationUpdates(
	            LocationManager.GPS_PROVIDER, 
	            0, 
	            0, 
	            locationListener);
		
		return true;
	}
	
}
