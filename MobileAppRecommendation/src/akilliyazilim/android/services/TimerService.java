package akilliyazilim.android.services;

import java.util.Calendar;

import akilliyazilim.android.receiver.AlarmReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimerService extends Service {
	private Calendar mCalendar;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		mCalendar = Calendar.getInstance();
		/* zaman ayarlamalarýný yap */
		// mCalendar.set(mCalendar.get(Calendar.YEAR),
		// mCalendar.get(Calendar.MONTH),
		// mCalendar.get(Calendar.DAY_OF_MONTH));

		mCalendar.set(Calendar.HOUR_OF_DAY, 12);
		mCalendar.set(Calendar.MINUTE, 36);
		mCalendar.set(Calendar.SECOND, 00);
		mCalendar.set(Calendar.AM, Calendar.PM);

		Intent receiverIntent = new Intent(TimerService.this,
				AlarmReceiver.class);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				TimerService.this, 0, receiverIntent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		/*
		 * Alarm Manager set et (tetiklenecek zaman ve intent parametre olarak
		 * ekleniyor)!
		 */
		// alarmManager.set(AlarmManager.RTC, mCalendar.getTimeInMillis(),
		// pendingIntent);
		// Inexact
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				mCalendar.getTimeInMillis(), 180000, pendingIntent);

		// alarmManager.setInexactRepeating(AlarmManager.RTC,
		// mCalendar.getTimeInMillis(), 180000, pendingIntent);

		return super.onStartCommand(intent, flags, startId);

	}
}
