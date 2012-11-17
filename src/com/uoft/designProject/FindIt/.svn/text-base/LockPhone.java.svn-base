package com.uoft.designProject.FindIt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.FindIt.engine.data.User;
import com.FindIt.engine.database.UserSettingsDB;
import com.FindIt.engine.security.Authentication;

public class LockPhone extends Activity
{
	private String msg = "The device has been locked. Please Enter Password to unlock the " +
						 "device.";
	
	private User userObj;
	private UserSettingsDB userDB;
	private Authentication auth;
	
	private TextView msgView;
	private TextView infoView;
	private TextView errMsg;
	private EditText psswd;
	private Button loginBtn;
		
	private OnClickListener clickListener = new OnClickListener()
	{
		public void onClick(View v) 
		{		
			if(v.getId() == loginBtn.getId())
			{
				if(!psswd.getText().equals(userObj.getDePswd()))
				{
					errMsg.setText("Invalid Password");
					return;
				}
				else
				{
					Intent lockIntent = new Intent(LockPhone.this, Menu.class);
					LockPhone.this.startActivity(lockIntent);
				}
			}
			else if(v.getId() == infoView.getId())
			{
				errMsg.setText("om...");
			}			
		}
		
	};
		
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock);
		
		userDB = new UserSettingsDB(this);
		auth = new Authentication(this);
		
		userObj = userDB.getUserInfo();
		userObj.setDePswd(auth.decrypt(userObj.getEnPswd()));
		userObj.setDePswdHint(auth.decrypt(userObj.getEnPswdHint()));
		
		msgView = (TextView)findViewById(R.id.txtView_lockMsg);
		msgView.setText(msg);
		
		infoView = (TextView)findViewById(R.id.txtView_lockCall);
		infoView.setOnClickListener(clickListener);
		
		errMsg = (TextView)findViewById(R.id.txtView_errMsg);
		errMsg.setTextColor(Color.RED);
		
		loginBtn = (Button)findViewById(R.id.btn_lockLogin);
		loginBtn.setOnClickListener(clickListener);
		
		psswd = (EditText)findViewById(R.id.editTxt_lockPswd);
    }
}
