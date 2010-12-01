package org.pierre.remotedroid.client.activity;

import org.pierre.remotedroid.client.R;
import org.pierre.remotedroid.client.app.PRemoteDroid;
import org.pierre.remotedroid.protocol.PRemoteDroidActionReceiver;
import org.pierre.remotedroid.protocol.action.PRemoteDroidAction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class UIMainMenuActivity extends Activity implements PRemoteDroidActionReceiver
{
	private static final int EXIT_MENU_ITEM_ID = 0;
	
	private static CharSequence ExitText = "Return to pRemoteDroid";
	
	private PRemoteDroid application;
	private SharedPreferences preferences;
	
	private Button UISelectionButton;
	private Button UICreationButton;
	private Button UIEditButton;
	private Button UIHelpButton;
	
	// temporary
	static final int KEY_BIND_REQUEST = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.application = (PRemoteDroid) this.getApplication();
		
		this.preferences = this.application.getPreferences();
		
		this.setContentView(R.layout.uimainmenu);
		
		UISelectionButton = (Button) findViewById(R.id.UI_Selection_Button);
		UICreationButton = (Button) findViewById(R.id.UI_Creation_Button);
		UIEditButton = (Button) findViewById(R.id.UI_Edit_Button);
		UIHelpButton = (Button) findViewById(R.id.UI_Help_Button);
		
	}
	
	protected void onResume()
	{
		super.onResume();
		
		this.application.registerActionReceiver(this);
		
	}
	
	protected void onPause()
	{
		super.onPause();
		
		this.application.unregisterActionReceiver(this);
	}
	
	public void receiveAction(PRemoteDroidAction action)
	{
		// TODO Auto-generated method stub
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(Menu.NONE, EXIT_MENU_ITEM_ID, Menu.NONE, ExitText);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case EXIT_MENU_ITEM_ID:
				this.startActivity(new Intent(this, ControlActivity.class));
				break;
			
		}
		
		return true;
	}
	
	public void UISelection(View view)
	{
		// Go to the User Interface Selection screen
		
		// temporary
		this.startActivity(new Intent(this, UISelectionActivity.class));
		
	}
	
	// temporary
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == KEY_BIND_REQUEST)
		{
			if (resultCode == RESULT_OK)
			{
				Bundle bundle = data.getExtras();
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle("Debug");
				alertDialog.setMessage(bundle.getString("keys"));
				alertDialog.setButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						return;
					}
				});
				alertDialog.show();
			}
		}
	}
	
	public void UICreation(View view)
	{
		// Go to the User Interface Creation screen
		this.startActivity(new Intent(this, CreateUIActivity.class));
	}
	
	public void UIEdit(View view)
	{
		// Go to the User Interface Edit screen
	}
	
	public void UIHelp(View view)
	{
		// Go to the UI Help screen
		this.startActivity(new Intent(this, UIMainHelpActivity.class));
	}
	
}
