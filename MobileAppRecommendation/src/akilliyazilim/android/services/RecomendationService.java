package akilliyazilim.android.services;

import akilliyazilim.android.constants.Constants;
import akilliyazilim.android.mobileapprecommendation.RecomendationPage;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mobileapprecommendation.R;

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
		Intent intentNotif = new Intent(getBaseContext(),
				RecomendationPage.class);
		/*** index düzenlemesi yapýlacak ***/
		intentNotif.putExtra("appName", Constants.appNameList[0]);
		intentNotif.putExtra("appInfo", Constants.appInfoList[0]);
		intentNotif.putExtra("appPopulerLinkList",
				Constants.appPopulerLinkList[0]);
		intentNotif.putExtra("appEditorLinkList",
				Constants.appEditorLinkList[0]);
		intentNotif.putExtra("appPopulerPackageList",
				Constants.appPopulerPackageList[0]);
		intentNotif.putExtra("appEditorPackageList",
				Constants.appEditorPackageList[0]);
		Log.i("LOG", "RecomendationService");

		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentNotif,
				0);

		Notification n = new NotificationCompat.Builder(this)
				.setContentTitle("Uygulama Baþlýðý")
				.setContentText("Ufak Uygulama Yazýsý")
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