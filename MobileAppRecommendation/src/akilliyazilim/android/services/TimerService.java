package akilliyazilim.android.services;

import java.util.Calendar;
import java.util.Random;

import akilliyazilim.android.constants.Constants;
import akilliyazilim.android.receiver.AlarmReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

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
		Random ran = new Random();

		mCalendar.set(
				Calendar.HOUR_OF_DAY,
				Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						+ (1 + ran.nextInt(5)));
		Log.i("LOG", Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + "");
		mCalendar.set(Calendar.MINUTE,
				Calendar.getInstance().get(Calendar.MINUTE));
		Log.i("LOG", Calendar.getInstance().get(Calendar.MINUTE) + "");

		mCalendar.set(Calendar.SECOND,
				Calendar.getInstance().get(Calendar.SECOND));
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
		alarmManager.setRepeating(AlarmManager.RTC,
				mCalendar.getTimeInMillis(),
				Constants.schedule[ran.nextInt(10)], pendingIntent);

		// alarmManager.setInexactRepeating(AlarmManager.RTC,
		// mCalendar.getTimeInMillis(), 180000, pendingIntent);

		return super.onStartCommand(intent, flags, startId);

	}
}
