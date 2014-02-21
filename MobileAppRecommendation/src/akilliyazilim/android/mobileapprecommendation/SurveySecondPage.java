package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.adapters.KisilikTestiAdapter;
import akilliyazilim.android.constants.Constants;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SurveySecondPage extends Activity {
	private ListView listView;
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private Button button;
	private EditText oneri; 
	private TextView text;
	DatabaseHelper database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survey_page);

		listView = (ListView) findViewById(R.id.listSorular);
		button = (Button) findViewById(R.id.btnNext);
		button.setText("Bitir");
		text = (TextView) findViewById(R.id.textbaslik);
		text.setText("Kiþilik Testi");
		oneri = (EditText)findViewById(R.id.oneriText);
		oneri.setVisibility(0);
		String androidId = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		database = new DatabaseHelper(getApplicationContext(), androidId+".db");
		Toast.makeText(
				getApplicationContext(),
				"Ýfadelerin sizi tanýmlama düzeyýni dikkate alarak cevaplayýnýz",
				Toast.LENGTH_LONG).show();

		KisilikTestiAdapter adapter = new KisilikTestiAdapter(this,
				Constants.kisilikTesiSorular);

		listView.setAdapter(adapter);

		button.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi") @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!oneri.getText().toString().isEmpty()){
					SQLiteDatabase db = database.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put("gorus", oneri.getText().toString());
					db.insertOrThrow("oneri", null, values);
					database.close();
				}
				
				Toast.makeText(getApplicationContext(), "Deneyimiz burada bitmiþtir. Yardýmlarýnýz için teþekkür ederiz.",
						Toast.LENGTH_LONG).show();
			}
		});

	}
}
