package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.adapters.IknaTestiAdapter;
import akilliyazilim.android.constants.Constants;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listSorular);
		button = (Button) findViewById(R.id.btnNext);
		text = (TextView) findViewById(R.id.textbaslik);
		text.setText("Ýkna Testi");

		IknaTestiAdapter adapter = new IknaTestiAdapter(this,
				Constants.iknaTestiSorular);

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
