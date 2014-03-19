package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.Database.DatabaseHelper;
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

public class LastSurveyInfoPage extends Activity {

	DatabaseHelper databaseHelper;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lastsurvey_infopage);

		final Spinner spinner = (Spinner) findViewById(R.id.spinnerlast1);
		final Spinner spinner2 = (Spinner) findViewById(R.id.spinnerlast2);
		Button buttonBasla = (Button) findViewById(R.id.buttonBasla);

		buttonBasla.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("LOG", spinner.getSelectedItem().toString());
				Log.i("LOG", spinner2.getSelectedItem().toString());
				// db kayýt ýslemlerý burada yapýlacak
				String androidId = Settings.Secure.getString(getContentResolver(),
						Settings.Secure.ANDROID_ID);
				databaseHelper = new DatabaseHelper(getApplicationContext(), androidId+".db");
				db = databaseHelper.getWritableDatabase();
				
				ContentValues values = new ContentValues();
				values.put("cinsiyet", spinner.getSelectedItem().toString());
				values.put("bolum", spinner2.getSelectedItem().toString());
				
				db.insert("cinsiyetbolum", null, values);
				databaseHelper.close();
				startActivity(new Intent(LastSurveyInfoPage.this,
						SurveyFirstPage.class));
				finish();
			}
		});

	}
}
