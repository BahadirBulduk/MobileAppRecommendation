package akilliyazilim.android.receiver;

import akilliyazilim.android.services.UploadService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UploadReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		/* UploadService cal�smaya baslar */

		/* Burada callLog db ye kayded�lebilir */
		Intent i = new Intent(context, UploadService.class);
		context.startService(i);
	}

}
