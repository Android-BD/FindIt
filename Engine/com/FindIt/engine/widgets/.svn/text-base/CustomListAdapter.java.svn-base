package com.FindIt.engine.widgets;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uoft.designProject.FindIt.R;

@SuppressWarnings("unchecked")
public class CustomListAdapter extends ArrayAdapter
{
	LayoutInflater inflator;
	String[] items;
	HashMap<String,Integer> itemMap;
	String type;
	int rowView;
	
	public CustomListAdapter(Context context, int textViewResourceId, Object[] objects) 
	{
		super(context, textViewResourceId, objects);
		this.inflator = LayoutInflater.from(context);
		this.items = (String[]) objects;
		this.rowView = textViewResourceId;
	}
	
	public CustomListAdapter(Context context, int textViewResourceId, Object[] objects,
			HashMap<String, Integer> map, String adapterType) 
	{
		super(context, textViewResourceId, objects);
		this.inflator = LayoutInflater.from(context);
		this.items = (String[]) objects;
		this.rowView = textViewResourceId;
		this.itemMap = map;
		this.type = adapterType;
	}
			
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		if(row == null)
			row = inflator.inflate(rowView, null);
		
		if(type == "menuList")
			menuList(position, row);
				
		return row;
	}
	
	private void menuList(int position, View row)
	{
		String itemText = items[position];
		TextView label = (TextView)row.findViewById(R.id.txtView_listRowLabel);
		label.setText(itemText);
		
		ImageView icon = (ImageView)row.findViewById(R.id.icon_listRowIcon);
		
		if(!itemMap.equals(null) && itemMap.containsKey(itemText))
			icon.setImageResource(itemMap.get(itemText));
	}
}
