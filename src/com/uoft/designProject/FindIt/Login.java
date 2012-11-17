package com.uoft.designProject.FindIt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FindIt.engine.data.User;
import com.FindIt.engine.database.UserSettingsDB;
import com.FindIt.engine.security.Authentication;

public class Login extends Activity 
{
	Context con = this;
	private UserSettingsDB userDB = new UserSettingsDB(con);
	private Authentication auth = new Authentication(con);
	private static final String TAG = "login_activity";
	private String simNumber, simIMEI;
	private Boolean firstLogin = true;
	private User userObj;
	
	private TextView message; //TODO: this will be replaced by a popup in future
	private TextView userName;
	private TextView pConfirm;
	private TextView pHint;
	private TextView forgotPswd;
	
	private EditText pswd;
	private EditText pswdHint;
	private EditText pswdConfirm;
	
	private Button login;
	
	private OnClickListener loginListener = new OnClickListener()
	{

		public void onClick(View v) 
		{
			message.setText("");
			String password = pswd.getText().toString();
			Intent LoginIntent = new Intent(Login.this, Menu.class);
			
			if(firstLogin)
			{				
				String pConfirm = pswdConfirm.getText().toString();
				String pHint = pswdHint.getText().toString();
				
				if(password.length() < 8 || pConfirm.length() < 8)
				{
					message.setText("Minimum Password length is 8 characters");
					message.setTextColor(Color.RED);	
					return;
				}
				else if(!password.equals(pConfirm))
				{
					message.setText("Password and Confirm Password fields DONOT Match");
					message.setTextColor(Color.RED);	
					return;
				}
				else if(pHint.contains(password))
				{
					message.setText("Password Hint CANNOT contain Password.");
					message.setTextColor(Color.RED);	
					return;
				}
				
				userObj = new User();
				userObj.setEnPswd(auth.encrypt(password));
				userObj.setEnPswdHint(auth.encrypt(pHint));
				userObj.setNumber(simNumber);
				userObj.setIMEI(simIMEI);
				
				long id = userDB.insertInfo(userObj);
				Log.d(TAG, "User Inserted with ID = " + new Integer((int)id).toString());
			}
			else
			{
				if(password.length() == 0)
				{
					message.setText("Please Enter the Password");
					message.setTextColor(Color.RED);
					return;
				}
				else if(!userObj.getDePswd().equals(password))
				{
					message.setText("Password Entered is INCORRECT");
					message.setTextColor(Color.RED);
					return;
				}
				if(!userObj.getNumber().equals(simNumber) || !userObj.getIMEI().equals(simIMEI))
				{
					String old_num = userObj.getNumber();
					userObj.setNumber(simNumber);
					userObj.setIMEI(simIMEI);
					
					boolean updated = userDB.updateInfo(userObj, old_num);
					Log.d(TAG, "User Updated = " + new Boolean(updated).toString());
				}
			}
			LoginIntent.putExtra("firstLogin", firstLogin);
			Login.this.startActivity(LoginIntent);
		}
				
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setFirstLogin(isFirstLogin());
        setSimNumber(getSIMNumber());
        setSimIMEI(getDeviceID());
        
        userName = (TextView)findViewById(R.id.txtView_phoneNumber);
        pConfirm = (TextView)findViewById(R.id.txtView_pswdConfirm);
        pHint = (TextView)findViewById(R.id.txtView_pswdHint);
        
        forgotPswd = (TextView)findViewById(R.id.txtView_forgotPswd);
        
        message = (TextView)findViewById(R.id.txtView_msg);
        
        pswd = (EditText)findViewById(R.id.editTxt_pswd);
        pswdConfirm = (EditText)findViewById(R.id.editTxt_pswdConfirm);
        pswdHint = (EditText)findViewById(R.id.editTxt_pswdHint);
        
        login = (Button)findViewById(R.id.btn_login);
        login.setOnClickListener(loginListener);
        
        if(firstLogin)
        {
        	forgotPswd.setVisibility(View.GONE);
        	firstLogin();
        }
        else
        	regularLogin();
    }
	
	private void firstLogin()
	{
		userName.setText(simNumber);
	}
	
	private void regularLogin()
	{
		pswdConfirm.setVisibility(View.GONE);
		pswdHint.setVisibility(View.GONE);
		
		pConfirm.setVisibility(View.GONE);
		pHint.setVisibility(View.GONE);
		
		LinearLayout pswdConfirm = (LinearLayout)findViewById(R.id.horizontalLine_pswdConfirm);
		pswdConfirm.setVisibility(View.GONE);
		
		LinearLayout pswdHint = (LinearLayout)findViewById(R.id.horizontalLine_hint);
		pswdHint.setVisibility(View.GONE);
		
		userName.setText(simNumber);
		
		forgotPswd.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v) 
			{
				message.setText("Password Hint: " + userObj.getDePswdHint());
			}
			
		});		
	}
	
	/****************************
     * Following function gets the phone number from SIM card.
     * It utilizes the phoneManager provided by Android
     * @return
     */
	private String getSIMNumber()
    {
    	TelephonyManager phoneManager;
    	phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    	String number = phoneManager.getLine1Number();
    	return number;
    }
	
	/****************************
     * - Following function gets the device ID aka IMEI for GSM and MEID for CDMA
     *   from SIM card.
     * - It utilizes the phoneManager provided by Android
     * @return
     */
	private String getDeviceID()
    {
    	TelephonyManager phoneManager;
    	phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    	String imei = phoneManager.getDeviceId();
    	Log.d(TAG, "device id is: " + imei);
    	return imei;
    }
	
	private Boolean isFirstLogin()
	{
		Boolean first_login = true;
		
		userObj = userDB.getUserInfo();
		
		if(userObj == null)
			return first_login;
		
		userObj.setDePswd(auth.decrypt(userObj.getEnPswd()));
		userObj.setDePswdHint(auth.decrypt(userObj.getEnPswdHint()));
		return false;
	}

	/**
	 * @param simNumber the simNumber to set
	 */
	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	/**
	 * @param simIMEI the simIMEI to set
	 */
	public void setSimIMEI(String simIMEI) {
		this.simIMEI = simIMEI;
	}

	/**
	 * @param firstLogin the firstLogin to set
	 */
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
}
