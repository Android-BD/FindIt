package com.uoft.designProject.FindIt;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.FindIt.engine.data.User;
import com.FindIt.engine.database.UserSettingsDB;
import com.FindIt.engine.security.Authentication;

/** Called when the activity is first created. */
public class Login_example extends Activity 
{
	/*
	 * User defined private global variables
	 */
	private TextView message; //this will be replaced by a popup in future
	private TextView userName;
	String encrypted = null, decrypted;
	
	long t;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        /*****************************
         * Following function sets the layout (the one in xml file) on the screen 
         */
        setContentView(R.layout.main);
        
        /*****************************
         * Following block of code edits the text in a particular widget aka view in android
         * Line 1 of the block finds and gets the label aka text view in the layout
         * Line 2 of the block get the number from the SIM card (see the function below) 
         * Line 3 of the block is the a easy way to keep track of the tasks todo in the project
         * Line 4 of the block sets the text (modified text) to the view
         */
        userName = (TextView) findViewById(R.id.txtView_phoneNumber);
        String phoneNumber = getNumber();
        //TODO: what to do if phoneNumber == NULL?
        userName.setText(phoneNumber);
        /***********END of the Block**********/
        
        /*****************************
         * Line 1 finds and gets the TextView
         * Line 2 finds and gets the button
         */
        message = (TextView) findViewById(R.id.txtView_msg);
        Button loginButton = (Button) findViewById(R.id.btn_login);
        
        /****************************
         * - whenever you want to listen to user's input you need a listener
         * - following line applies a listener to the button.
         *   in other words this lines enables the button to listen for user inputs
         */
        loginButton.setOnClickListener(clickListener);
               
        /****************How to Authenticate*********/
        Authentication test2 = new Authentication (this);
		Boolean x = test2.authenticate("1234", "omshiv");
		Log.d("Login", "Authentication Passed: " + x.toString());
		/***************End**************************/
		
        /*******************Encryption/Decryption Call**********/
        encrypted = test2.encrypt("omshiv");
		decrypted = test2.decrypt(encrypted);
		/*****************End of Encryption/Decryption Call*******/
		
		/**********************************************
         * Database Call.
         * UserSettingsDB object creates the table if it does not exist
         * We need to open and close database before and after using it
         */
        UserSettingsDB test = new UserSettingsDB(this);
        //test.open();
        test.deleteEntry("1234");
        User uObj = new User();
        uObj.setNumber("1234");
        uObj.setIMEI("IMEI");
        uObj.setEnPswd(encrypted);
        uObj.setEnPswdHint(test2.encrypt("hint"));
        t = test.insertInfo(uObj);
        //test.close();
        Log.d("Login", "something was inserted id is: "+ t);
        /*****************End of DB Call*************/
    }
    
    /*****************************************
     * - Following click listener is applied to the button.
     * - This block defines the actions taken when user input is received
     * - This is initialize outside of onCreate() function so that other functions can take 
     *   advantage of it.
     */
    private OnClickListener clickListener = new OnClickListener() 
    {
		public void onClick(View arg0) 
		{
			message.setText("TODO: \n - Authenticate; \n" +
					"- Show next activity with list of options \n" +
					"- Add graphics and change alignment to center \n"+
					"- By default the password should be phonenumber " +
					"when the user logs in for the very first time \n" + encrypted +" \n" +
					decrypted);
			
			/*Context mContext = getApplicationContext();
			final Notification om = new Notification(mContext);
			om.setMessage("om shiv");
			om.setButton("mk", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which) {
					om.getDialogObj().show();					
				}
				
			});
			om.onCreateDialog();
			om.getDialogObj().show();*/
			
		}
    };
    
    /****************************
     * Following function gets the phone number from SIM card.
     * It utilizes the phoneManager provided by Android
     * @return
     */
    private String getNumber()
    {
    	TelephonyManager phoneManager;
    	phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    	String number = phoneManager.getLine1Number();
    	return number;
    }
}