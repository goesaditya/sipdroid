package org.sipdroid.sipua.ui;

import org.sipdroid.media.RtpStreamReceiver;
import org.sipdroid.sipua.R;
import org.sipdroid.sipua.UserAgent;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/*
 * Copyright (C) 2009 The Sipdroid Open Source Project
 * 
 * This file is part of Sipdroid (http://www.sipdroid.org)
 * 
 * Sipdroid is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

public class CallScreen extends Activity {
	public static final int FIRST_MENU_ID = Menu.FIRST;
	public static final int HANG_UP_MENU_ITEM = FIRST_MENU_ID + 1;
	public static final int HOLD_MENU_ITEM = FIRST_MENU_ID + 2;
	public static final int MUTE_MENU_ITEM = FIRST_MENU_ID + 3;
	public static final int DTMF_MENU_ITEM = FIRST_MENU_ID + 4;
	public static final int VIDEO_MENU_ITEM = FIRST_MENU_ID + 5;
	public static final int SPEAKER_MENU_ITEM = FIRST_MENU_ID + 6;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);

		MenuItem m = menu.add(0, HOLD_MENU_ITEM, 0, R.string.menu_hold);
		m.setIcon(R.drawable.sym_call_hold_on);
		m = menu.add(0, HANG_UP_MENU_ITEM, 0, R.string.menu_endCall);
		m.setIcon(R.drawable.sym_call_end);
		m = menu.add(0, MUTE_MENU_ITEM, 0, R.string.menu_mute);
		m.setIcon(R.drawable.mute);
		m = menu.add(0, SPEAKER_MENU_ITEM, 0, R.string.menu_speaker);
		m.setIcon(R.drawable.sym_call_speakerphone_on);
		m = menu.add(0, DTMF_MENU_ITEM, 0, R.string.menu_dtmf);
		m.setIcon(R.drawable.sym_incoming_call_answer_options);
		m = menu.add(0, VIDEO_MENU_ITEM, 0, R.string.menu_video);
		m.setIcon(R.drawable.incall_photo_border);
				
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = super.onOptionsItemSelected(item);
		Intent intent = null;

		switch (item.getItemId()) {
		case HANG_UP_MENU_ITEM:
			Receiver.engine(this).rejectcall();
			break;
			
		case HOLD_MENU_ITEM:
			Receiver.engine(this).togglehold();
			break;
			
		case MUTE_MENU_ITEM:
			Receiver.engine(this).togglemute();
			break;
					
		case SPEAKER_MENU_ITEM:
			Receiver.engine(this).speaker(RtpStreamReceiver.TOGGLE);
			break;
					
		case DTMF_MENU_ITEM:
			if (Receiver.call_state == UserAgent.UA_STATE_HOLD) Receiver.engine(this).togglehold();
			try {
				intent = new Intent(this, org.sipdroid.sipua.ui.DTMF.class);
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
			}
			break;

		case VIDEO_MENU_ITEM:
			if (Receiver.call_state == UserAgent.UA_STATE_HOLD) Receiver.engine(this).togglehold();
			try {
				intent = new Intent(this, org.sipdroid.sipua.ui.VideoCamera.class);
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
			}
			break;
		}

		return result;
	}
	
}
