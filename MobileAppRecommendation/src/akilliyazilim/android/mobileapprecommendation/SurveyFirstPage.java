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
			"Doktorumun tavsiyelerine kesinlikle uyarým.",
			"Otoritelerin ve konunun uzmanlarýnýn görüþlerine çok deðer veririm.",
			"Üstlerimin verdiði talimatlara her zaman uyarým.",
			"Otoritelerin ve konunun uzmanlarýnýn görüþlerini arkadaþlarýmýn görüþlerinden daha çok dikkate alýrým",
			"Baþkalarýnýn da ayný þeyi yaptýðýný biliyorsam, ayný þeyi yapmaya devam ederim.",
			"Sosyal çevremden birisi bir kitabýn güzel olduðunu söylerse, o kitabý okumaya eðilimli olurum.",
			"Yeni bir durumla karþýlaþtýðýmda karar verebilmek için diðerlerinin ne yaptýðýna bakarým.",
			"Aykýrý düþmemek benim için önemlidir.",
			"Ne yapacaðýmý belirlemek için çoðu kez diðerlerinin ne yaptýðýný dikkate alýrým." };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listSorular);
		radioGroup = (RadioGroup) listView.findViewById(R.id.cevaplar);
		button = (Button) findViewById(R.id.btnNext);
		text = (TextView) findViewById(R.id.textbaslik);
		text.setText("Ýkna Testi");

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
