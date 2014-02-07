package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.Database.DatabaseHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.mobileapprecommendation.R;

public class DownloadPage extends Activity {

	TextView textLink1, textLink2, textLink3;
	String whereDatabase;
	SQLiteDatabase db;
	ContentValues values;
	String appPopulerLinkList, appEditorLinkList;
	String androidId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);

		Bundle extras = getIntent().getExtras();
		whereDatabase = extras.getString("appName");
		appPopulerLinkList = extras.getString("appPopulerLinkList");
		appEditorLinkList = extras.getString("appEditorLinkList");
		Log.i("appPopulerLinkList",appPopulerLinkList);

		 androidId = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		values = new ContentValues();
		textLink1 = (TextView) findViewById(R.id.textLink1);
		textLink2 = (TextView) findViewById(R.id.textLink2);
		textLink3 = (TextView) findViewById(R.id.textLink3);

		// TextLink1--> appPopulerLinkList TextLink2-->appEditorLinkList
		textLink1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHelper database = new DatabaseHelper(getApplicationContext(),
						androidId + ".db");
				db = database.getWritableDatabase();
				values.put("playLink", appPopulerLinkList);
				db.update("Survey", values, "recommendationAppName = ?",
						new String[] { whereDatabase });
				db.close();
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(appPopulerLinkList));
				startActivity(browserIntent);
				

			}
		});
		textLink2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHelper database = new DatabaseHelper(getApplicationContext(),
						androidId + ".db");
				db = database.getWritableDatabase();
				values.put("playLink", appEditorLinkList);
				db.update("Survey", values, "recommendationAppName = ?",
						new String[] { whereDatabase });
				db.close();
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(appEditorLinkList));
				startActivity(browserIntent);
				
			}
		});
		textLink3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHelper database = new DatabaseHelper(getApplicationContext(),
						androidId + ".db");
				db = database.getWritableDatabase();
				values.put("playLink", "yuklemedi");
				db.update("Survey", values, "recommendationAppName = ?",
						new String[] { whereDatabase });
				db.close();
			}
		});

	}
}
