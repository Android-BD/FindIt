/****************SOURCE**************
 * Following tutorial available at online community was used
 * to develop DBHelper and UserSettingsDB:
 * http://www.devx.com/wireless/Article/40842/1954
 */
package com.FindIt.engine.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.FindIt.engine.data.User;

/**
 * @author Aditya
 *
 */
public class UserSettingsDB 
{
	private final Context dbContext; 
    private DBHelper helper;
    private SQLiteDatabase db;
	
	public static final String KEY_ID = "id";
	public static final String KEY_NUMBER = "phone_number";
	public static final String KEY_IMEI = "phone_imei";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_HINT = "password_hint";
	public static final String KEY_OWNER = "owner_name";
	public static final String KEY_ALTNUM = "alternate_number";
	
	private static final String TAG = "UserSettingsDB";
    private static final String DATABASE_TABLE = "user_settings";
    private static final int DATABASE_VERSION = 5;

    private static final String DATABASE_CREATE =
        "create table user_settings (id integer primary key autoincrement, "
        + "phone_number text unique, " +
        		"phone_imei text not null, " 
        + "password text not null, " +
        		"password_hint text not null, " +
        		"owner_name text, " +
        		"alternate_number text);";
        
    public UserSettingsDB(Context context) 
    {
        this.dbContext = context;
        helper = new DBHelper(dbContext, "user_info", null, DATABASE_VERSION);
        helper.setCreateQuery(DATABASE_CREATE);
        helper.setTableName(DATABASE_TABLE);
    }
    
    public UserSettingsDB open() throws SQLException 
    {
        db = helper.getWritableDatabase();
        return this;
    }

    public void close() 
    {
        helper.close();
    }
    
    public long insertInfo(User userObj) 
    {
        this.open();
    	
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NUMBER, userObj.getNumber());
        initialValues.put(KEY_IMEI, userObj.getIMEI());
        initialValues.put(KEY_PASSWORD, userObj.getEnPswd());
        initialValues.put(KEY_HINT, userObj.getEnPswdHint());
        initialValues.put(KEY_OWNER, userObj.getOwnerName());
        initialValues.put(KEY_ALTNUM, userObj.getAlternateNumber());
        long om = db.insert(DATABASE_TABLE, null, initialValues);
        
        this.close();
        Log.d(TAG, "inserted user with id = " + new Integer((int) om).toString());
        return om;
    }
    
    public boolean updateInfo(User uObj, String old_number) 
    {
    	this.open();
    	
    	ContentValues args = new ContentValues();
    	args.put(KEY_NUMBER, uObj.getNumber());
    	args.put(KEY_IMEI, uObj.getIMEI());
    	args.put(KEY_PASSWORD, uObj.getEnPswd());
    	args.put(KEY_HINT, uObj.getEnPswdHint());
    	args.put(KEY_OWNER, uObj.getOwnerName());
    	args.put(KEY_ALTNUM, uObj.getAlternateNumber());
    	
    	int x =  db.update(DATABASE_TABLE, args, KEY_NUMBER + "= '" + old_number + "'", null);
    	boolean updated = false;
    	
    	if(x> 0)
    		updated = true;
    	
    	Log.d(TAG, "Entry Successfully Updated " + new Boolean(updated).toString());
    	Log.d(TAG, "Entry Successfully Updated " + new Integer((int)x).toString());
    	
    	this.close();
    	
    	return updated;
    }
    
    private Cursor getUserEntry() throws SQLException 
    {
        this.open();
    	
        String[] query = new String[] {KEY_ID,KEY_NUMBER, KEY_IMEI, KEY_PASSWORD, KEY_HINT, 
        		KEY_OWNER, KEY_ALTNUM};
        
    	Cursor entry = db.query(DATABASE_TABLE, query, null, null, null, null, null);
        
    	if (entry == null || entry.getCount() == 0) 
    		entry.close();
    	else
    		entry.moveToFirst();
    	
    	this.close();
    	
        return entry;
    } 
    
    public int deleteEntry(String phone_number) 
    {
    	this.open();
    	int ret = db.delete(DATABASE_TABLE, KEY_NUMBER + "=" + phone_number, null);
    	this.close();
    	
    	return ret;
    }
    
    public User getUserInfo()
    {
    	User userObj = new User();
    	
    	Cursor curs = getUserEntry();
    	
    	if(curs.isClosed())
    		return null;
    	
    	userObj.setNumber(curs.getString(curs.getColumnIndex(KEY_NUMBER)));
    	userObj.setIMEI(curs.getString(curs.getColumnIndex(KEY_IMEI)));
    	userObj.setEnPswd(curs.getString(curs.getColumnIndex(KEY_PASSWORD)));
    	userObj.setEnPswdHint(curs.getString(curs.getColumnIndex(KEY_HINT)));
    	userObj.setOwnerName(curs.getString(curs.getColumnIndex(KEY_OWNER)));
    	userObj.setAlternateNumber(curs.getString(curs.getColumnIndex(KEY_ALTNUM)));
    	
    	curs.close();
    	
    	return userObj;
    }
}
