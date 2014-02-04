package akilliyazilim.android.mobileapprecommendation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mobileapprecommendation.R;

public class RecomendationPage extends Activity {

	private Button button;
	private Spinner spinnerAnket1, spinnerAnket2, spinnerAnket3;
	String cevap1, cevap2, cevap3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recomendation);

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

			}
		});
	}
}
