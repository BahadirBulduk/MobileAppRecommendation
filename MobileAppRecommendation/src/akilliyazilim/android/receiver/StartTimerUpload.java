package akilliyazilim.android.receiver;

import akilliyazilim.android.services.TimerUploadService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartTimerUpload extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, TimerUploadService.class);
		context.startService(i);
	}

}
