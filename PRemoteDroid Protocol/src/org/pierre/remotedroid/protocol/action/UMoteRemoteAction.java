package org.pierre.remotedroid.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UMoteRemoteAction extends PRemoteDroidAction
{
	
	public String keys;
	
	public UMoteRemoteAction(String password)
	{
		this.keys = password;
	}
	
	public static AuthentificationAction parse(DataInputStream dis) throws IOException
	{
		String password = dis.readUTF();
		
		return new AuthentificationAction(password);
	}
	
	public void toDataOutputStream(DataOutputStream dos) throws IOException
	{
		dos.writeByte(AUTHENTIFICATION);
		dos.writeUTF(this.keys);
	}
	
}
