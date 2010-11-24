package org.pierre.remotedroid.client.activity;

import org.pierre.remotedroid.client.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CreateUIActivity extends Activity
{
	
	private static CharSequence ExitText = "Return to uMote";
	
	private FrameLayout layout;
	
	/** button configuration related **/
	private Dialog dialog;
	private Button newBtn;
	// private Button addedButton;
	private EditText buttonLabel;
	private RadioGroup colorGroup;
	/** END button configuration related **/
	
	private int status;
	private final static int START_DRAGGING = 0;
	private final static int STOP_DRAGGING = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uicreation);
		
		layout = (FrameLayout) findViewById(R.id.UICreationLayout);
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
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public void buttonConfigDiag()
	{
		dialog = new Dialog(CreateUIActivity.this);
		
		dialog.setContentView(R.layout.buttonconfig);
		dialog.setTitle("Create a Button");
		dialog.setCancelable(true);
		
		dialog.show();
		
		newBtn = (Button) dialog.findViewById(R.id.ButtonSample);
		
		buttonLabel = (EditText) dialog.findViewById(R.id.ButtonSampleLabelInput);
		buttonLabel.setOnKeyListener(new OnKeyListener()
		{
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				
				newBtn.setText(((EditText) v).getText());
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
				}
				else if (checkId == R.id.RadioOrange)
				{
					newBtn.setBackgroundResource(R.drawable.orangebutton);
				}
				else if (checkId == R.id.RadioGray)
				{
					newBtn.setBackgroundResource(R.drawable.graybutton);
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
			}
			
		});
		
		Button resetBtn = (Button) dialog.findViewById(R.id.CreateUIResetButton);
		resetBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				buttonLabel.setText("Button");
				newBtn.setText("Button");
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
		
	}
	
	public void addButtonToScreen()
	{
		// btn.scrollTo(20, 20);
		if (newBtn != null)
		{
			Button addedButton = new Button(CreateUIActivity.this);
			addedButton.setBackgroundDrawable(newBtn.getBackground());
			addedButton.setText(newBtn.getText());
			FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(90, 38);
			layoutParam.topMargin = 50;
			layoutParam.leftMargin = 100;
			addedButton.setLayoutParams(layoutParam);
			
			addedButton.setPadding(50, 100, 0, 0);
			
			addedButton.setOnTouchListener(new OnTouchListener()
			{
				// FrameLayout.LayoutParams position = new
				// FrameLayout.LayoutParams(90, 38);
				
				public boolean onTouch(View view, MotionEvent motion)
				{
					if (motion.getAction() == MotionEvent.ACTION_DOWN)
					{
						status = START_DRAGGING;
					}
					else if (motion.getAction() == MotionEvent.ACTION_UP)
					{
						status = STOP_DRAGGING;
					}
					else if (motion.getAction() == MotionEvent.ACTION_MOVE)
					{
						if (status == START_DRAGGING)
						{
							System.out.println("Dragging");
							// position.topMargin = (int) motion.getX();
							// position.leftMargin = (int) motion.getY();
							// ((Button) view).setLayoutParams(position);
							view.setPadding((int) motion.getX(), (int) motion.getY(), 0, 0);
						}
					}
					return false;
				}
				
			});
			
			layout.addView(addedButton);
		}
	}
}
