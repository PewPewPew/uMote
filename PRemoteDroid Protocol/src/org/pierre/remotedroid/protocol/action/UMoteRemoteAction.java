package org.pierre.remotedroid.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UMoteRemoteAction extends PRemoteDroidAction
{
	public static final byte CTRL = 0;
	public static final byte ALT = 1;
	public static final byte SHIFT = 2;
	public static final byte CAPS_LOCK = 3;
	public static final byte TAB = 4;
	public static final byte ESC = 5;
	public static final byte F1 = 6;
	public static final byte F2 = 7;
	public static final byte F3 = 8;
	public static final byte F4 = 9;
	public static final byte F5 = 10;
	public static final byte F6 = 11;
	public static final byte F7 = 12;
	public static final byte F8 = 13;
	public static final byte F9 = 14;
	public static final byte F10 = 15;
	public static final byte F11 = 16;
	public static final byte F12 = 17;
	public static final byte INSERT = 18;
	public static final byte DELETE = 19;
	public static final byte HOME = 20;
	public static final byte PG_UP = 21;
	public static final byte PG_DN = 22;
	public static final byte BACKSPACE = 23;
	public static final byte ARROW_UP = 24;
	public static final byte ARROW_DOWN = 25;
	public static final byte ARROW_LEFT = 26;
	public static final byte ARROW_RIGHT = 27;
	public static final byte WINDOWS = 28;
	
	public int keys[];
	public byte specialKeys[];
	
	public UMoteRemoteAction(int keys[], byte specialKeys[])
	{
		this.keys = keys;
		this.specialKeys = specialKeys;
	}
	
	public static UMoteRemoteAction parse(DataInputStream dis) throws IOException
	{
		int keysSize = dis.readInt();
		int[] keys = new int[keysSize];
		for (int i = 0; i < keysSize; i++)
		{
			keys[i] = dis.readInt();
		}
		
		int specialKeysSize = dis.readInt();
		byte[] specialKeys = new byte[specialKeysSize];
		for (int i = 0; i < specialKeysSize; i++)
		{
			keys[i] = dis.readByte();
		}
		
		return new UMoteRemoteAction(keys, specialKeys);
	}
	
	public void toDataOutputStream(DataOutputStream dos) throws IOException
	{
		dos.writeByte(UMOTE_REMOTE);
		
		dos.writeInt(this.keys.length);
		for (int key : this.keys)
		{
			dos.writeInt(key);
		}
		
		dos.writeInt(this.specialKeys.length);
		for (byte specialKey : this.specialKeys)
		{
			dos.writeByte(specialKey);
		}
	}
}
