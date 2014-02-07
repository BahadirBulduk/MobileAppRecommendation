package akilliyazilim.android.receiver;

import akilliyazilim.android.constants.Constants;
import akilliyazilim.android.services.UploadService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getExtras() != null) {
			NetworkInfo ni = (NetworkInfo) intent.getExtras().get(
					ConnectivityManager.EXTRA_NETWORK_INFO);
			if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
				// internet baglantýsý mevcut
				if (Constants.code == 1111) {
					Intent service = new Intent(context, UploadService.class);
					context.stopService(service);
				}

			} else if (intent.getBooleanExtra(
					ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
				// ýnternet baglantýsý yok
			}
		}
	}
}
