package org.chris.umote.server.win32;

import java.io.File;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

/**
 * This class is used to encapsulate platform independent calls to native
 * operating systems through JNA, or other native platform technologies.
 * <p>
 * JAN is the Java Native Access library supplied via <a
 * href="http://jna.dev.java.net" target="_blank">JNA Home</a>
 * <p>
 * JNA provides Java programs easy access to native shared libraries on multiple
 * operating systems without writing anything but Java code—no JNI or native
 * code is required. This functionality is comparable to Windows'
 * Platform/Invoke and Python's ctypes. Access is dynamic at runtime without
 * code generation.
 * <p>
 * SAFS is now delivered with the core JNA.ZIP(JAR). Other JNA support libraries
 * may be added as needed.
 * 
 * @author canagl
 * @author CANAGL Jun 03, 2009 Added GetProcessUIResourceCount CANAGL Aug 07,
 *         2009 Added GetProcessFileName CANAGL SEP 14, 2009 Refactored with WIN
 *         classes. CANAGL DEC 15, 2009 Added GetRegistryKeyValue and
 *         DoesRegistryKeyExists routines. JunwuMa JUL 23, 2010 Updated to get
 *         GetRegistryKeyValue support Win7.
 * 
 * @since 2009.02.03
 */
public class NativeWrapper
{
	
	/** -99 */
	public static final int NO_RESULT = -99;
	/** "Vector" */
	public static final String VECTOR_KEY = "Vector";
	/** "Result" */
	public static final String RESULT_KEY = "Result";
	
	/**
	 * Platform independent entry-point to receive the ID or HANDLE of the
	 * current foreground window. The GetForegroundWindow function returns a
	 * "handle" to the foreground window--the window with which the user is
	 * currently working.
	 * <p>
	 * For windows NOTE: WIN32: we will return a Long representing the Handle
	 * (HWND), or null on error. NOTE: Support for other Platforms will be added
	 * as needed.
	 * 
	 * @return
	 * @see org.safs.natives.win32.User32#GetForegroundWindow()
	 */
	public static Object GetForegroundWindow()
	{
		Object rc = null;
		
		if (Platform.isWindows())
		{
			NativeLong nl = null;
			try
			{
				nl = User32.INSTANCE.GetForegroundWindow();
				rc = new Long(nl.longValue());
			}
			catch (Exception x)
			{
			}
			catch (Error x)
			{
			}
		}
		return rc;
	}
	
	/**
	 * The GetWindowThreadProcessId function retrieves the identifier of the
	 * thread that created the specified window and, optionally, the identifier
	 * of the process that created the window.
	 * 
	 * @param parent
	 *            - handle to the parent. For WIN32, this is a Long.
	 * @return Object[2] [0]=ThreadID, [1]=ProcessID. For WIN32 these are both
	 *         Integers. Array values must be <> 0 to be considered valid.
	 */
	public static Object[] GetWindowThreadProcessId(Object parent)
	{
		Object[] rc = new Object[2];
		rc[0] = new Integer(0);
		rc[1] = new Integer(0);
		if (Platform.isWindows())
		{
			NativeLong lparent = new NativeLong(((Long) parent).longValue());
			Memory pidOut = new Memory(4);
			pidOut.clear();
			int thrid = 0;
			try
			{
				thrid = User32.INSTANCE.GetWindowThreadProcessId(lparent, pidOut);
				rc[0] = new Integer(thrid);
				rc[1] = new Integer(pidOut.getInt(0));
			}
			catch (Exception x)
			{
				System.out.println("Exception received: " + x.getClass().getSimpleName());
				x.printStackTrace();
			}
			catch (Error x)
			{
				System.out.println("Error received: " + x.getClass().getSimpleName());
				x.printStackTrace();
			}
		}
		return rc;
	}
	
	/**
	 * Retrieve the name of the executable file for the specified process. The
	 * returned value should not include path information to the executable
	 * filename. NOTE: Support for non-WIN32 platforms will be added when able.
	 * 
	 * @param processID
	 *            -- the id of the process to query. For WIN32 this is an
	 *            Integer.
	 * @return Object -- the name of the process or NULL. For WIN32 this is a
	 *         String.
	 * @see org.safs.natives.win32.Kernel32#GetProcessImageFileNameA(Pointer,
	 *      Pointer, int)
	 * @see org.safs.natives.win32.Psapi#GetProcessImageFileNameA(Pointer,
	 *      Pointer, int)
	 */
	public static Object GetProcessFileName(Object processID)
	{
		if (processID == null)
		{
			return null;
		}
		if (Platform.isWindows())
		{
			Pointer pHandle = null;
			try
			{
				int id = ((Integer) processID).intValue();
				// 0x0400 is PROCESS_QUERY_INFORMATION -- valid for pre-Vista OS
				// may not be valid for Vista and beyond?
				pHandle = Kernel32.INSTANCE.OpenProcess(0x0400, false, id);
				if (pHandle == null)
				{
					return null;
				}
				int size = 0;
				int plen = 4096;
				Memory lpFileName = new Memory(plen);
				if (lpFileName.isValid())
				{
					lpFileName.clear();
				}
				else
				{
					Kernel32.INSTANCE.CloseHandle(pHandle);
					return null;
				}
				try
				{
					size = Psapi.INSTANCE.GetProcessImageFileNameA(pHandle, lpFileName, plen);
				}
				catch (Throwable t)
				{
					try
					{
						size = Kernel32.INSTANCE.GetProcessImageFileNameA(pHandle, lpFileName, plen);
					}
					catch (Throwable t2)
					{
					}
				}
				if (pHandle != null)
					Kernel32.INSTANCE.CloseHandle(pHandle);
				pHandle = null;
				if (size > 0)
				{
					String rc = new String((String) lpFileName.getString(0));
					// Log.info("NativeWrapper.getProcessFileName returning String len: "+
					// rc.length()+ ", "+ rc);
					int i = rc.lastIndexOf(File.separatorChar);
					if (i > -1)
						rc = rc.substring(i + 1);
					return rc;
				}
			}
			catch (Throwable x)
			{
				// System.out.println("Exception received: "+
				// x.getClass().getSimpleName());
				// x.printStackTrace();
				try
				{
					if (pHandle != null)
						Kernel32.INSTANCE.CloseHandle(pHandle);
				}
				catch (Throwable x2)
				{
				}
			}
			if (pHandle != null)
				try
				{
					Kernel32.INSTANCE.CloseHandle(pHandle);
				}
				catch (Throwable x)
				{
				}
			pHandle = null;
		}
		return null;
	}
	
	/**
	 * Simple regression tests with output to System.out
	 * <p>
	 * java org.safs.natives.NativeWrapper > outputFile.txt
	 * <p>
	 * 
	 * @param args
	 *            --accepts none
	 */
	public static String getForegroundProgram()
	{
		Long foregroundPointer = (Long) GetForegroundWindow();
		Object[] pids = GetWindowThreadProcessId(foregroundPointer);
		String procname = (String) GetProcessFileName(pids[1]);
		return procname;
	}
}
