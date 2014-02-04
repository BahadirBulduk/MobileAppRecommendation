package akilliyazilim.android.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RecomendationService extends Service {
	private NotificationManager mManager;

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
		// Intent intentNotif = new Intent(getBaseContext(),
		// RecomendationPage.class);
		// PendingIntent pIntent = PendingIntent.getActivity(this, 0,
		// intentNotif,
		// 0);
		//
		// Notification n = new NotificationCompat.Builder(this)
		// .setContentTitle("Uygulama Baþlýðý ")
		// .setContentText("Ufak Uygulama Yazýsý")
		// .setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
		// .addAction(R.drawable.ic_launcher, "Call", pIntent).build();
		// NotificationManager notificationManager = (NotificationManager)
		// getSystemService(NOTIFICATION_SERVICE);
		// // noti.flags |= Notification.FLAG_AUTO_CANCEL;
		//
		// notificationManager.notify(0, n);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}