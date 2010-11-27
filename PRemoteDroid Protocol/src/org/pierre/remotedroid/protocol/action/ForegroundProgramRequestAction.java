package org.pierre.remotedroid.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ForegroundProgramRequestAction extends PRemoteDroidAction
{
	public ForegroundProgramRequestAction()
	{
	}
	
	public static ForegroundProgramRequestAction parse(DataInputStream dis) throws IOException
	{
		return new ForegroundProgramRequestAction();
	}
	
	public void toDataOutputStream(DataOutputStream dos) throws IOException
	{
		// THIS IS A COMMENT!
		dos.writeByte(FOREGROUND_PROGRAM_REQUEST);
	}
}
