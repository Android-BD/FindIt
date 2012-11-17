package com.uoft.designProject.FindIt;

import java.util.HashMap;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.FindIt.engine.features.FILocationService;
import com.FindIt.engine.features.Page;
import com.FindIt.engine.widgets.CustomListAdapter;

public class Menu extends Activity
{
	static final int RESULT_ENABLE = 1;

    static DevicePolicyManager mDPM;
    static ActivityManager mAM;
    static ComponentName mDeviceAdmin;
	
	//private static final String TAG = "menu_activity";
	private String[] items;
	private ListView menuList;
	private HashMap<String, Integer> itemMap = new HashMap<String, Integer>();
	private CustomListAdapter listAdapter;
	private TextView notification;
	//private String TAG = "menu_activity";
	private Intent menuIntent;
	
	private LocationManager lm;
	private Context context;
	private FILocationService locationListener;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        mAM = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        mDeviceAdmin= new ComponentName(Menu.this, DeviceAdmin.class);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_list);
				
		notification = (TextView)findViewById(R.id.txtView_menuNotification);
		Bundle menuBundle = this.getIntent().getExtras();
			
		//initiate location service
		context = this.getApplicationContext();
		initLocate();
		
		if(menuBundle != null)
		{
			if(menuBundle.getBoolean("firstLogin"))
			{
				menuIntent = new Intent(Menu.this, Settings.class);
				menuIntent.putExtra("firstLogin", true);
				Menu.this.startActivity(menuIntent);
			}
			if(menuBundle.containsKey("settings.update"))
			{
				notification.setText(menuBundle.getString("settings.update"));
			}
		}
		
		// Show "Disable Paging" button if paging is initialized
		if (Page.isLockMode()) {
			items = new String[]{"About", "Find Me", "Disable Paging", 
					"Lock Phone", "Wipe Data", "Enable Device Administration", "Settings"};	
			itemMap.put("Disable Paging", new Integer(R.drawable.ic_nospeaker));
		} else {
			items = new String[]{"About", "Find Me", "Disable Paging", 
					"Lock Phone", "Wipe Data", "Enable Device Administration", "Settings"};	
		}

		itemMap.put("About", new Integer(R.drawable.ic_list_fi2));
		itemMap.put("Find Me", new Integer(R.drawable.ic_list_map));
		//itemMap.put("Auto Reply", new Integer(R.drawable.ic_list_sms));
		itemMap.put("Lock Phone", new Integer(R.drawable.ic_list_lock));
		//itemMap.put("Dropbox Account", new Integer(R.drawable.ic_list_account));
		itemMap.put("Page", new Integer(R.drawable.ic_list_sound));
		itemMap.put("Disable Paging", new Integer(R.drawable.ic_list_sound));
		itemMap.put("Wipe Data", new Integer(R.drawable.ic_list_empty));
		itemMap.put("Settings", new Integer(R.drawable.ic_list_settings));
		itemMap.put("Enable Device Administration", new Integer(R.drawable.ic_list_enable));
		
		menuList = (ListView)findViewById(R.id.listView_menu);
		listAdapter = new CustomListAdapter(this, R.layout.list_row, items, itemMap, 
				"menuList");
		
		menuList.setAdapter(listAdapter);
		menuList.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{								
				if(items[arg2] == "About")
				{
					menuIntent = new Intent(Menu.this, About.class);
					Menu.this.startActivity(menuIntent);
				}				
				if(items[arg2] == "Settings")
				{
					menuIntent = new Intent(Menu.this, Settings.class);
					Menu.this.startActivity(menuIntent);
				}
				if(items[arg2] == "Find Me")
				{
					menuIntent = new Intent(Menu.this, MapLocation.class);
					Menu.this.startActivity(menuIntent);
				}
				if(items[arg2] == "Disable Paging")
				{
					//TODO: authentication mechanism
					if(Page.isLockMode())
					{
						Page.disableLockMode();
						notification.setText("Paging Disabled");
					}
					else
					{
						notification.setText("Paging is NOT Activated");
					}
					return;
				}
				if(items[arg2] == "Enable Device Administration")
				{
					boolean active = mDPM.isAdminActive(mDeviceAdmin);
	                if (active) 
	                {
	                    notification.setText("Device Administration is Enabled");
	                    return;
	                }
					Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
	                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
	                        mDeviceAdmin);
	                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
	                        "Additional text explaining why this needs to be added.");
	                startActivityForResult(intent, RESULT_ENABLE);
					//menuIntent = new Intent(Menu.this, DeviceAdmin.Controller.class);
					//Menu.this.startActivity(menuIntent);
				}
				if(items[arg2] == "Lock Phone")
				{
					boolean active = mDPM.isAdminActive(mDeviceAdmin);
	                if (active) 
	                {
	                    mDPM.lockNow();
	                }
	                else if(!active)
	                {
	                	notification.setText("Action Failed, Device Administration is NOT Enabled." +
	                			"Please Enable Device Administration");
	                	notification.setTextColor(Color.RED);
	                	
	                    return;
	                }
					
					//menuIntent = new Intent(Menu.this, LockPhone.class);
					//Menu.this.startActivity(menuIntent);
				}
				if(items[arg2] == "Wipe Data")
				{
					boolean active = mDPM.isAdminActive(mDeviceAdmin);
                    if (active) 
                    {
                        mDPM.wipeData(0);
                    }
                    else if(!active)
	                {
	                	notification.setText("Action Failed, Device Administration is NOT Enabled." +
	                			"Please Enable Device Administration");
	                	notification.setTextColor(Color.RED);
	                	
	                    return;
	                }
				}
				/*if(items[arg2] == "Dropbox Account")
				{
					menuIntent = new Intent(Menu.this, Dropbox.class);
					Menu.this.startActivity(menuIntent);
				}*/
			}
		});
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) 
                {
                    Log.i("DeviceAdminSample", "Admin enabled!");
                } 
                else 
                {
                	Log.i("DeviceAdminSample", "Admin enable FAILED!");
                }
                return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
	
	public static DevicePolicyManager getDPM() {
    	return mDPM;
    }
    
    public static ComponentName getDAS() {
    	return mDeviceAdmin;
    }
    
    //initiates location service
    public boolean initLocate() 
	{
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new FILocationService(context);
	
		lm.requestLocationUpdates(
	            LocationManager.GPS_PROVIDER, 
	            0, 
	            0, 
	            locationListener);
		
		return true;
	}
}