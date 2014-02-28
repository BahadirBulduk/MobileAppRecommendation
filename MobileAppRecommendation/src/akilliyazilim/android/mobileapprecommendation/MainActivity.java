package akilliyazilim.android.mobileapprecommendation;

import java.io.File;

import AppList.AppList;
import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.services.RecomendationService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static DatabaseHelper database;
	public static String androidId;
	Button oneriButton;
	EditText oneriText;

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
		oneriButton = (Button)findViewById(R.id.mainoneriButton);
		oneriText = (EditText)findViewById(R.id.MainoneriText);
		if (!f.isFile()) {
			database  = new DatabaseHelper(getApplicationContext(), androidId+".db");
			SQLiteDatabase db = database.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("next", 0);
			db.insertOrThrow("NotifId", null, values);
			database.close();
			AppList a = new AppList(this, androidId);
			Intent service1 = new Intent(getApplicationContext(), RecomendationService.class);
			startService(service1);
//			initialize();
			startServiceRecom();
//			startServiceUpload();
		}
		Intent service1 = new Intent(getApplicationContext(), RecomendationService.class);
		startService(service1);
		oneriButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!oneriText.getText().toString().isEmpty()){
					SQLiteDatabase db = database.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put("gorus", oneriText.getText().toString());
					db.insertOrThrow("oneri", null, values);
					database.close();
					oneriText.setText("");
					Toast.makeText(getApplicationContext(), "Öneriniz yollandý. Teþekkür ederiz", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "Önce önerinizi yazýnýz.", Toast.LENGTH_SHORT).show();
				}
			}
		});
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
