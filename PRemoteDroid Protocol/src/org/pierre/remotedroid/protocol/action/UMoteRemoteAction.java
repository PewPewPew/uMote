package org.pierre.remotedroid.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UMoteRemoteAction extends PRemoteDroidAction
{
	public ArrayList<Integer> keys;
	public ArrayList<String> specialKeys;
	
	public UMoteRemoteAction(ArrayList<Integer> keys, ArrayList<String> specialKeys)
	{
		this.keys = keys;
		this.specialKeys = specialKeys;
	}
	
	public static UMoteRemoteAction parse(DataInputStream dis) throws IOException
	{
		int keysSize = dis.readInt();
		ArrayList<Integer> keys = new ArrayList<Integer>(keysSize);
		for (int i = 0; i < keysSize; i++)
		{
			keys.add(new Integer(dis.readInt()));
		}
		
		int specialKeysSize = dis.readInt();
		ArrayList<String> specialKeys = new ArrayList<String>(specialKeysSize);
		for (int i = 0; i < specialKeysSize; i++)
		{
			specialKeys.add(dis.readUTF());
		}
		
		return new UMoteRemoteAction(keys, specialKeys);
	}
	
	public void toDataOutputStream(DataOutputStream dos) throws IOException
	{
		dos.writeByte(UMOTE_REMOTE);
		
		dos.writeInt(this.keys.size());
		for (Integer key : this.keys)
		{
			dos.writeInt(key.intValue());
		}
		
		dos.writeInt(this.specialKeys.size());
		for (String specialKey : this.specialKeys)
		{
			dos.writeUTF(specialKey);
		}
	}
}
