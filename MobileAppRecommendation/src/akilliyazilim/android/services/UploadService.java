package akilliyazilim.android.services;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

public class UploadService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		if (intent.getExtras() != null) {
			NetworkInfo ni = (NetworkInfo) intent.getExtras().get(
					ConnectivityManager.EXTRA_NETWORK_INFO);
			if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
				// internet baglantýsý mevcut

			} else if (intent.getBooleanExtra(
					ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
				// ýnternet baglantýsý yok
			}
		}
		return super.onStartCommand(intent, flags, startId);

	}

}
