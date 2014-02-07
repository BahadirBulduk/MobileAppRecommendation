package akilliyazilim.android.mobileapprecommendation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.services.RecomendationService;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapprecommendation.R;

public class RecomendationPage extends Activity {

	private Button button;
	private Spinner spinnerAnket1, spinnerAnket2, spinnerAnket3;
	String cevap1, cevap2, cevap3, appName, appInfo, appPopulerLinkList,
			appEditorLinkList;
	String androidId;
	TextView txtappName, txtAppInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recomendation);
		androidId = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		txtappName = (TextView) findViewById(R.id.textAppName);
		txtAppInfo = (TextView) findViewById(R.id.textAppInfo);
		Intent stopIntent = new Intent(RecomendationPage.this,
				RecomendationService.class);
		stopService(stopIntent);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			appName = extras.getString("appName");
			appInfo = extras.getString("appInfo");
			appPopulerLinkList = extras.getString("appPopulerLinkList");
			appEditorLinkList = extras.getString("appEditorLinkList");
			Toast.makeText(getApplicationContext(),
					appPopulerLinkList + "++++" + appEditorLinkList,
					Toast.LENGTH_SHORT).show();

			txtappName.setText(appName);
			txtAppInfo.setText(appInfo);

		}
		
		spinnerAnket1 = (Spinner) findViewById(R.id.spinner1);
		spinnerAnket2 = (Spinner) findViewById(R.id.spinner2);
		spinnerAnket3 = (Spinner) findViewById(R.id.spinner3);
		button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHelper database  = new DatabaseHelper(getApplicationContext(), androidId+".db");
				SQLiteDatabase db = database.getWritableDatabase();
				ContentValues values = new ContentValues();
				cevap1 = String.valueOf(spinnerAnket1.getSelectedItem());
				cevap2 = String.valueOf(spinnerAnket2.getSelectedItem());
				cevap3 = String.valueOf(spinnerAnket3.getSelectedItem());
				Log.i("cevap",cevap1);
				Log.i("cevap",cevap2);
				Log.i("cevap",cevap3);
				Log.i("cevap",appName);
				Log.i("cevap",androidId);

				
				/* Bu bilgiler burada db ye yazılmalı */
				values.put("TelId", androidId);
				values.put("recommendationAppName", appName);
				values.put("answer1", cevap1);
				values.put("answer2", cevap2);
				values.put("answer3", cevap3);
				values.putNull("playLink");
				db.insertOrThrow("Survey", null, values);
				db.close();
				Intent i = new Intent(RecomendationPage.this,
						DownloadPage.class);
				i.putExtra("appName", appName);
				i.putExtra("appPopulerLinkList", appPopulerLinkList);
				i.putExtra("appEditorLinkList", appEditorLinkList);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_left);
				finish();
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(),
				"Lutfen sorulari cevaplayınız.", Toast.LENGTH_SHORT).show();
	}
}
