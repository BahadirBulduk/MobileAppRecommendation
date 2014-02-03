package akilliyazilim.android.services;

import java.util.Calendar;
import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AppTrackingService extends Service {
	/* counter sureyi sayan sayac */
	int counter = 0;
	/* en son uygulaman�n package ad�n� tutan deg�sken */
	String lastPackageName;
	/* Cal�san uygulamalar�n listesinin tutuldugu List */
	List<ActivityManager.RunningTaskInfo> taskInfo;
	ActivityManager activityManager;
	ComponentName componentInfo;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		activityManager = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		taskInfo = activityManager.getRunningTasks(1);
		/* Activity stackte en tepedeki activity al�n�r */
		componentInfo = taskInfo.get(0).topActivity;
		try {
			/* Ayn� Uygulama �al��maya devam ederken */
			if (componentInfo.getPackageName().equals(lastPackageName)) {

				counter++;
				Log.d("Running task", componentInfo.getPackageName() + "  "
						+ componentInfo.getClassName() + " " + counter + " "
						+ Calendar.getInstance().get(

						Calendar.HOUR_OF_DAY));
			}
			/* Yen� bir uygulama a��ld�g�nda counter s�f�rlan�r */
			else {
				if (lastPackageName != null) {
					/* Counter bu nokta da db ye kay�t ed�lmeli */

					Log.i("Running task", "Last:" + lastPackageName);

				}

				counter = 0;
				Log.d("Running task",
						componentInfo.getPackageName()
								+ "  "
								+ counter
								+ " "
								+ Calendar.getInstance().get(
										Calendar.HOUR_OF_DAY));
			}
			/* Bir �nceki uygulaman�n package name elde et! */
			lastPackageName = taskInfo.get(0).topActivity.getPackageName();

		} catch (Exception e) {

		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
