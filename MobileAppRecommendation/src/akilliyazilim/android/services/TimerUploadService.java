package akilliyazilim.android.services;

import java.util.Calendar;

import akilliyazilim.android.receiver.UploadReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TimerUploadService extends Service {
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

		/**** Burayý kontrol etmek lazým zamanlamanýn dogru oldugundan ****/
		mCalendar.set(Calendar.HOUR_OF_DAY, 00);
		mCalendar.set(Calendar.MINUTE, 36);
		mCalendar.set(Calendar.SECOND, 00);
		mCalendar.set(Calendar.AM, Calendar.PM);
		/* Zaman gelince Upload Receiver devreye gierecek */
		Intent receiverIntent = new Intent(TimerUploadService.this,
				UploadReceiver.class);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				TimerUploadService.this, 0, receiverIntent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		/*
		 * Alarm Manager set et (tetiklenecek zaman ve intent parametre olarak
		 * ekleniyor)!
		 */
		alarmManager.set(AlarmManager.RTC, mCalendar.getTimeInMillis(),
				pendingIntent);

		return super.onStartCommand(intent, flags, startId);

	}
}
