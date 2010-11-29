package org.pierre.remotedroid.client.activity;

import org.pierre.remotedroid.client.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CreateUIActivity extends Activity
{
	private static final int KEY_BIND_REQUEST = 0;
	private static CharSequence EditText = "Select Button To Edit";
	
	private RelativeLayout layout;
	
	/** button configuration related **/
	private Dialog dialog;
	private Button newBtn;
	// private Button addedButton;
	private EditText buttonLabel;
	private RadioGroup colorGroup;
	/** END button configuration related **/
	
	// button fields START
	private String buttonString;
	private int buttonColor;
	// button fields END
	
	private int currentlySelected;
	private int savedX;
	private int savedY;
	
	// used for sizing buttons
	private static int MAX_SIZE = 450;
	private static int MIN_SIZE = 90;
	private int buttonWidth;
	private int buttonHeight;
	
	// used to determine if the user wants to edit a button or not
	private boolean editButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.uicreation);
		currentlySelected = Integer.MAX_VALUE;
		savedX = 0;
		savedY = 0;
		
		layout = (RelativeLayout) findViewById(R.id.UICreationLayout);
		// save the title of the menu, used when editButton mode is on
		layout.setContentDescription(this.getTitle());
		
		editButton = false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.createuimenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		switch (item.getItemId())
		{
			case R.id.AddButton:
				buttonConfigDiag();
				break;
			case R.id.EditButton:
				editButton = true;
				// change the title of the screen so that the user knows when
				// they are in editButton mode
				this.setTitle(EditText);
				break;
			default:
				return super.onOptionsItemSelected(item);
				
		}
		return true;
	}
	
	public void buttonConfigDiag()
	{
		buttonWidth = MIN_SIZE;
		buttonHeight = MIN_SIZE;
		
		buttonString = "Button";
		buttonColor = Color.GRAY;
		
		dialog = new Dialog(CreateUIActivity.this);
		
		dialog.setContentView(R.layout.buttonconfig);
		dialog.setTitle("Create a Button");
		dialog.setCancelable(true);
		
		dialog.show();
		
		newBtn = (Button) dialog.findViewById(R.id.ButtonSample);
		newBtn.setDrawingCacheEnabled(true);
		
		buttonLabel = (EditText) dialog.findViewById(R.id.ButtonSampleLabelInput);
		buttonLabel.setOnKeyListener(new OnKeyListener()
		{
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				
				newBtn.setText(((EditText) v).getText());
				buttonString = ((EditText) v).getText().toString();
				return false;
			}
			
		});
		
		colorGroup = (RadioGroup) dialog.findViewById(R.id.ColorRadioGroup);
		colorGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup group, int checkId)
			{
				
				if (checkId == R.id.RadioRed)
				{
					newBtn.setBackgroundResource(R.drawable.redbutton);
					buttonColor = Color.RED;
				}
				else if (checkId == R.id.RadioOrange)
				{
					newBtn.setBackgroundResource(R.drawable.orangebutton);
					buttonColor = Color.BLUE;
				}
				else if (checkId == R.id.RadioGray)
				{
					newBtn.setBackgroundResource(R.drawable.graybutton);
					buttonColor = Color.GRAY;
				}
			}
			
		});
		
		Button createBtn = (Button) dialog.findViewById(R.id.CreateUICreateButton);
		createBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				addButtonToScreen();
				dialog.hide();
				
				// call key binding immediately
				startKeyBinding();
			}
			
		});
		
		Button resetBtn = (Button) dialog.findViewById(R.id.CreateUIResetButton);
		resetBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				buttonLabel.setText("Button");
				newBtn.setText("Button");
				buttonString = "Button";
				buttonColor = Color.GRAY;
				colorGroup.check(R.id.RadioGray);
			}
			
		});
		
		Button cancelBtn = (Button) dialog.findViewById(R.id.CreateUICancelButton);
		cancelBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				dialog.cancel();
			}
			
		});
		
		Button heightUpBtn = (Button) dialog.findViewById(R.id.HeightUpButton);
		heightUpBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				buttonHeight += 10;
				// wrap around to the min height if it goes above max size
				if (buttonHeight > MAX_SIZE)
				{
					buttonHeight = MIN_SIZE;
				}
				updateHeightText();
			}
			
		});
		Button heightDownBtn = (Button) dialog.findViewById(R.id.HeightDownButton);
		heightDownBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				buttonHeight -= 10;
				// wrap around to the max height if it goes below min size
				if (buttonHeight < MIN_SIZE)
				{
					buttonHeight = MAX_SIZE;
				}
				updateHeightText();
			}
			
		});
		Button widthUpBtn = (Button) dialog.findViewById(R.id.WidthUpButton);
		widthUpBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				buttonWidth += 10;
				// wrap around to the max width if it goes above max size
				if (buttonWidth > MAX_SIZE)
				{
					buttonWidth = MIN_SIZE;
				}
				updateWidthText();
			}
			
		});
		Button widthDownBtn = (Button) dialog.findViewById(R.id.WidthDownButton);
		widthDownBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				buttonWidth -= 10;
				// wrap around to the max width if it goes below min size
				if (buttonWidth < MIN_SIZE)
				{
					buttonWidth = MAX_SIZE;
				}
				updateWidthText();
			}
			
		});
		
		// these are called down here to update the initial values
		updateWidthText();
		updateHeightText();
	}
	
	// start key binding activity
	private void startKeyBinding()
	{
		this.startActivityForResult(new Intent(this, KeyBindingActivity.class), KEY_BIND_REQUEST);
	}
	
	// get result back from key binding
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == KEY_BIND_REQUEST)
		{
			if (resultCode == RESULT_OK)
			{
				Bundle bundle = data.getExtras();
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle("Keys bound");
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
	
	// updates the button width text object
	private void updateWidthText()
	{
		TextView widthText = (TextView) dialog.findViewById(R.id.WidthNumber);
		String width = Integer.toString(buttonWidth);
		widthText.setText(width);
	}
	
	// updates the button height text object
	private void updateHeightText()
	{
		TextView heightText = (TextView) dialog.findViewById(R.id.HeightNumber);
		String height = Integer.toString(buttonHeight);
		heightText.setText(height);
	}
	
	// when the user clicks on a button while editButton is true
	// create a dialog screen that gives them options to edit the button
	private void editButtonConfig()
	{
		dialog = new Dialog(CreateUIActivity.this);
		
		dialog.setContentView(R.layout.editbuttonconfig);
		dialog.setTitle("Edit A Button");
		dialog.setCancelable(true);
		
		dialog.show();
		
		Button keybindBtn = (Button) dialog.findViewById(R.id.EditKeybindButton);
		keybindBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				// this allows the user to put in a new keybinding
				// NOT DONE
				dialog.hide();
				startKeyBinding();
			}
			
		});
		
		Button renameBtn = (Button) dialog.findViewById(R.id.EditRenameButton);
		renameBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				// this allows the user to rename a button
				// NOT DONE
				// MIGHT REMOVE
			}
			
		});
		
		Button deleteBtn = (Button) dialog.findViewById(R.id.EditDeleteButton);
		deleteBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				// this deletes the currently selected button
				layout.removeViewAt(currentlySelected);
				dialog.cancel();
			}
			
		});
		
		Button cancelBtn = (Button) dialog.findViewById(R.id.EditCancelButton);
		cancelBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				// closes the dialog
				dialog.cancel();
			}
			
		});
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		int eventaction = event.getAction();
		
		int X = (int) event.getX();
		int Y = (int) event.getY();
		
		switch (eventaction)
		{
			
			case MotionEvent.ACTION_DOWN: // touch down so check if the finger
				// is on a button
				currentlySelected = Integer.MAX_VALUE;
				for (int i = 0; i < layout.getChildCount(); i++)
				{
					View v = layout.getChildAt(i);
					
					// check all the bounds of the button (square)
					if (X > v.getLeft() && X < v.getLeft() + v.getWidth() && Y > v.getTop() + 100 && Y < v.getTop() + v.getHeight() + 100)
					{
						currentlySelected = i;
						break;
					}
					
				}
				
				// if the user is trying to edit a button, but mis clicked, turn
				// off edit button mode
				if (editButton && currentlySelected == Integer.MAX_VALUE)
				{
					editButton = false;
					this.setTitle(layout.getContentDescription());
				}
				// otherwise if the user did click on a button bring up the edit
				// button screen for the selected button
				else if (editButton)
				{
					editButtonConfig();
					editButton = false;
					this.setTitle(layout.getContentDescription());
				}
				
				break;
			
			case MotionEvent.ACTION_MOVE: // touch drag
				// move the buttons the same as the finger
				if (currentlySelected < layout.getChildCount())
				{
					RelativeLayout.LayoutParams par = (LayoutParams) layout.getChildAt(currentlySelected).getLayoutParams();
					par.leftMargin += X - savedX;
					par.topMargin += Y - savedY;
					layout.getChildAt(currentlySelected).setLayoutParams(par);
					
				}
				
				break;
			
			case MotionEvent.ACTION_UP:

				break;
		}
		// save previous position
		savedX = X;
		savedY = Y;
		
		// redraw the canvas
		layout.postInvalidate();
		return true;
	}
	
	public void addButtonToScreen()
	{
		// btn.scrollTo(20, 20);
		if (newBtn != null)
		{
			TextView addedButton = new TextView(CreateUIActivity.this);
			addedButton.setText(buttonString);
			addedButton.setBackgroundColor(buttonColor);
			
			// ImageView addedButton = new ImageView(CreateUIActivity.this);
			// addedButton.setImageBitmap(newBtn.getDrawingCache());
			RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(buttonWidth, buttonHeight);
			layoutParam.topMargin = 0;
			layoutParam.leftMargin = 0;
			
			layout.addView(addedButton, layoutParam);
		}
	}
}
