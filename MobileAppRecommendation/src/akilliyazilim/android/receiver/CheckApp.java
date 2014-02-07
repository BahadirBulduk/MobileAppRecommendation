package akilliyazilim.android.receiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.constants.Constants;
import akilliyazilim.android.mobileapprecommendation.MainActivity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;

public class CheckApp extends BroadcastReceiver {

	DatabaseHelper database;
	String androidId;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		/* packagename */
		Log.i("LOG", intent.getData().getEncodedSchemeSpecificPart());
		/* yap�lan i�lem */
		Log.i("LOG", intent.getAction().toString());

		androidId = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
		Calendar c2 = Calendar.getInstance();
		SimpleDateFormat sdf2 = new SimpleDateFormat("H:m:s");
		String strdate2 = sdf2.format(c2.getTime());
		database  = new DatabaseHelper(context, androidId+"-"+strdate2+".db");
		
		SQLiteDatabase db = database.getWritableDatabase();
		Date dt = new Date();
		CharSequence s  = DateFormat.format("dd-mm-yyyy ", dt.getTime());
		
		/**
		 * !!!Kad�n�n uygulamalar y�klenirken com.example.appname �eklinde db ye kaydedilecek!!!
		 */
		
		// Silinen veya y�klenen uygulama �nerilen uygulama ise Recommendation tablosu update edilir.
		for(int i =0;i<3;i++){
			if(intent.getData().getEncodedSchemeSpecificPart().equals(Constants.appEditorPackageList[i])||intent.getData().getEncodedSchemeSpecificPart().equals(Constants.appPopulerPackageList[i]) ){
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
			
			db.close();
	}
	
}
