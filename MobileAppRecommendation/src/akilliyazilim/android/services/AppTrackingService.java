package akilliyazilim.android.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import android.util.Log;

public class AppTrackingService extends Service {
	/* counter sureyi sayan sayac */
	int counter = 0;
	/* en son uygulaman�n package ad�n� tutan deg�sken */
	String lastPackageName;
	/* Cal�san uygulamalar�n listesinin tutuldugu List */
	List<ActivityManager.RunningTaskInfo> taskInfo;
	public static ActivityManager activityManager;
	ComponentName componentInfo;
	DatabaseHelper database;
	String androidId;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
		database  = new DatabaseHelper(getApplicationContext(), androidId+".db");
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
				Log.i("counter",counter+"-");
			}
			/* Yen� bir uygulama a��ld�g�nda counter s�f�rlan�r */
			else {
				if (lastPackageName != null) {
					/* Database'e yaz�lacak androidId ve tarih al�nd� */
					String androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
//					Date dt = new Date();
//					CharSequence s  = DateFormat.format("dd-mm-yyyy ", dt.getTime());
					String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
					/*Database'e yazma islemi gerceklestirildi*/
					SQLiteDatabase db = database.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put("TelId", androidId);//string
					values.put("AppName", lastPackageName);//string
					values.put("DurationOfUse", counter);//int
					values.put("Date",currentDateTimeString);//string
					db.insertOrThrow("AppTracking", null, values);
					db.close();	
				}
				counter = 0;
			}
			/* Bir �nceki uygulaman�n package name elde et! */
			lastPackageName = taskInfo.get(0).topActivity.getPackageName();
		} catch (Exception e) {
			Log.w("hata",e.getLocalizedMessage() +"--" + e.getMessage());
		}
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
