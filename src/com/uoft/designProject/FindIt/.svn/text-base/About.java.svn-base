package com.uoft.designProject.FindIt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends Activity
{
	private TextView about;
	private TextView fiFeatures;
	private TextView howWork;
	private TextView sec;
	
	private String descr = "FindIt Application was created with a vision to allow users " +
			"like you to control your Android Smartphone remotely thus, securing data " +
			"and increasing the chance of recovering your lost or stolen device. \n";
	
	private String features = "With currently implemented features you can remotely: \n" +
	"LOCK - your Android Device. \n" +
	"WIPE - phone contacts, SMS, MMS, and phone log. \n" +
	"LOCATE - find precise location of your Android Device. \n" +
	"PAGE - your Android Device. \n" +
	"BACKUP - phone contacts, SMS, MMS and phone log to your FREE 2GB online storage" +
	"on DropBox cloud information storage system.\n \n" +
	"In addition... \n" +
	"You can set auto-reply SMS message for different modes such as Car Mode, " +
	"and Meeting mode. \n";
	
	private String how = "FindIt is designed to allow users to access the above features " +
			"from anywhere. You can access the above features via SMS. In order, to " +
			"activate a feature, send a SMS to your Android Device from any phone in " +
			"following format: \n \n findit:COMMAND:PASSWORD. \n \n" +
			"COMMAND corresponds to one of the features mentioned above in upper-case such as" +
			" LOCK, WIPE, LOCATE, PAGE, and BACKUP. \n" +
			"NOTE: The command is NOT Case Sensitive. \n \n" +
			"PASSWORD corresponds to the password used to login to the application. \n";
	
	private String security = "FindIt application is designed to incorporate three core " +
			"principles of information security, which are widely known as CIA triad. " +
			"They are: \n\n" +
			"CONFIDENTIALITY: FindIt uses AES-128 bit encrytion to store core user" +
			"information such as password, password hint, and unique Device ID. \n \n" +
			"INTEGRITY: In order to preserve integrity of information stored on phone and " +
			"feature functionality, FindIt mandates authentication of the user. \n\n" +
			"AVAILABILITY: Information and features of FindIt application are available all " +
			"the time through Text Message, GUI and DropBox cloud information storage system" +
			"\n";			
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		about = (TextView)findViewById(R.id.txtView_about);
		about.setText(descr);
		
		fiFeatures = (TextView)findViewById(R.id.txtView_features);
		fiFeatures.setText(features);
		
		howWork = (TextView)findViewById(R.id.txtView_howWork);
		howWork.setText(how);
		
		sec = (TextView)findViewById(R.id.txtView_security);
		sec.setText(security);
    }
}
