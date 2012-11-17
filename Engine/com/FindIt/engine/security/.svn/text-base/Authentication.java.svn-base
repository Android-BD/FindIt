package com.FindIt.engine.security;

import android.content.Context;
import android.util.Log;

import com.FindIt.engine.data.User;
import com.FindIt.engine.database.UserSettingsDB;

public class Authentication 
{
	private UserSettingsDB userDB;
	private Context context;
	private static String TAG = "AUTHENTICATION";
	
	public Authentication (Context con)
	{
		this.context = con;
		userDB = new UserSettingsDB(context);
	}
	
	public Boolean authenticate (String phone_number, String password)
	{
		Boolean result = false;
		String enPassword, dePassword = null;
		
		//userDB.open();
		User uObj = userDB.getUserInfo();
		//userDB.close();
		
		if(uObj == null)
			return result;
		
		enPassword = uObj.getEnPswd();
		Log.d(TAG, "encrypted password is " + enPassword);
		
		dePassword = decrypt(enPassword);
		Log.d(TAG, "decrypted password is " + dePassword);
		
		if(dePassword.equals(null) || !password.equals(dePassword))
			return result;
		
		return true;
	}
	
	public String encrypt(String txtToEncrypt)
	{
		String enTxt = null;
		
		if(txtToEncrypt == null || txtToEncrypt == "")
			return enTxt;
		
		try 
		{
			enTxt = AESCrypto.encrypt(txtToEncrypt);
			Log.d(TAG, "encrypted text is " + enTxt);
		} 
		catch (Exception e) 
		{
			// TODO How to print StackTrace?
			Log.e(TAG, "Error occured while encrypting " + txtToEncrypt);
			e.printStackTrace();
		}
		
		return enTxt;
	}
	
	public String decrypt(String txtToDecrypt)
	{
		String deTxt = null;
		
		if(txtToDecrypt.equals(null) || txtToDecrypt == "")
			return deTxt;
		
		try 
		{
			deTxt = AESCrypto.decrypt(txtToDecrypt);
			Log.d(TAG, "decrypted txt is " + deTxt);
		} 
		catch (Exception e) 
		{
			// TODO How to print StackTrace?
			Log.e(TAG, "Error occured while encrypting " + txtToDecrypt);
			e.printStackTrace();
		}
		
		return deTxt;
	}

}
