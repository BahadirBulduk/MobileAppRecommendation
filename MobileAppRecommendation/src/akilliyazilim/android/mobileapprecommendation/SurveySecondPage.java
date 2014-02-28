package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.adapters.KisilikTestiAdapter;
import akilliyazilim.android.constants.Constants;
import akilliyazilim.android.services.RecomendationService;
import akilliyazilim.android.services.UploadService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
//	private EditText oneri; 
	private TextView text;
	DatabaseHelper database;
	Activity a;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survey_page);
		context = this;
		a=this;
		listView = (ListView) findViewById(R.id.listSorular);
		button = (Button) findViewById(R.id.btnNext);
		button.setText("Bitir");
		text = (TextView) findViewById(R.id.textbaslik);
		text.setText("Kiþilik Testi");
//		oneri = (EditText)findViewById(R.id.oneriText);
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

				UploadService.setActivity(a);
				
				Intent service1 = new Intent(getApplicationContext(), UploadService.class);
				startService(service1);
//					new AsynUpload().execute();
					
				// upload service çaðýrýlacak.. recom service durdurulacak.alarm durdurulacak.
				
				
			}
		});

	}
	
//	public class AsynUpload extends AsyncTask<Void, Void, Void>{
//		private ProgressDialog dialog;
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			dialog = new ProgressDialog(context);
//			dialog.setMessage("Cevaplarýnýz kaydediliyor lütfen internetinizin açýk olduðuna emin olunuz.");
//		    dialog.show();
//			
//		}
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			Intent service1 = new Intent(getApplicationContext(), UploadService.class);
//			startService(service1);
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_SHORT).show();
//			}
//			return null;
//		}
//		@Override
//		protected void onPostExecute(Void result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			Intent service1 = new Intent(getApplicationContext(), RecomendationService.class);
//			stopService(service1);
//			this.dialog.dismiss();
//		}
//		
//	}
}
