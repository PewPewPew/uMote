package org.pierre.remotedroid.client.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class UISelectionActivity extends Activity
{
	public static DBAdapter dba;
	AlertDialog.Builder builder;
	AlertDialog alertDialog;
	Dialog dialog;
	String str[];
	RelativeLayout rl;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		dba = new DBAdapter(this);
		
		ScrollView sv = new ScrollView(this);
		rl = new RelativeLayout(this);
		sv.addView(rl);
		
		// dialog = new Dialog(UISelectionActivity.this);
		
		this.setContentView(sv);
		
		ArrayList<String> tables = new ArrayList<String>();
		tables = dba.listTables();
		str = (String[]) tables.toArray(new String[tables.size()]);
		
		builder = new AlertDialog.Builder(UISelectionActivity.this);
		
		builder.setTitle("Select a Saved Remote");
		builder.setItems(str, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int item)
			{
				Toast.makeText(getApplicationContext(), str[item] + " loaded.", Toast.LENGTH_SHORT).show();
				
				ArrayList<Record> results = new ArrayList<Record>();
				results = dba.query(str[item]);
				
				for (int i = 0; i < results.size(); i++)
				{
					Record temp = results.get(i);
					Button btn = new Button(UISelectionActivity.this);
					btn.setText(temp.getLabel());
					btn.setBackgroundResource(temp.getColor());
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(temp.getWidth(), temp.getWidth());
					params.leftMargin = temp.getX();
					params.topMargin = temp.getY();
					rl.addView(btn, params);
					// Log.v(this.toString(), "Name:" + temp.getId() + " X:" +
					// temp.getX() +
					// " Y: " + temp.getY() + " Width:" + temp.getWidth() +
					// " Height:" +
					// temp.getHeight() + " Color:" + temp.getColor() +
					// " Label:" +
					// temp.getLabel() + " KeyBinding:" + temp.getKeyBinding());
				}
			}
		});
		alertDialog = builder.create();
		alertDialog.show();
		
	}
}
