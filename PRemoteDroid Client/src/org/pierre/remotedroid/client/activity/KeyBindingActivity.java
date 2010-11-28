package org.pierre.remotedroid.client.activity;

import org.pierre.remotedroid.client.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class KeyBindingActivity extends Activity
{
	private EditText keyCom;
	private String keyStr = "";
	private static final int OTHER_KEYS_REQUEST = 0;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.keybinding);
		Drawable d = findViewById(R.id.KBUndo).getBackground();
		PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
		d.setColorFilter(filter);
		
		d = findViewById(R.id.KBOK).getBackground();
		d.setColorFilter(filter);
		
		d = findViewById(R.id.otherkeys).getBackground();
		d.setColorFilter(filter);
		
		keyCom = (EditText) findViewById(R.id.EditText01);
	}
	
	public void undo(View view)
	{
		int i = keyStr.lastIndexOf("+");
		if (i != -1)
			keyStr = keyStr.substring(0, i);
		else
			keyStr = "";
		keyCom.setText(keyStr);
	}
	
	public void accept(View view)
	{
		Bundle keyBinds = new Bundle();
		keyBinds.putString("keys", keyStr);
		Intent intent = new Intent();
		intent.putExtras(keyBinds);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	public void other(View view)
	{
		Intent intent = new Intent();
		intent.setClass(this, OtherKeysActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("keys", keyStr);
		intent.putExtras(bundle);
		this.startActivityForResult(intent, OTHER_KEYS_REQUEST);
	}
	
	public void keys(View view)
	{
		Button temp = (Button) findViewById(view.getId());
		if (keyStr == "")
			keyStr += temp.getText();
		else
			keyStr += "+" + temp.getText();
		keyCom.setText(keyStr);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == OTHER_KEYS_REQUEST)
		{
			if (resultCode == RESULT_OK)
			{
				Bundle bundle = data.getExtras();
				keyStr = bundle.getString("keys");
				// keyCom.setText(keyStr);
				Bundle keyBinds = new Bundle();
				keyBinds.putString("keys", keyStr);
				Intent intent = new Intent();
				intent.putExtras(keyBinds);
				setResult(RESULT_OK, intent);
				finish();
			}
			else if (resultCode == RESULT_FIRST_USER)
			{
				Bundle bundle = data.getExtras();
				keyStr = bundle.getString("keys");
				keyCom.setText(keyStr);
			}
		}
	}
}
