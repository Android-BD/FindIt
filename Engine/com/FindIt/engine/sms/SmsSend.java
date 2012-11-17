package com.FindIt.engine.sms;

import android.telephony.SmsManager;

public class SmsSend {
	
	
	static public void sendSms(String destinationAddress, String text) {
		SmsManager sms = SmsManager.getDefault() ;
		sms.sendTextMessage(destinationAddress, null, text, null, null);
	}
	

}
