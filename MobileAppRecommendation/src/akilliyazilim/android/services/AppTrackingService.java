	package akilliyazilim.android.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.mobileapprecommendation.MainActivity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;

public class AppTrackingService extends Service {
	/* counter sureyi sayan sayac */
	int counter = 0;
	/* en son uygulamanýn package adýný tutan degýsken */
	String lastPackageName;
	/* Calýsan uygulamalarýn listesinin tutuldugu List */
	List<ActivityManager.RunningTaskInfo> taskInfo;
	ActivityManager activityManager;
	ComponentName componentInfo;
	DatabaseHelper database;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		database = MainActivity.database;
		activityManager = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		taskInfo = activityManager.getRunningTasks(1);
		/* Activity stackte en tepedeki activity alýnýr */
		componentInfo = taskInfo.get(0).topActivity;
		try {
			/* Ayný Uygulama çalýþmaya devam ederken */
			if (componentInfo.getPackageName().equals(lastPackageName)) {
				counter++;
			}
			/* Yený bir uygulama açýldýgýnda counter sýfýrlanýr */
			else {
				if (lastPackageName != null) {
//deneme 
					/* Database'e yazýlacak androidId ve tarih alýndý */
					String androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
					Date dt = new Date();
					CharSequence s  = DateFormat.format("dd-mm-yyyy ", dt.getTime());
					
					/*Database'e yazma islemi gerceklestirildi*/
					SQLiteDatabase db = database.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put("TelId", androidId);//string
					values.put("AppName", lastPackageName);//string
					values.put("DurationOfUse", counter);//int
					values.put("Date",s+"");//string
					db.insertOrThrow("AppTracking", null, values);
					db.close();				
				}
				counter = 0;
			}
			/* Bir önceki uygulamanýn package name elde et! */
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
