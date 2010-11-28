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

public class OtherKeysActivity extends Activity
{
	private EditText keyCom;
	private String keyStr = "";
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.otherkeys);
		Drawable d = findViewById(R.id.KBUndo).getBackground();
		PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
		d.setColorFilter(filter);
		
		d = findViewById(R.id.KBOK).getBackground();
		d.setColorFilter(filter);
		
		d = findViewById(R.id.otherkeys).getBackground();
		d.setColorFilter(filter);
		
		keyCom = (EditText) findViewById(R.id.EditText01);
		
		Bundle bundle = getIntent().getExtras();
		keyStr = bundle.getString("Keys");
		keyCom.setText(keyStr);
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
		Bundle keyBinds = new Bundle();
		keyBinds.putString("keys", keyStr);
		Intent intent = new Intent();
		intent.putExtras(keyBinds);
		setResult(RESULT_CANCELED, intent);
		finish();
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
}
