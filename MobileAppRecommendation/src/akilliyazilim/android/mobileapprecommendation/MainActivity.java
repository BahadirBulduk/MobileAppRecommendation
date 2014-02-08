package akilliyazilim.android.mobileapprecommendation;

import java.io.File;

import AppList.AppList;
import akilliyazilim.android.Database.DatabaseHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;

public class MainActivity extends Activity {

	public static DatabaseHelper database;
	public static String androidId;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		androidId = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		File f = new File(getDatabasePath(androidId + ".db").toString());
		if (!f.isFile()) {
			database  = new DatabaseHelper(getApplicationContext(), androidId+".db");
			SQLiteDatabase db = database.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("next", 0);
			db.insertOrThrow("NotifId", null, values);
			database.close();
			AppList a = new AppList(this, androidId);
			initialize();
			startServiceRecom();
			startServiceUpload();
		}
		
	}

	private void initialize() {
		/* Manual Kontrol */
		getBaseContext().getApplicationContext().sendBroadcast(
				new Intent("Manual_Start"));
	}

	public void startServiceRecom() {

		/* Manual Kontrol */
		getBaseContext().getApplicationContext().sendBroadcast(
				new Intent("Manual_Start_Recom"));

	}

	public void startServiceUpload() {

		/* Manual Kontrol */
		getBaseContext().getApplicationContext().sendBroadcast(
				new Intent("Manual_Start_Upload"));

	}

}
