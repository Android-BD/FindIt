package com.FindIt.engine.widgets;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.uoft.designProject.FindIt.R;

/**
 * @author Aditya Thakkar
 *
 */
public class Notification extends Dialog{

	private String message;
	private String buttonName;
	private Dialog dialogObj;
	private Context activityContext;
	private OnClickListener btnListener;
		
	public Notification(Context context) {
		super(context);
		activityContext = context;
	}
		
	public void onCreateDialog() {
		dialogObj = new Dialog(activityContext);
		dialogObj.setContentView(R.layout.notification_widget);
		dialogObj.setTitle("Custom Dialog");

		TextView messageLabel = (TextView) dialogObj.findViewById(R.id.msg);
		messageLabel.setText(message);
		
		Button btn = (Button)dialogObj.findViewById(R.id.submitBtn);
		btn.setText(buttonName);
		btn.setOnClickListener((android.view.View.OnClickListener) btnListener);
	}
	
	public void onShow()
	{
		dialogObj.show();
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param name the button name to set
	 * @param listener the OnClickListener to attach
	 */
	public void setButton(String name, OnClickListener listener) {
		buttonName = name;
		btnListener = listener;
	}
	
	/**
	 * @param listener the OnClickListener to attach
	 */
	public void setButton(OnClickListener listener) {
		buttonName = "Ok";
		btnListener = listener;
	}
	public Dialog getDialogObj() {
		return dialogObj;
	}
}
