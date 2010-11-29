package org.pierre.remotedroid.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ForegroundProgramResponseAction extends PRemoteDroidAction
{
	public String foregroundProgram;
	
	public ForegroundProgramResponseAction(String foregroundProgram)
	{
		this.foregroundProgram = foregroundProgram;
	}
	
	public static ForegroundProgramResponseAction parse(DataInputStream dis) throws IOException
	{
		String foregroundProgram = dis.readUTF();
		return new ForegroundProgramResponseAction(foregroundProgram);
	}
	
	public void toDataOutputStream(DataOutputStream dos) throws IOException
	{
		dos.writeByte(FOREGROUND_PROGRAM_RESPONSE);
		dos.writeUTF(this.foregroundProgram);
	}
}
