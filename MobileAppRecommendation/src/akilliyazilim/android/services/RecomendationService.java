package akilliyazilim.android.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.constants.Constants;
import akilliyazilim.android.mobileapprecommendation.RecomendationPage;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mobileapprecommendation.R;

public class RecomendationService extends Service {

	private NotificationManager mManager;
	private int index;
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
		Intent intentNotif = new Intent(getBaseContext(),
				RecomendationPage.class);
		String androidId = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		/*** index düzenlemesi yapýlacak ***/
		Calendar c2 = Calendar.getInstance();
		SimpleDateFormat sdf2 = new SimpleDateFormat("H:m:s");
		String strdate2 = sdf2.format(c2.getTime());
		DatabaseHelper db_helper = new DatabaseHelper(getApplicationContext(), androidId+"-"+strdate2+".db");
		SQLiteDatabase db = db_helper.getReadableDatabase();
		String query = "SELECT next FROM NotifId";
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		index =Integer.parseInt(c.getString(0));
		db.close();
		Log.i("index",index +"--");
		intentNotif.putExtra("appName", Constants.appNameList[index]);
		intentNotif.putExtra("appInfo", Constants.appInfoList[index]);
		intentNotif.putExtra("appPopulerLinkList",
				Constants.appPopulerLinkList[index]);
		intentNotif.putExtra("appEditorLinkList",
				Constants.appEditorLinkList[index]);
		intentNotif.putExtra("appPopulerPackageList",
				Constants.appPopulerPackageList[index]);
		intentNotif.putExtra("appEditorPackageList",
				Constants.appEditorPackageList[index]);
		Log.i("LOG", "RecomendationService");

		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentNotif,
				0);

		Notification n = new NotificationCompat.Builder(this)
				.setContentTitle("Uygulama Önerisi" +(index+ 1))
				// i+1 ile yer deðiþtirecek
				.setContentText(Constants.appNameList[index])
				.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
				.build();
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		n.defaults |= Notification.DEFAULT_ALL;
		n.flags |= Notification.FLAG_ONGOING_EVENT;
		n.flags |= Notification.FLAG_AUTO_CANCEL;
		Log.i("LOG", "RecomendationService1");

		notificationManager.notify(0, n);
		Log.i("LOG", "RecomendationService2");
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}