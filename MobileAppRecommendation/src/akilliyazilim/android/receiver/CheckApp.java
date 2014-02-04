package akilliyazilim.android.receiver;

import java.util.Date;
import java.util.List;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.mobileapprecommendation.MainActivity;
import akilliyazilim.android.services.AppTrackingService;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

public class CheckApp extends BroadcastReceiver {

	DatabaseHelper database;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		/* packagename */
		Log.i("LOG", intent.getData().getEncodedSchemeSpecificPart());
		/* yap�lan i�lem */
		Log.i("LOG", intent.getAction().toString());

		
		database = MainActivity.database;
		SQLiteDatabase db = database.getWritableDatabase();
		Date dt = new Date();
		CharSequence s  = DateFormat.format("dd-mm-yyyy ", dt.getTime());
		
		/**
		 * !!!Kad�n�n uygulamalar y�klenirken com.example.appname �eklinde db ye kaydedilecek!!!
		 */
		
		// Silinen veya y�klenen uygulama �nerilen uygulama ise Recommendation tablosu update edilir.
		for(int i =0;i<8;i++){
			if(intent.getData().getEncodedSchemeSpecificPart().contains("kad�n�n verdi�i uygulama isimleri kontrol edilecek")){
				if(intent.getAction().toString().equals("android.intent.action.PACKAGE_REMOVED")){
					ContentValues values = new ContentValues();
					values.put("DeletedDate",s+"" );
					db.update("Recommendation", values, "AppName = ?", new String [] {intent.getData().getEncodedSchemeSpecificPart()});
					break;
				}else if (intent.getAction().toString().equals("android.intent.action.PACKAGE_INSTALL")){
					ContentValues values = new ContentValues();
					values.put("TelId", MainActivity.androidId);
					values.put("AppName", intent.getData().getEncodedSchemeSpecificPart());
					values.put("InstallDate", s+"");
					values.putNull("DeletedDate");
					db.insert("Recommendation", null, values);
					break;
				}
			}
		}
		//Herhangi bir uygulama silindiginde veya yuklendiginde AppList guncellenir.
			if(intent.getAction().toString().equals("android.intent.action.PACKAGE_REMOVED")){
				ContentValues values = new ContentValues();
				values.put("DeletedDate",s+"" );
				db.update("AppList", values, "AppName = ?", new String [] {intent.getData().getEncodedSchemeSpecificPart()});
			}else if (intent.getAction().toString().equals("android.intent.action.PACKAGE_INSTALL")){
				ContentValues values = new ContentValues();
				values.put("TelId", MainActivity.androidId);
				values.put("AppName", intent.getData().getEncodedSchemeSpecificPart());
				values.put("InstallDate", s+"");
				values.putNull("DeletedDate");
				db.insert("AppList", null, values);
			}
	}

}
