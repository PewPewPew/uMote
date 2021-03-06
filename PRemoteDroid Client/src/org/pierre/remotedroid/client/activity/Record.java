package org.pierre.remotedroid.client.activity;

public class Record
{
	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	// private int grid;
	private int color;
	private String label;
	private String keyBinding;
	
	// private String icon;
	
	public Record(int id, int x, int y, int width, int height, int color, String label, String keyBinding)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		// this.grid = grid;
		this.color = color;
		this.label = label;
		this.keyBinding = keyBinding;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	// public int getGrid(){
	// return this.grid;
	// }
	
	public int getColor()
	{
		return this.color;
	}
	
	public String getLabel()
	{
		return this.label;
	}
	
	public String getKeyBinding()
	{
		return this.keyBinding;
	}
}
