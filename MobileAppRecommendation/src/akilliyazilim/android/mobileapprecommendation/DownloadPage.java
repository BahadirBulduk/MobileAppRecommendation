package akilliyazilim.android.mobileapprecommendation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.mobileapprecommendation.R;

public class DownloadPage extends Activity {

	TextView textLink1, textLink2, textLink3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);

		textLink1 = (TextView) findViewById(R.id.textLink1);
		textLink2 = (TextView) findViewById(R.id.textLink2);
		textLink3 = (TextView) findViewById(R.id.textLink3);

		textLink1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		textLink2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		textLink3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

	}
}
