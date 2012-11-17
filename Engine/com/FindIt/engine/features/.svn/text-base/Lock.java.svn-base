package com.FindIt.engine.features;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.uoft.designProject.FindIt.Menu;

public class Lock extends Activity {
	private static DevicePolicyManager mDPM;
	private static ComponentName mDeviceAdmin;
	
	static public boolean performLock(Context context) {
		mDPM = Menu.getDPM();
		mDeviceAdmin = Menu.getDAS();
		
		boolean active = mDPM.isAdminActive(mDeviceAdmin);
        if (active) {
            mDPM.lockNow();
        }
        else
        {
        	return false;
        }
		
		return true;
	}
}
