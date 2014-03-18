package akilliyazilim.android.mobileapprecommendation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class LastSurveyInfoPage extends Activity {

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

				startActivity(new Intent(LastSurveyInfoPage.this,
						SurveyFirstPage.class));
				finish();
			}
		});

	}
}
