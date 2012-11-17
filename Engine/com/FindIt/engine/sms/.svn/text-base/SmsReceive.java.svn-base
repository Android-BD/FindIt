/*************Source********
 * Source code for the onReceive() method is based on source code posted in:
 * http://www.anddev.org/novice-tutorials-f8/recognize-react-on-incoming-sms-t295-105.html
 */

package com.FindIt.engine.sms;

import java.io.IOException;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.FindIt.engine.data.LocationData;
import com.FindIt.engine.database.LocationDB;
import com.FindIt.engine.features.Backup;
import com.FindIt.engine.features.Image;
import com.FindIt.engine.features.Lock;
import com.FindIt.engine.features.Page;
import com.FindIt.engine.features.Recover;
import com.FindIt.engine.features.Wipe;
import com.FindIt.engine.security.Authentication;

public class SmsReceive extends BroadcastReceiver {

	static final String ACTION = "android.provider.Telephony.SMS_RECEIVED" ;
	private Authentication auth;
	private Context con;

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		con = context;
		auth = new Authentication(con);
		
		if(intent.getAction().equals(ACTION)) 
		{
			//TODO: check if we have an existing password in the database, if not do not continue
			
			StringBuilder buf = new StringBuilder();
			Bundle bundle = intent.getExtras();
			
			//The SMS message is stored in the extras of the intent
			if (bundle != null) 
			{
				String senderNumber = "";
				String senderMessage = "";
				
				Object[] pduObj = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pduObj.length];
				
				//TODO: double check if we really need for-loops
				
				//getting all messages, there might be more than one message
				for(int i=0;i<pduObj.length;i++)
					messages[i]=SmsMessage.createFromPdu((byte[])pduObj[i]);
				
				//parsing each messages found
				for (SmsMessage currentMessage : messages)
				{
					senderNumber = currentMessage.getDisplayOriginatingAddress();
					senderMessage = currentMessage.getDisplayMessageBody();
					
					
					//Message is not a FindIt message
					if (!isCommandValid(senderMessage)) {
						break;
					}
					
					//TODO: debugging purposes, remove
					buf.append("source: ");
					buf.append(senderNumber);
					buf.append("\nmessage: ");
					buf.append(senderMessage);
					
					
					/* format of message: FINDIT:<command>:password */
					String[] message = senderMessage.split(":");
					
					if(!auth.authenticate(senderNumber, message[2]))
					{
						//TODO: send "Authentication Failed: Password Entered is INCORRECT" error
						break;
					}
					
					/* checking the command and performing the necessary activity */
					if( message[1].toUpperCase().equals("LOCATE")) 
					{
						Geocoder gcd = new Geocoder(context);
						
						try 
						{
							LocationDB loc = new LocationDB(context);
							LocationData ld = loc.getLocationInfo();
							if(ld != null)
							{
								SmsSend.sendSms(senderNumber, "As of " + ld.getDateTime() +
										" Latitude is " + ld.getLatitude() + " Longitude is " 
										+ ld.getLongitude());
								/*List<Address> addresses = gcd.getFromLocation(Double.valueOf(ld.getLatitude()), 
										Double.valueOf(ld.getLongitude()), 1);
								
								if(addresses.size() > 0)
								{
									String sendMsg = null;
									String country = addresses.get(0).getCountryName();
									String locality = addresses.get(0).getLocality();
									String pcode = addresses.get(0).getPostalCode();
									String subLocality = addresses.get(0).getSubAdminArea();
									String add = addresses.get(0).getPremises();
									
									if(add != null)
										sendMsg += "Premises:" + add;
									if(subLocality !=null)
										sendMsg += " Locality:" + subLocality;
									if(pcode != null)
										sendMsg += " Postal Code:" + pcode;
									if(locality !=null)
										sendMsg += " City:"+locality;
									if(country !=null)
										sendMsg += " Country:"+country;
									
									if(sendMsg != null)
									{
										String tmp = sendMsg;
										sendMsg = "As of " + ld.getDateTime() 
										+ " location is - " + tmp;
										
										SmsSend.sendSms(senderNumber, sendMsg);
									}
									else
									{
										SmsSend.sendSms(senderNumber, "As of " + ld.getDateTime() +
												" Latitude is " + ld.getLatitude() + " Longitude is " 
												+ ld.getLongitude());
									}
								}
								else
								{
									SmsSend.sendSms(senderNumber, "Location Unavailable");
								}*/
							}											
						} 
						catch (NumberFormatException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						/*
						catch (IOException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
					} 
					else if ( message[1].toUpperCase().equals("BACKUP")) {						
						Backup.performBackup(context);
						SmsSend.sendSms(senderNumber, "BACKUP sms message") ;

					}
					else if ( message[1].toUpperCase().equals("RECOVERY")) {
						Recover.performRecovery(context);
						SmsSend.sendSms(senderNumber, "RECOVERY sms message") ;
					}
					else if ( message[1].toUpperCase().equals("PAGE")) {					
						try 
						{
							Page.performPage(context);
							SmsSend.sendSms(senderNumber, "Page Initiated") ;
						} 
						catch (InterruptedException e) 
						{
							// do nothing
						}
					}
					else if ( message[1].toUpperCase().equals("LOCK")) {
						if(Lock.performLock(context))
							SmsSend.sendSms(senderNumber, "Phone Locked");
						else
							SmsSend.sendSms(senderNumber, "Device CANNOT be Locked because" +
									"Device Administration is disabled. Please Try Again");
					}
					else if ( message[1].toUpperCase().equals("WIPE")) 
					{
						SmsSend.sendSms(senderNumber, "Wiping user data and Shutting Down " +
								"Device");
						if(!Wipe.performWipe(context))
						{
							SmsSend.sendSms(senderNumber, "Wipe Failed because" +
									"Device Administration is disabled. Please Try Again");
						}
					}
					else if ( message[1].toUpperCase().equals("IMAGE")) {
						Image.performImage(context);
						SmsSend.sendSms(senderNumber, "IMAGE sms message") ;
					}
					else
					{
						//TODO: send "Command NOT Recognized" error.
					}
				}
			}
			
		}
		
	}
	
    /**
     * private method to check if the SMS message is a valid command
     * for FindIt
     * 
     * @param body of the message
     * @return true if message is a valid command else false
     */
	private static boolean isCommandValid(String senderMessage) {
		if (!(senderMessage.toUpperCase().contains("FINDIT")))
			return false ;
		else if (senderMessage.split(":").length < 3 ) 
			return false ;
		else
			return true ;
		
	}

}
