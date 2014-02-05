package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.services.RecomendationService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileapprecommendation.R;

public class RecomendationPage extends Activity {

	private Button button;
	private Spinner spinnerAnket1, spinnerAnket2, spinnerAnket3;
	String cevap1, cevap2, cevap3, value;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recomendation);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			value = extras.getString("message");
		}

		Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT)
				.show();

		spinnerAnket1 = (Spinner) findViewById(R.id.spinner1);
		spinnerAnket2 = (Spinner) findViewById(R.id.spinner2);
		spinnerAnket3 = (Spinner) findViewById(R.id.spinner3);
		button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cevap1 = String.valueOf(spinnerAnket1.getSelectedItem());
				cevap2 = String.valueOf(spinnerAnket2.getSelectedItem());
				cevap3 = String.valueOf(spinnerAnket3.getSelectedItem());
				/* Bu bilgiler burada db ye yazýlmalý */

				startActivity(new Intent(RecomendationPage.this,
						DownloadPage.class));
				finish();

			}
		});

		Intent stopIntent = new Intent(RecomendationPage.this,
				RecomendationService.class);
		stopService(stopIntent);
	}
}
