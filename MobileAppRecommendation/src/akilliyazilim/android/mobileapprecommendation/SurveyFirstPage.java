package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.adapters.IknaTestiAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SurveyFirstPage extends Activity {
	private ListView listView;
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private Button button;
	TextView text;
	String[] iknaTestiSorular = {
			"Doktorumun tavsiyelerine kesinlikle uyar�m.",
			"Otoritelerin ve konunun uzmanlar�n�n g�r��lerine �ok de�er veririm.",
			"�stlerimin verdi�i talimatlara her zaman uyar�m.",
			"Otoritelerin ve konunun uzmanlar�n�n g�r��lerini arkada�lar�m�n g�r��lerinden daha �ok dikkate al�r�m",
			"Ba�kalar�n�n da ayn� �eyi yapt���n� biliyorsam, ayn� �eyi yapmaya devam ederim.",
			"Sosyal �evremden birisi bir kitab�n g�zel oldu�unu s�ylerse, o kitab� okumaya e�ilimli olurum.",
			"Yeni bir durumla kar��la�t���mda karar verebilmek i�in di�erlerinin ne yapt���na bakar�m.",
			"Ayk�r� d��memek benim i�in �nemlidir.",
			"Ne yapaca��m� belirlemek i�in �o�u kez di�erlerinin ne yapt���n� dikkate al�r�m." };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listSorular);
		radioGroup = (RadioGroup) listView.findViewById(R.id.cevaplar);
		button = (Button) findViewById(R.id.btnNext);
		text = (TextView) findViewById(R.id.textbaslik);
		text.setText("�kna Testi");

		IknaTestiAdapter adapter = new IknaTestiAdapter(this, iknaTestiSorular);

		listView.setAdapter(adapter);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				startActivity(new Intent(SurveyFirstPage.this,
						SurveySecondPage.class));
				finish();
			}
		});

	}
}
