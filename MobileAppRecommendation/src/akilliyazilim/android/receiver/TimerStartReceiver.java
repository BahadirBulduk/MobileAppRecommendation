package akilliyazilim.android.receiver;

import akilliyazilim.android.services.TimerService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimerStartReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, TimerService.class);
		context.startService(i);
	}

}