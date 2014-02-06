package akilliyazilim.android.mobileapprecommendation;

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
import android.widget.Toast;

import com.example.mobileapprecommendation.R;

public class RecomendationPage extends Activity {

	private Button button;
	private Spinner spinnerAnket1, spinnerAnket2, spinnerAnket3;
	String cevap1, cevap2, cevap3, appName, appPopulerLinkList,
			appEditorLinkList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recomendation);
		Log.i("LOG", "RecomendationPage");
		Intent stopIntent = new Intent(RecomendationPage.this,
				RecomendationService.class);
		stopService(stopIntent);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			appName = extras.getString("appName");
			appPopulerLinkList = extras.getString("appPopulerLinkList");
			appEditorLinkList = extras.getString("appEditorLinkList");
			Toast.makeText(getApplicationContext(),
					appPopulerLinkList + "++++" + appEditorLinkList,
					Toast.LENGTH_SHORT).show();

		}

		spinnerAnket1 = (Spinner) findViewById(R.id.spinner1);
		spinnerAnket2 = (Spinner) findViewById(R.id.spinner2);
		spinnerAnket3 = (Spinner) findViewById(R.id.spinner3);
		button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String AndroidId = Settings.Secure.getString(
						getContentResolver(), Settings.Secure.ANDROID_ID);
				DatabaseHelper database = new DatabaseHelper(
						getApplicationContext(), AndroidId + ".db");
				SQLiteDatabase db = database.getWritableDatabase();
				ContentValues values = new ContentValues();
				cevap1 = String.valueOf(spinnerAnket1.getSelectedItem());
				cevap2 = String.valueOf(spinnerAnket2.getSelectedItem());
				cevap3 = String.valueOf(spinnerAnket3.getSelectedItem());
				/* Bu bilgiler burada db ye yazýlmalý */
				values.put("TelId", cevap1);
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
				"Lutfen sorulari cevaplayýnýz.", Toast.LENGTH_SHORT).show();
	}
}
