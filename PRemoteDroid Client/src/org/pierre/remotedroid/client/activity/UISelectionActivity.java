package org.pierre.remotedroid.client.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class UISelectionActivity extends Activity
{
	public static DBAdapter dba;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		dba = new DBAdapter(this);
		
		ScrollView sv = new ScrollView(this);
		RelativeLayout rl = new RelativeLayout(this);
		sv.addView(rl);
		
		ArrayList<String> tables = new ArrayList<String>();
		tables = dba.listTables();
		String str[] = (String[]) tables.toArray(new String[tables.size()]);
		
		ArrayList<Record> results = new ArrayList<Record>();
		for (int i = 0; i < str.length; i++)
			results = dba.query(str[i]);
		
		for (int i = 0; i < results.size(); i++)
		{
			Record temp = results.get(i);
			Button btn = new Button(this);
			btn.setText(temp.getLabel());
			btn.setBackgroundResource(temp.getColor());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(temp.getWidth(), temp.getWidth());
			params.leftMargin = temp.getX();
			params.topMargin = temp.getY();
			rl.addView(btn, params);
			Log.v(this.toString(), "Name:" + temp.getId() + " X:" + temp.getX() + " Y: " + temp.getY() + " Width:" + temp.getWidth() + " Height:" + temp.getHeight() + " Color:" + temp.getColor() + " Label:" + temp.getLabel() + " KeyBinding:" + temp.getKeyBinding());
		}
		this.setContentView(sv);
		
	}
}
