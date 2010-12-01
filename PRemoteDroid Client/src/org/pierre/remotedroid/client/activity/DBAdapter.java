package org.pierre.remotedroid.client.activity;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter
{
	
	private static final String DB_NAME = "umote";
	private static final int DB_VERSION = 1;
	
	private static final String KEY_NAME = "id";
	private static final String KEY_X = "x";
	private static final String KEY_Y = "y";
	private static final String KEY_WIDTH = "width";
	private static final String KEY_HEIGHT = "height";
	// private static final String KEY_GRID = "grid";
	private static final String KEY_COLOR = "color";
	private static final String KEY_LABEL = "label";
	private static final String KEY_KEYBINDING = "keybinding";
	
	private static final String TAG = "DBAdapter";
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	// Constructor
	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	// Check if the table exists, if not, create it
	private void checkTable(String tableName)
	{
		final String DB_CREATE = "CREATE TABLE IF NOT EXISTS " + tableName + "(" + KEY_NAME + " INTEGER PRIMARY KEY, " + KEY_X + " INTEGER NOT NULL, " + KEY_Y + " INTEGER NOT NULL, " + KEY_WIDTH + " INTEGER NOT NULL, " + KEY_HEIGHT + " INTEGER NOT NULL, "
		// + KEY_GRID + " INTEGER NOT NULL, "
		        + KEY_COLOR + " INTEGER NOT NULL, " + KEY_LABEL + " TEXT NOT NULL, " + KEY_KEYBINDING + " TEXT);";
		
		this.open();
		db.execSQL(DB_CREATE);
		this.close();
	}
	
	// Open the database
	private DBAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database
	private void close()
	{
		DBHelper.close();
	}
	
	// Insert an entry into a table
	public void insert(String layoutName, int btnName, int x, int y, int width, int height, int color, String label, String keyBinding)
	{
		
		checkTable(layoutName);
		
		this.open();
		ContentValues uiValues = new ContentValues();
		uiValues.put(KEY_NAME, btnName);
		uiValues.put(KEY_X, x);
		uiValues.put(KEY_Y, y);
		uiValues.put(KEY_WIDTH, width);
		uiValues.put(KEY_HEIGHT, height);
		// uiValues.put(KEY_GRID, grid);
		uiValues.put(KEY_COLOR, color);
		uiValues.put(KEY_LABEL, label);
		uiValues.put(KEY_KEYBINDING, keyBinding);
		if (db.insert(layoutName, null, uiValues) == -1L)
			Toast.makeText(context, btnName + " already exists.", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(context, btnName + " saved.", Toast.LENGTH_SHORT).show();
		this.close();
	}
	
	// Search for all the records in a table
	public ArrayList<Record> query(String layoutName) throws SQLException
	{
		ArrayList<Record> results = new ArrayList<Record>();
		final String querySQL = "SELECT * FROM " + layoutName + ";";
		
		this.open();
		Cursor c = db.rawQuery(querySQL, null);
		if (c.getCount() == 0)
		{
			this.close();
			return null;
		}
		
		while (c.moveToNext())
		{
			Record temp = new Record(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getString(6), c.getString(7));
			results.add(temp);
		}
		this.close();
		return results;
	}
	
	// Drop a table
	public void drop(String layoutName)
	{
		final String dropSQL = "DROP TABLE IF EXISTS " + layoutName + ";";
		
		this.open();
		try
		{
			db.execSQL(dropSQL);
		}
		catch (SQLException e)
		{
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		this.close();
	}
	
	// Delete a row in a table
	public void delete(String layoutName, String btnName)
	{
		this.open();
		final String where = KEY_NAME + " = '" + btnName + "';";
		try
		{
			if (db.delete(layoutName, where, null) == 0)
				Toast.makeText(context, btnName + " does not exist.", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(context, btnName + " has been delete.", Toast.LENGTH_SHORT).show();
		}
		catch (SQLiteException e)
		{
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		this.close();
	}
	
	// Update a value in row in a table
	public void update(String layoutName, String btnName, String attrName, String newAttrValue)
	{
		this.open();
		ContentValues uiValues = new ContentValues();
		uiValues.put(attrName, newAttrValue);
		final String where = KEY_NAME + " = '" + btnName + "'";
		try
		{
			if (db.update(layoutName, uiValues, where, null) == 0)
				Toast.makeText(context, btnName + " does not exist.", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(context, btnName + " has been updated.", Toast.LENGTH_SHORT).show();
		}
		catch (SQLiteException e)
		{
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		this.close();
	}
	
	// List all the tables' name
	public ArrayList<String> listTables()
	{
		ArrayList<String> tables = new ArrayList<String>();
		final String listSQL = "SELECT NAME FROM SQLITE_MASTER WHERE TYPE='table';";
		
		this.open();
		Cursor c = db.rawQuery(listSQL, null);
		if (c.getCount() == 0)
		{
			this.close();
			return null;
		}
		
		while (c.moveToNext())
		{
			tables.add(c.getString(0));
		}
		tables.remove(0);
		this.close();
		return tables;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		// private SQLiteDatabase db;
		
		public DatabaseHelper(Context context)
		{
			super(context, DB_NAME, null, DB_VERSION);
		}
		
		// public void createDB() {
		// db.execSQL(DB_CREATE);
		// }
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			// this.db = db;
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			// this.createDB();
		}
	}
}
