package com.FindIt.engine.features;

import com.FindIt.engine.data.LocationData;
import com.FindIt.engine.database.LocationDB;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class FILocationService implements LocationListener
{
	Context con;
	LocationDB locDB;
	
	public FILocationService(Context con)
	{
		this.con = con;
	}

	public void onLocationChanged(Location location) 
	{
		locDB = new LocationDB(this.con);
		if (location != null) 
		{
            LocationData l = locDB.getLocationInfo();
            if(l != null)
            {
            	if(String.valueOf(location.getLatitude()).equals(l.getLatitude()) &&
            		String.valueOf(location.getLongitude()).equals(l.getLongitude()))
            	{            		
            		locDB.updateLocation(l, l);
            	}
            	else
            	{
            		locDB.deleteEntry(l);
            		l = new LocationData();
            		l.setLatitude(String.valueOf(location.getLatitude()));
            		l.setLongitude(String.valueOf(location.getLongitude()));
            		
            		locDB.insertLocation(l);
            	}
            }
            else
            {
            	l = new LocationData();
        		l.setLatitude(String.valueOf(location.getLatitude()));
        		l.setLongitude(String.valueOf(location.getLongitude()));
        		
        		locDB.insertLocation(l);
            }
            
			Toast.makeText(con, 
                "Location changed : Lat: " + location.getLatitude() + 
                " Lng: " + location.getLongitude(), 
                Toast.LENGTH_SHORT).show();
        }
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
