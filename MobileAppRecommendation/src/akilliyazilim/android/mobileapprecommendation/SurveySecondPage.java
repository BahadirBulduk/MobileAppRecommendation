package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.adapters.KisilikTestiAdapter;
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
	String[] kisilikTesiSorular = { "Konu�kan",
			"Ba�kalar�n�n kusurlar�n� bulmaya e�ilimli", "M�kemmel i� ��karan",
			"Depresif, h�z�nl�", "Orjinal, yeni fikirler �reten", "�ekingen",
			"Yard�msever, bencil olmayan", "Biraz dikkatsiz",
			"Rahat, stresle ba�a ��kabilen" };
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
		text.setText("Ki�ilik Testi");

		Toast.makeText(
				getApplicationContext(),
				"�fadelerin sizi tan�mlama d�zey�ni dikkate alarak cevaplay�n�z",
				Toast.LENGTH_SHORT).show();

		KisilikTestiAdapter adapter = new KisilikTestiAdapter(this,
				kisilikTesiSorular);

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
