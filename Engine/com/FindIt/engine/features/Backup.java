package com.FindIt.engine.features;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Backup extends Activity {
	static public boolean performBackup(Context context) {
		
		//TODO: remove, debugging
		Toast.makeText(context, "BACKUP Command", Toast.LENGTH_LONG).show();
		
		return true;
	}
}
