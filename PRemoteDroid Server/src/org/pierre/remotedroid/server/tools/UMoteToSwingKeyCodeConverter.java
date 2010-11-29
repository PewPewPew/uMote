package org.pierre.remotedroid.server.tools;

import java.awt.event.KeyEvent;

import org.pierre.remotedroid.protocol.action.UMoteRemoteAction;

public class UMoteToSwingKeyCodeConverter
{
	public static final int NO_SWING_KEYCODE = -1;
	
	public static int convertSpecial(byte specialChar)
	{
		switch (specialChar)
		{
			case UMoteRemoteAction.CTRL:
				return KeyEvent.VK_CONTROL;
			case UMoteRemoteAction.ALT:
				return KeyEvent.VK_ALT;
			case UMoteRemoteAction.SHIFT:
				return KeyEvent.SHIFT_DOWN_MASK;
			case UMoteRemoteAction.CAPS_LOCK:
				return KeyEvent.VK_CAPS_LOCK;
			case UMoteRemoteAction.TAB:
				return KeyEvent.VK_TAB;
			case UMoteRemoteAction.ESC:
				return KeyEvent.VK_ESCAPE;
			case UMoteRemoteAction.F1:
				return KeyEvent.VK_F1;
			case UMoteRemoteAction.F2:
				return KeyEvent.VK_F2;
			case UMoteRemoteAction.F3:
				return KeyEvent.VK_F3;
			case UMoteRemoteAction.F4:
				return KeyEvent.VK_F4;
			case UMoteRemoteAction.F5:
				return KeyEvent.VK_F5;
			case UMoteRemoteAction.F6:
				return KeyEvent.VK_F6;
			case UMoteRemoteAction.F7:
				return KeyEvent.VK_F7;
			case UMoteRemoteAction.F8:
				return KeyEvent.VK_F8;
			case UMoteRemoteAction.F9:
				return KeyEvent.VK_F9;
			case UMoteRemoteAction.F10:
				return KeyEvent.VK_F10;
			case UMoteRemoteAction.F11:
				return KeyEvent.VK_F11;
			case UMoteRemoteAction.F12:
				return KeyEvent.VK_F12;
			case UMoteRemoteAction.INSERT:
				return KeyEvent.VK_INSERT;
			case UMoteRemoteAction.DELETE:
				return KeyEvent.VK_DELETE;
			case UMoteRemoteAction.HOME:
				return KeyEvent.VK_HOME;
			case UMoteRemoteAction.PG_UP:
				return KeyEvent.VK_PAGE_UP;
			case UMoteRemoteAction.PG_DN:
				return KeyEvent.VK_PAGE_DOWN;
			case UMoteRemoteAction.BACKSPACE:
				return KeyEvent.VK_BACK_SPACE;
			case UMoteRemoteAction.ARROW_UP:
				return KeyEvent.VK_UP;
			case UMoteRemoteAction.ARROW_DOWN:
				return KeyEvent.VK_DOWN;
			case UMoteRemoteAction.ARROW_LEFT:
				return KeyEvent.VK_LEFT;
			case UMoteRemoteAction.ARROW_RIGHT:
				return KeyEvent.VK_RIGHT;
			case UMoteRemoteAction.WINDOWS:
				return KeyEvent.VK_WINDOWS;
			default:
				return NO_SWING_KEYCODE;
		}
	}
}
