package org.pierre.remotedroid.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UMoteRemoteAction extends PRemoteDroidAction
{
	
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
