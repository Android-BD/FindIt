package com.FindIt.engine.database;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.FindIt.engine.data.LocationData;

public class LocationDB 
{
	private final Context dbContext; 
    private DBHelper helper;
    private SQLiteDatabase db;
	
	public static final String KEY_ID = "id";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_DATE_TIME = "date_time";
		
	private static final String TAG = "LocationDB";
    private static final String DATABASE_TABLE = "location";
    private static final int DATABASE_VERSION = 3;
    
    private static final String DATABASE_CREATE =
        "create table location(id integer primary key autoincrement, "
        + "latitude text not null, " +
        		"longitude text not null, " 
        + "date_time text);";
        
    public LocationDB(Context context) 
    {
        this.dbContext = context;
        helper = new DBHelper(dbContext, "location", null, DATABASE_VERSION);
        helper.setCreateQuery(DATABASE_CREATE);
        helper.setTableName(DATABASE_TABLE);
    }
    
    public LocationDB open() throws SQLException 
    {
        db = helper.getWritableDatabase();
        return this;
    }

    public void close() 
    {
        helper.close();
    }
    
    public long insertLocation(LocationData loc) 
    {
        this.open();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Date date = new Date();
    	
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_LATITUDE, loc.getLatitude());
        initialValues.put(KEY_LONGITUDE, loc.getLongitude());
        initialValues.put(KEY_DATE_TIME, dateFormat.format(date));
        
        long om = db.insert(DATABASE_TABLE, null, initialValues);
        
        //db.execSQL("update location set date_time = datetime('now') where " +
        	//	"latitude='"+latitude+"' and longitude='"+longitude+"';");
        
        this.close();
        Log.d(TAG, "inserted user with id = " + new Integer((int) om).toString());
        return om;
    }
    
    public int deleteEntry(LocationData loc) 
    {
    	this.open();
    	int ret = db.delete(DATABASE_TABLE, KEY_LATITUDE + "='" + loc.getLatitude() + 
    			"' and " + KEY_LONGITUDE + "='" + loc.getLongitude() +"'", null);
    	this.close();
    	Log.d(TAG, "Deleted = " + new Integer((int) ret).toString());
    	return ret;
    }
    
    public boolean updateLocation(LocationData loc, LocationData oloc) 
    {
    	this.open();
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Date date = new Date();
    	
    	ContentValues args = new ContentValues();
    	args.put(KEY_LATITUDE, loc.getLatitude());
    	args.put(KEY_LONGITUDE, loc.getLongitude());
    	args.put(KEY_DATE_TIME, dateFormat.format(date));
    	    	
    	int x =  db.update(DATABASE_TABLE, args, KEY_LATITUDE + "= '"+oloc.getLatitude()
    			+"' and "+ KEY_LONGITUDE + "='"+oloc.getLongitude() + "'", null);
    	
    	boolean updated = false;
    	
    	if(x> 0)
    		updated = true;
    	
    	Log.d(TAG, "Entry Successfully Updated " + new Boolean(updated).toString());
    	Log.d(TAG, "Entry Successfully Updated " + new Integer((int)x).toString());
    	
    	this.close();
    	
    	return updated;
    }
    
    private Cursor getLocation() throws SQLException 
    {
        this.open();
    	
        String[] query = new String[] {KEY_ID,KEY_LATITUDE, KEY_LONGITUDE, 
        		KEY_DATE_TIME};
        
    	Cursor entry = db.query(DATABASE_TABLE, query, null, null, null, null, null);
        
    	if (entry == null || entry.getCount() == 0) 
    		entry.close();
    	else
    		entry.moveToFirst();
    	
    	this.close();
    	
        return entry;
    }
    
    public LocationData getLocationInfo()
    {
    	LocationData loc = new LocationData();
    	
    	Cursor curs = getLocation();
    	
    	if(curs.isClosed())
    		return null;
    	
    	loc.setLatitude(curs.getString(curs.getColumnIndex(KEY_LATITUDE)));
    	loc.setLongitude(curs.getString(curs.getColumnIndex(KEY_LONGITUDE)));
    	loc.setDateTime(curs.getString(curs.getColumnIndex(KEY_DATE_TIME)));
    	
    	curs.close();
    	
    	return loc;
    }
    
}
