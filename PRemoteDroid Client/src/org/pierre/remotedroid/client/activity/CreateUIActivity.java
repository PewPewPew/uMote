package org.pierre.remotedroid.client.activity;

import java.util.ArrayList;

import org.pierre.remotedroid.client.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout.LayoutParams;

public class CreateUIActivity extends Activity {
	private static final int KEY_BIND_REQUEST = 0;
	private static final int EDIT_KEY_BIND_REQUEST = 1;
	private static CharSequence EditText = "Select Button To Edit";
	private RelativeLayout layout;

	/** button configuration related **/
	private Dialog dialog;
	AlertDialog.Builder builder;
	AlertDialog alertDialog;
	private EditText buttonLabel;
	private RadioGroup colorGroup;
	// private Button newBtn;
	/** END button configuration related **/

	// ArrayList for key binding
	ArrayList<String> keyArray = new ArrayList<String>();

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

	private EditText inputArea;
	private String uiFileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.uicreation);
		currentlySelected = Integer.MAX_VALUE;
		savedX = 0;
		savedY = 0;

		layout = (RelativeLayout) findViewById(R.id.UICreationLayout);
		// save the title of the menu, used when editButton mode is on
		layout.setContentDescription(this.getTitle());

		editButton = false;
		// dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.createuimenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.AddButton:
			buttonConfigDiag();
			break;
		case R.id.EditButton:
			editButton = true;
			// change the title of the screen so that the user knows when
			// they are in editButton mode
			this.setTitle(EditText);
			break;
		case R.id.ResetUIScreen:
			// remove every thing from the screen and reset all the values
			for (int i = 0; i < layout.getChildCount(); i++) {
				layout.removeViewAt(i);
			}
			currentlySelected = Integer.MAX_VALUE;
			savedX = 0;
			savedY = 0;
			break;
		case R.id.SaveUI:
			// create an alert box is there's no child but still need to
			// save the UI
			if (layout.getChildCount() == 0) {
				builder = new AlertDialog.Builder(CreateUIActivity.this);
				builder
						.setMessage("Are you sure you want to save the UI without any button?");
				builder.setCancelable(false);
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// CreateUIActivity.this.finish();
								saveUIDialog();
							}
						});
				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
				alertDialog = builder.create();
				alertDialog.show();
			} else {
				saveUIDialog();
			}
			break;
		default:
			return super.onOptionsItemSelected(item);

		}
		return true;
	}

	public void saveUIDialog() {
		dialog = new Dialog(CreateUIActivity.this);

		dialog.setContentView(R.layout.uisave);
		dialog.setTitle("Save UI");
		dialog.setCancelable(true);
		dialog.show();

		inputArea = (EditText) dialog.findViewById(R.id.SaveUIInput);
		inputArea.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				uiFileName = inputArea.getText().toString();
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}
		});

		// .setOnKeyListener(new OnKeyListener()
		// {
		// public boolean onKey(View v, int keyCode, KeyEvent event)
		// {
		// uiFileName = ((EditText) v).getText().toString();
		// return false;
		// }
		//
		// });

		Button saveBtn = (Button) dialog.findViewById(R.id.SaveUISaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				saveToDatabase();
				dialog.cancel();
			}

		});

		Button resetBtn = (Button) dialog.findViewById(R.id.SaveUIResetBtn);
		resetBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				inputArea.setText("");
			}

		});

		Button cancelBtn = (Button) dialog.findViewById(R.id.SaveUICancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
			}

		});
	}

	public void saveToDatabase() {
		DBAdapter dba = new DBAdapter(this);
		boolean flag = true;
		for (int i = 0; i < layout.getChildCount(); i++) {
			TextView v = (TextView) layout.getChildAt(i);
			int btnColor = Integer.parseInt(v.getHint().toString());
			String keyString = keyArray.get(i);
			int rtn = dba.insert(uiFileName, i, v.getLeft(), v.getTop(), v
					.getWidth(), v.getHeight(), btnColor, v.getText()
					.toString(), keyString);
			if (rtn == -1) {
				AlertDialog alertInsert = new AlertDialog.Builder(this)
						.create();
				alertInsert.setTitle("Insert");
				alertInsert.setMessage("UI file name already exists");
				alertInsert.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				alertInsert.show();
				flag = false;
				break;
			} else if (rtn == 0) {
				AlertDialog alertInsert = new AlertDialog.Builder(this)
						.create();
				alertInsert.setTitle("Insert");
				alertInsert.setMessage("Insert error.");
				alertInsert.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				alertInsert.show();
				flag = false;
				break;
			}
		}
		if (flag == true)
			Toast.makeText(this, "UI saved successfully", Toast.LENGTH_LONG)
					.show();
	}

	public void buttonConfigDiag() {
		buttonWidth = MIN_SIZE;
		buttonHeight = MIN_SIZE;

		buttonString = "Button";
		buttonColor = R.drawable.graybutton;

		dialog = new Dialog(CreateUIActivity.this);

		dialog.setContentView(R.layout.buttonconfig);
		dialog.setTitle("Create a Button");
		dialog.setCancelable(true);

		dialog.show();

		// newBtn = (Button) dialog.findViewById(R.id.ButtonSample);
		// newBtn.setDrawingCacheEnabled(true);

		buttonLabel = (EditText) dialog
				.findViewById(R.id.ButtonSampleLabelInput);
		buttonLabel.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				buttonString = buttonLabel.getText().toString();
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}
		});
		// .setOnKeyListener(new OnKeyListener()
		// {
		// public boolean onKey(View v, int keyCode, KeyEvent event)
		// {
		// // newBtn.setText(((EditText) v).getText());
		// buttonString = ((EditText) v).getText().toString();
		// return false;
		// }
		//
		// });

		colorGroup = (RadioGroup) dialog.findViewById(R.id.ColorRadioGroup);
		colorGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkId) {

				if (checkId == R.id.RadioRed) {
					buttonColor = R.drawable.redbutton;
				} else if (checkId == R.id.RadioOrange) {
					buttonColor = R.drawable.orangebutton;
				} else if (checkId == R.id.RadioGray) {
					buttonColor = R.drawable.graybutton;
				}
			}

		});

		Button createUICreateButton = (Button) dialog
				.findViewById(R.id.CreateUICreateButton);
		createUICreateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addButtonToScreen();
				dialog.hide();

				// call key binding immediately
				startKeyBinding(KEY_BIND_REQUEST);
			}

		});

		Button createUIResetBtn = (Button) dialog
				.findViewById(R.id.CreateUIResetButton);
		createUIResetBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				buttonLabel.setText("Button");
				buttonString = "Button";
				buttonColor = R.drawable.graybutton;
				colorGroup.check(R.id.RadioGray);
			}

		});

		Button createUICancelBtn = (Button) dialog
				.findViewById(R.id.CreateUICancelButton);
		createUICancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
			}

		});

		Button heightUpBtn = (Button) dialog.findViewById(R.id.HeightUpButton);
		heightUpBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				buttonHeight += 10;
				// wrap around to the min height if it goes above max size
				if (buttonHeight > MAX_SIZE) {
					buttonHeight = MIN_SIZE;
				}
				updateHeightText();
			}

		});
		Button heightDownBtn = (Button) dialog
				.findViewById(R.id.HeightDownButton);
		heightDownBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				buttonHeight -= 10;
				// wrap around to the max height if it goes below min size
				if (buttonHeight < MIN_SIZE) {
					buttonHeight = MAX_SIZE;
				}
				updateHeightText();
			}

		});
		Button widthUpBtn = (Button) dialog.findViewById(R.id.WidthUpButton);
		widthUpBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				buttonWidth += 10;
				// wrap around to the max width if it goes above max size
				if (buttonWidth > MAX_SIZE) {
					buttonWidth = MIN_SIZE;
				}
				updateWidthText();
			}

		});
		Button widthDownBtn = (Button) dialog
				.findViewById(R.id.WidthDownButton);
		widthDownBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				buttonWidth -= 10;
				// wrap around to the max width if it goes below min size
				if (buttonWidth < MIN_SIZE) {
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
	private void startKeyBinding(int requestNum) {
		this.startActivityForResult(new Intent(this, KeyBindingActivity.class),
				requestNum);
	}

	// get result back from key binding
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == KEY_BIND_REQUEST) {
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				AlertDialog alertDialog = new AlertDialog.Builder(this)
						.create();
				alertDialog.setTitle("Keys bound");

				String keyStr = bundle.getString("keys"); // this is the key
				// binding string
				// returned
				// add the key binding to array to save
				keyArray.add(layout.getChildCount() - 1, keyStr);
				String debugMessage = "";
				for (int i = 0; i < layout.getChildCount(); i++) {
					debugMessage += keyArray.get(i);
				}
				alertDialog.setMessage(debugMessage);
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				alertDialog.show();
			}
		} else if (requestCode == EDIT_KEY_BIND_REQUEST) {
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				AlertDialog alertDialog = new AlertDialog.Builder(this)
						.create();
				alertDialog.setTitle("Keys bound");

				String keyStr = bundle.getString("keys"); // this is the key
				// binding string
				// returned
				// edit the element in the key binding array
				keyArray.set(currentlySelected, keyStr);
				String debugMessage = "";
				for (int i = 0; i < layout.getChildCount(); i++) {
					debugMessage += keyArray.get(i);
				}
				alertDialog.setMessage(debugMessage);
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				alertDialog.show();
			}
		}
	}

	// updates the button width text object
	private void updateWidthText() {
		TextView widthText = (TextView) dialog.findViewById(R.id.WidthNumber);
		String width = Integer.toString(buttonWidth);
		widthText.setText(width);
	}

	// updates the button height text object
	private void updateHeightText() {
		TextView heightText = (TextView) dialog.findViewById(R.id.HeightNumber);
		String height = Integer.toString(buttonHeight);
		heightText.setText(height);
	}

	// when the user clicks on a button while editButton is true
	// create a dialog screen that gives them options to edit the button
	private void editButtonConfig() {
		dialog = new Dialog(CreateUIActivity.this);

		dialog.setContentView(R.layout.editbuttonconfig);
		dialog.setTitle("Edit A Button");
		dialog.setCancelable(true);

		dialog.show();

		Button keybindBtn = (Button) dialog
				.findViewById(R.id.EditKeybindButton);
		keybindBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// this allows the user to put in a new keybinding
				// NOT DONE
				dialog.hide();
				startKeyBinding(EDIT_KEY_BIND_REQUEST);
			}

		});

		Button renameBtn = (Button) dialog.findViewById(R.id.EditRenameButton);
		renameBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// this allows the user to rename a button
				// NOT DONE
				dialog.hide();
				dialog.setContentView(R.layout.renamebutton);
				EditText text = (EditText) dialog
						.findViewById(R.id.RenameButtonSampleInput);
				text.setText(((TextView) layout.getChildAt(currentlySelected))
						.getText());
				dialog.setTitle("Rename A Button");
				dialog.setCancelable(true);

				Button renameOKBtn = (Button) dialog
						.findViewById(R.id.RenameOKButton);
				renameOKBtn.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						EditText text = (EditText) dialog
								.findViewById(R.id.RenameButtonSampleInput);
						((TextView) layout.getChildAt(currentlySelected))
								.setText(text.getText());
						dialog.hide();
					}

				});

				Button renameCancelBtn = (Button) dialog
						.findViewById(R.id.RenameCancelButton);
				renameCancelBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog.cancel();
					}

				});

				dialog.show();
			}

		});

		Button deleteBtn = (Button) dialog.findViewById(R.id.EditDeleteButton);
		deleteBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// this deletes the currently selected button
				layout.removeViewAt(currentlySelected);
				keyArray.remove(currentlySelected);

				// if there's no button on the screen, disable the save button
				if (layout.getChildCount() == 0) {

				}

				dialog.cancel();
			}

		});

		Button cancelBtn = (Button) dialog.findViewById(R.id.EditCancelButton);
		cancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// closes the dialog
				dialog.cancel();
			}

		});
	}

	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();

		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN: // touch down so check if the finger
			// is on a button
			currentlySelected = Integer.MAX_VALUE;
			for (int i = 0; i < layout.getChildCount(); i++) {
				View v = layout.getChildAt(i);

				// check all the bounds of the button (square)
				if (X > v.getLeft() && X < v.getLeft() + v.getWidth()
						&& Y > v.getTop() + 50
						&& Y < v.getTop() + v.getHeight() + 50) {
					currentlySelected = i;
					break;
				}

			}

			// if the user is trying to edit a button, but mis clicked, turn
			// off edit button mode
			if (editButton && currentlySelected == Integer.MAX_VALUE) {
				editButton = false;
				this.setTitle(layout.getContentDescription());
			}
			// otherwise if the user did click on a button bring up the edit
			// button screen for the selected button
			else if (editButton) {
				editButtonConfig();
				editButton = false;
				this.setTitle(layout.getContentDescription());
			}

			break;

		case MotionEvent.ACTION_MOVE: // touch drag
			// move the buttons the same as the finger
			if (currentlySelected < layout.getChildCount()) {
				RelativeLayout.LayoutParams par = (LayoutParams) layout
						.getChildAt(currentlySelected).getLayoutParams();
				par.leftMargin += X - savedX;
				/*
				 * // Grid snapping feature for x if (Math.abs(par.leftMargin -
				 * 10) < 10) { par.leftMargin = 10; } else if
				 * (Math.abs(par.leftMargin - 110) < 10) { par.leftMargin = 110;
				 * } else if (Math.abs(par.leftMargin - 210) < 10) {
				 * par.leftMargin = 210; }
				 */
				par.topMargin += Y - savedY;
				/*
				 * // Grid snapping feature for y if (Math.abs(par.topMargin -
				 * 10) < 10) { par.topMargin = 10; } else if
				 * (Math.abs(par.topMargin - 110) < 10) { par.topMargin = 110; }
				 * else if (Math.abs(par.topMargin - 210) < 10) { par.topMargin
				 * = 210; } else if (Math.abs(par.topMargin - 310) < 10) {
				 * par.topMargin = 310; }
				 */
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

	public void addButtonToScreen() {
		// btn.scrollTo(20, 20);
		// if (newBtn != null)
		// {
		// TextView addedButton = new TextView(CreateUIActivity.this);
		// addedButton.setText(buttonString);
		// addedButton.setBackgroundColor(buttonColor);
		//
		// // Please don't remove the following two lines, it is used for data
		// // saving.
		// CharSequence colorHint = Integer.toString(buttonColor);
		// addedButton.setHint(colorHint);
		//
		// // ImageView addedButton = new ImageView(CreateUIActivity.this);
		// // addedButton.setImageBitmap(newBtn.getDrawingCache());
		// RelativeLayout.LayoutParams layoutParam = new
		// RelativeLayout.LayoutParams(buttonWidth, buttonHeight);
		// layoutParam.topMargin = 0;
		// layoutParam.leftMargin = 0;
		//
		// layout.addView(addedButton, layoutParam);
		// }
		TextView addedButton = new TextView(CreateUIActivity.this);
		addedButton.setText(buttonString);
		addedButton.setBackgroundResource(buttonColor);
		// Please do not remove the following two lines, it is used for save and
		// retrieving color
		CharSequence colorHint = Integer.toString(buttonColor);
		addedButton.setHint(colorHint);

		addedButton.setGravity(Gravity.CENTER_VERTICAL
				| Gravity.CENTER_HORIZONTAL);
		RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(
				buttonWidth, buttonHeight);
		layoutParam.topMargin = 20;
		layoutParam.leftMargin = 20;

		layout.addView(addedButton, layoutParam);
	}
}
