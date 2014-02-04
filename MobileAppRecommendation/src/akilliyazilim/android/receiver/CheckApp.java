package akilliyazilim.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CheckApp extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		/* packagename */
		Log.i("LOG", intent.getData().getEncodedSchemeSpecificPart());
		/* yapýlan iþlem */
		Log.i("LOG", intent.getAction().toString());
	}

}
