/****************SOURCE**************
 * Following tutorial available at online community was used
 * to develop DBHelper and UserSettingsDB:
 * http://www.devx.com/wireless/Article/40842/1954
 */

package com.FindIt.engine.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper 
{
	private String createQuery;
	private String tableName;

	public DBHelper(Context context, String name, CursorFactory factory, int version) 
	{
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		if(createQuery != "")
		{
			db.execSQL(createQuery);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		if(createQuery != "" || tableName == "")
		{
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
			onCreate(db);
		}
	}
	
	void setCreateQuery(String query)
	{
		createQuery = query;
	}
	
	void setTableName(String name)
	{
		tableName = name;
	}
}
