package com.uoft.designProject.FindIt;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.FindIt.engine.data.LocationData;
import com.FindIt.engine.database.LocationDB;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MapLocation extends MapActivity
{
	private MapController mapController;
	private MapView mapView;
	private LocationDB loc;
	private LocationData ld;
	private GeoPoint gp;
	private String tag = "MAPLOCATION";
	
	private TextView notify;
	
	public void onCreate(Bundle bundle) 
	{
		super.onCreate(bundle);
		setContentView(R.layout.location); // bind the layout to the activity
		loc = new LocationDB(this);
		
		notify = (TextView) findViewById(R.id.txtView_mapNotify);
		
		mapView = (MapView) findViewById(R.id.mapview_filocation);
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);
		mapController = mapView.getController();
		mapController.setZoom(14);
		
		ld = loc.getLocationInfo();
		if(ld != null)
		{			
			int lat = (int)(Double.valueOf(ld.getLatitude()) * 1E6);
			int lon = (int)(Double.valueOf(ld.getLongitude()) * 1E6);
			notify.setText("This is the last known location as of: " + ld.getDateTime());
			Log.d(tag, "Latitude is: " + lat + ", Longitude is: " + lon);			
			
			gp = new GeoPoint(lat, lon);
			mapController.animateTo(gp);
			
		}
	}
	
	
	@Override
	protected boolean isRouteDisplayed() 
	{
		return false;
	}

}
