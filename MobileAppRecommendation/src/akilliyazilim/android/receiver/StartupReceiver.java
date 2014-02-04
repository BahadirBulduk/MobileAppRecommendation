package akilliyazilim.android.receiver;

import akilliyazilim.android.services.AppTrackingService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class StartupReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		try {
			Intent serviceIntent = new Intent(context, AppTrackingService.class);
			PendingIntent ServiceManagementIntent = PendingIntent.getService(
					context, 111111, serviceIntent, 0);
			/* Alarm Manager her bir saniye de çalýþacak */
			alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
					SystemClock.elapsedRealtime(), 1000,
					ServiceManagementIntent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
