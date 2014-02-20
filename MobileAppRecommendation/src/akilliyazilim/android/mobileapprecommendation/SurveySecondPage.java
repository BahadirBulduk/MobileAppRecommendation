package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.adapters.KisilikTestiAdapter;
import akilliyazilim.android.constants.Constants;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listSorular);
		radioGroup = (RadioGroup) listView.findViewById(R.id.cevaplar);
		button = (Button) findViewById(R.id.btnNext);
		button.setText("Bitir");
		text = (TextView) findViewById(R.id.textbaslik);
		text.setText("Kiþilik Testi");

		Toast.makeText(
				getApplicationContext(),
				"Ýfadelerin sizi tanýmlama düzeyýni dikkate alarak cevaplayýnýz",
				Toast.LENGTH_SHORT).show();

		KisilikTestiAdapter adapter = new KisilikTestiAdapter(this,
				Constants.kisilikTesiSorular);

		listView.setAdapter(adapter);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "deney burada biter!",
						Toast.LENGTH_LONG).show();
			}
		});
	}
}
