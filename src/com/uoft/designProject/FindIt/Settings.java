package com.uoft.designProject.FindIt;

import com.FindIt.engine.data.User;
import com.FindIt.engine.database.UserSettingsDB;
import com.FindIt.engine.security.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Settings extends Activity
{
	private TextView userName;
	private TextView msg;
	
	private EditText oldPswd;
	private EditText newPswd;
	private EditText newHint;
	private EditText ownerName;	
	private EditText altNum;	
	
	private User uObj;
	private UserSettingsDB userDB;
	private Authentication auth;
	private Button updateBtn;
	private boolean firstLogin = false;
	
	String pOld;
	String pNew;
	String nHint;
	String oName;
	String aNum;
	
	private String TAG = "settings_activity";
	
	OnClickListener updateListener = new OnClickListener()
	{
		public void onClick(View arg0) 
		{
			pOld = oldPswd.getText().toString();
			pNew = newPswd.getText().toString();
			nHint = newHint.getText().toString();
			oName = ownerName.getText().toString();
			aNum = altNum.getText().toString();
			
			Intent SettingsIntent = new Intent(Settings.this, Menu.class);
			
			msg.setText("");
			
			if(pOld.length() == 0 || !pOld.equals(uObj.getDePswd()))
			{
				msg.setText("Please enter Correct Current Password");
				return;
			}
			if(firstLogin)
			{
				if(!isAltNum())
				{
					msg.setText("Please enter Alternate Contact Number");
					return;
				}
				if(!checkAltNum())
					return;				
			}
			else
			{
				if(pNew.length() == 0 && nHint.length() == 0 &&
						oName.length() == 0 && aNum.length() == 0)
				{
					msg.setText("Please enter New Password or New Hint or Owner Name" +
							"or Alternate Number to Update.");
					return;
				}
				if(isNewPswd())
				{
					if(!checkNewPswd())
						return;
				}
				if(isNewHint())
				{
					if(!checkNewHint())
						return;
				}
				if(isAltNum())
				{
					if(!checkAltNum())
						return;
				}
			}
						
			boolean updated = userDB.updateInfo(uObj, uObj.getNumber());
			Log.d(TAG, "User Update = " + new Boolean(updated).toString());
						
			if(!updated)
				SettingsIntent.putExtra("settings.update", "Update Failed. Please Try Again.");
			else
				SettingsIntent.putExtra("settings.update", "Update was Successful.");
			
			Settings.this.startActivity(SettingsIntent);
		}
		
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		userDB = new UserSettingsDB(this);
		auth = new Authentication(this);
		
		uObj = userDB.getUserInfo();
		uObj.setDePswd(auth.decrypt(uObj.getEnPswd()));
		uObj.setDePswdHint(auth.decrypt(uObj.getEnPswdHint()));
		
		oldPswd = (EditText)findViewById(R.id.editTxt_settingsOldPswd);
		newPswd = (EditText)findViewById(R.id.editTxt_newPswd);
		newHint = (EditText)findViewById(R.id.editTxt_newHint);
		ownerName = (EditText)findViewById(R.id.editTxt_ownerName);
		altNum = (EditText)findViewById(R.id.editTxt_altNumber);
		
		userName = (TextView)findViewById(R.id.txtView_settingsNumber);
		userName.setText(uObj.getNumber());
		
		msg = (TextView)findViewById(R.id.txtView_settingsMsg);
		msg.setTextColor(Color.RED);
		
		updateBtn =(Button)findViewById(R.id.btn_update);
		updateBtn.setOnClickListener(updateListener);
		
		Bundle settingsBundle = this.getIntent().getExtras();
		
		if(settingsBundle != null)
		{
			if(settingsBundle.getBoolean("firstLogin"))
			{
				firstLogin = true;
				findViewById(R.id.txtView_settingsNumber).setVisibility(8);
				findViewById(R.id.txtView_settingsNumber).setVisibility(8);
				findViewById(R.id.txtView_newHint).setVisibility(8);
				findViewById(R.id.txtView_newPswd).setVisibility(8);
				findViewById(R.id.txtView_settingsUName).setVisibility(8);
				
				findViewById(R.id.editTxt_newPswd).setVisibility(8);
				findViewById(R.id.editTxt_newHint).setVisibility(8);
				
				findViewById(R.id.horizontalLine_newPswd).setVisibility(8);
				findViewById(R.id.horizontalLine_nHint).setVisibility(8);
				findViewById(R.id.horizontalLine_settingsUName).setVisibility(8);
			}
		}
		
    }
	
	private boolean checkAltNum()
	{
		if(aNum.equals(uObj.getNumber()))
		{
			msg.setText("Alternate Contact Number CANNOT be same as you Current " +
					"Cellular Number.");
			return false;
		}
		uObj.setAlternateNumber(aNum);
		uObj.setOwnerName(oName);
		
		return true;
	}
	
	private boolean checkNewPswd()
	{
		if(pNew.length() < 8)
		{
			msg.setText("New Password CANNOT be less than 8 characters.");
			return false;
		}
		else if(pOld.equals(pNew))
		{
			msg.setText("New Password and Old Password CANNOT be Same");
			return false;
		}
		uObj.setEnPswd(auth.encrypt(pNew));
		
		return true;
	}
	
	private boolean checkNewHint()
	{
		if((pNew.length()!=0 && nHint.contains(pNew)) || nHint.contains(pOld))
		{
			msg.setText("Password Hint CANNOT contain New Password or Old Password.");
			return false;
		}
		uObj.setEnPswdHint(auth.encrypt(nHint));
		
		return true;
	}
	
	private boolean isAltNum()
	{
		if(aNum.length() == 0)
			return false;
		
		return true;
	}
	
	private boolean isNewPswd()
	{
		if(pNew.length() == 0)
			return false;
		
		return true;
	}
	
	private boolean isNewHint()
	{
		if(nHint.length() == 0)
			return false;
		
		return true;
	}
	
}
