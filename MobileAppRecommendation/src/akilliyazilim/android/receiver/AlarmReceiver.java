package akilliyazilim.android.receiver;

import akilliyazilim.android.services.RecomendationService;
import akilliyazilim.android.services.TimerService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		/* service tanýmla */
		Intent service = new Intent(context, TimerService.class);
		context.stopService(service);
		Intent service1 = new Intent(context, RecomendationService.class);
		context.startService(service1);
	}
}
