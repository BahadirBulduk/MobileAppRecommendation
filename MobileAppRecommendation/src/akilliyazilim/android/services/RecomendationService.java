package akilliyazilim.android.services;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.constants.Constants;
import akilliyazilim.android.mobileapprecommendation.R;
import akilliyazilim.android.mobileapprecommendation.RecomendationPage;
import akilliyazilim.android.mobileapprecommendation.SurveyFirstPage;
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
		
		String androidId = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		DatabaseHelper db_helper = new DatabaseHelper(getApplicationContext(), androidId+".db");
		SQLiteDatabase db = db_helper.getReadableDatabase();
		String query = "SELECT next FROM NotifId";
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		Log.i("sadasdadasd", c.getString(0));
		index =Integer.parseInt(c.getString(0));
		Intent intentNotif;
		db.close();
		Notification n;
		if(index==8){
			intentNotif = new Intent(getBaseContext(),
					SurveyFirstPage.class);
			PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentNotif,
					0);

			 n = new NotificationCompat.Builder(this)
					.setContentTitle("Kiþilik ve Ýkna Testi")
					.setContentText("Testi cözmek icin týklayýnýz")
					.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
					.build();
		}else{
			intentNotif = new Intent(getBaseContext(),
				RecomendationPage.class);
			PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentNotif,
					0);

			 n = new NotificationCompat.Builder(this)
					.setContentTitle("Uygulama Önerisi" +(index+ 1))
					.setContentText(Constants.appNameList[index])
					.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
					.build();
		}
	
		
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