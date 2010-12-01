package org.pierre.remotedroid.server.tools;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class UMoteToSwingKeyCodeConverter
{
	public static HashMap<String, Integer> conversionTable = new HashMap<String, Integer>();
	static
	{
		conversionTable.put("Ctrl", new Integer(KeyEvent.VK_CONTROL));
		conversionTable.put("Shift", new Integer(KeyEvent.VK_SHIFT));
		conversionTable.put("Alt", new Integer(KeyEvent.VK_ALT));
		conversionTable.put("Space", new Integer(KeyEvent.VK_SPACE));
		conversionTable.put("Delete", new Integer(KeyEvent.VK_DELETE));
		conversionTable.put("Tab", new Integer(KeyEvent.VK_TAB));
		conversionTable.put("Enter", new Integer(KeyEvent.VK_ENTER));
		conversionTable.put("Backspace", new Integer(KeyEvent.VK_BACK_SPACE));
		conversionTable.put("F1", new Integer(KeyEvent.VK_F1));
		conversionTable.put("F2", new Integer(KeyEvent.VK_F2));
		conversionTable.put("F3", new Integer(KeyEvent.VK_F3));
		conversionTable.put("F4", new Integer(KeyEvent.VK_F4));
		conversionTable.put("F5", new Integer(KeyEvent.VK_F5));
		conversionTable.put("F6", new Integer(KeyEvent.VK_F6));
		conversionTable.put("F7", new Integer(KeyEvent.VK_F7));
		conversionTable.put("F8", new Integer(KeyEvent.VK_F8));
		conversionTable.put("F9", new Integer(KeyEvent.VK_F9));
		conversionTable.put("F10", new Integer(KeyEvent.VK_F10));
		conversionTable.put("F11", new Integer(KeyEvent.VK_F11));
		conversionTable.put("F12", new Integer(KeyEvent.VK_F12));
		conversionTable.put("PrtScn", new Integer(KeyEvent.VK_PRINTSCREEN));
		conversionTable.put("ScrLk", new Integer(KeyEvent.VK_SCROLL_LOCK));
		conversionTable.put("Pause", new Integer(KeyEvent.VK_PAUSE));
		conversionTable.put("Esc", new Integer(KeyEvent.VK_ESCAPE));
		conversionTable.put("Home", new Integer(KeyEvent.VK_HOME));
		conversionTable.put("End", new Integer(KeyEvent.VK_END));
		conversionTable.put("Insert", new Integer(KeyEvent.VK_INSERT));
		conversionTable.put("PageUp", new Integer(KeyEvent.VK_PAGE_UP));
		conversionTable.put("PageDown", new Integer(KeyEvent.VK_PAGE_DOWN));
		conversionTable.put("Up", new Integer(KeyEvent.VK_UP));
		conversionTable.put("Down", new Integer(KeyEvent.VK_DOWN));
		conversionTable.put("Left", new Integer(KeyEvent.VK_LEFT));
		conversionTable.put("Right", new Integer(KeyEvent.VK_RIGHT));
		conversionTable.put("CapsLock", new Integer(KeyEvent.VK_CAPS_LOCK));
		conversionTable.put("Win", new Integer(KeyEvent.VK_WINDOWS));
		conversionTable.put("F1", new Integer(KeyEvent.VK_F1));
	}
	
	public static final int NO_SWING_KEYCODE = -1;
	
	public static int convertSpecial(String specialChar)
	{
		if (conversionTable.containsKey(specialChar))
		{
			return conversionTable.get(specialChar).intValue();
		}
		else
		{
			return NO_SWING_KEYCODE;
		}
	}
}
