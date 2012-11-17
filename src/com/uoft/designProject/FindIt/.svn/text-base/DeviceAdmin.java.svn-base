/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uoft.designProject.FindIt;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Example of a do-nothing admin class.  When enabled, it lets you control
 * some of its policy and reports when there is interesting activity.
 */
public class DeviceAdmin extends DeviceAdminReceiver {

    static SharedPreferences getSamplePreferences(Context context) {
        return context.getSharedPreferences(DeviceAdminReceiver.class.getName(), 0);
    }

    
    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: enabled");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "This is an optional message to warn the user about disabling.";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: disabled");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: pw changed");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: pw failed");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: pw succeeded");
    }

    /**
     * <p>UI control for the sample device admin.  This provides an interface
     * to enable, disable, and perform other operations with it to see
     * their effect.</p>
     *
     * <p>Note that this is implemented as an inner class only keep the sample
     * all together; typically this code would appear in some separate class.
     */
    public static class Controller extends Activity {
        static final int RESULT_ENABLE = 1;

        static DevicePolicyManager mDPM;
        static ActivityManager mAM;
        static ComponentName mDeviceAdmin;

        Button mEnableButton;
        Button mForceLockButton;
        Button mWipeDataButton;
        
        TextView mEnableMsg;
        
        OnClickListener mForceLockListener = new OnClickListener() 
        {
            public void onClick(View v) 
            {
                boolean active = mDPM.isAdminActive(mDeviceAdmin);
                if (active) 
                {
                    mDPM.lockNow();
                }
                else if(!active)
                {
                	AlertDialog.Builder builder = new AlertDialog.Builder(Controller.this);
                    builder.setMessage("Action Failed, Device Administration is NOT Enabled.");
                    builder.setPositiveButton("Please Enable Device Administration", null);
                    builder.show();
                    return;
                }
            }
        };
        
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case RESULT_ENABLE:
                    if (resultCode == Activity.RESULT_OK) 
                    {
                        mEnableButton.setVisibility(8);
                        mEnableMsg.setVisibility(0);
                    	Log.i("DeviceAdminSample", "Admin enabled!");
                    } 
                    else 
                    {
                    	mEnableButton.setVisibility(0);
                        mEnableMsg.setVisibility(8);
                        Log.i("DeviceAdminSample", "Admin enable FAILED!");
                    }
                    return;
            }

            super.onActivityResult(requestCode, resultCode, data);
        }

        private OnClickListener mEnableListener = new OnClickListener() {
            public void onClick(View v) {
                // Launch the activity to have the user enable our admin.
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                        mDeviceAdmin);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "Additional text explaining why this needs to be added.");
                startActivityForResult(intent, RESULT_ENABLE);
            }
        };
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
            mAM = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            mDeviceAdmin= new ComponentName(Controller.this, DeviceAdmin.class);

            setContentView(R.layout.lock);

            // Watch for button clicks.
            mEnableButton = (Button)findViewById(R.id.btn_lockEnable);
            mEnableButton.setOnClickListener(mEnableListener);
            
            mEnableMsg = (TextView)findViewById(R.id.txtView_lockEnableMsg);
            
            boolean active = mDPM.isAdminActive(mDeviceAdmin);
            if(active)
            {
            	mEnableButton.setVisibility(8);
            	mEnableMsg.setVisibility(0);
            }
            
            mForceLockButton = (Button)findViewById(R.id.btn_lock);
            mForceLockButton.setOnClickListener(mForceLockListener);
            
            //mWipeDataButton = (Button)findViewById(R.id.wipe_data);
            //mWipeDataButton.setOnClickListener(mWipeDataListener);
        }

        private OnClickListener mWipeDataListener = new OnClickListener() {
            public void onClick(View v) {
                
                AlertDialog.Builder builder = new AlertDialog.Builder(Controller.this);
                builder.setMessage("This will erase all of your data.  Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Controller.this);
                        builder.setMessage("This is not a test.  "
                                + "This WILL erase all of your data!  "
                                + "Are you really absolutely sure?");
                        builder.setPositiveButton("BOOM!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                boolean active = mDPM.isAdminActive(mDeviceAdmin);
                                if (active) {
                                    mDPM.wipeData(0);
                                }
                            }
                        });
                        builder.setNegativeButton("Oops, run away!", null);
                        builder.show();
                    }
                });
                builder.setNegativeButton("No way!", null);
                builder.show();
            }
        };
        
        public static DevicePolicyManager getDPM() {
        	return mDPM;
        }
        
        public static ComponentName getDAS() {
        	return mDeviceAdmin;
        }
        
        
    }
}
