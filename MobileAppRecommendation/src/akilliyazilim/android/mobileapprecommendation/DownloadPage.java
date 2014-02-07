package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.Database.DatabaseHelper;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
		Log.i("appPopulerLinkList", appPopulerLinkList);

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
				DatabaseHelper database = new DatabaseHelper(
						getApplicationContext(), androidId + ".db");
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
				DatabaseHelper database = new DatabaseHelper(
						getApplicationContext(), androidId + ".db");
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
				final Dialog dialog = new Dialog(DownloadPage.this);

				dialog.setContentView(R.layout.dialog);
				dialog.setTitle("Uygulamayý yüklemeyi tercih etmeme sebeplerinizi lütfen aþaðýda");

				final EditText editText = (EditText) dialog
						.findViewById(R.id.editTextKeywordsToBlock);
				final CheckBox checkBox1 = (CheckBox) dialog
						.findViewById(R.id.checkBox1);
				final CheckBox checkBox2 = (CheckBox) dialog
						.findViewById(R.id.checkBox2);
				final CheckBox checkBox3 = (CheckBox) dialog
						.findViewById(R.id.checkBox3);
				final CheckBox checkBox4 = (CheckBox) dialog
						.findViewById(R.id.checkBox4);
				final CheckBox checkBox5 = (CheckBox) dialog
						.findViewById(R.id.checkBox5);
				final CheckBox checkBox6 = (CheckBox) dialog
						.findViewById(R.id.checkBox6);
				Button btnBlock = (Button) dialog
						.findViewById(R.id.buttonBlockByKeyword);
				Button btnCancel = (Button) dialog
						.findViewById(R.id.buttonCancelBlockKeyword);
				dialog.show();

				btnBlock.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						if (checkBox1.isChecked()) {
						}
						if (checkBox2.isChecked()) {
						}
						if (checkBox3.isChecked()) {
						}
						if (checkBox4.isChecked()) {
						}
						if (checkBox5.isChecked()) {
						}
						if (checkBox6.isChecked()) {
						}
						// editText.getText();
						dialog.dismiss();
					}
				});

				btnCancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();

					}
				});
				DatabaseHelper database = new DatabaseHelper(
						getApplicationContext(), androidId + ".db");
				db = database.getWritableDatabase();
				values.put("playLink", "yuklemedi");
				db.update("Survey", values, "recommendationAppName = ?",
						new String[] { whereDatabase });
				db.close();
			}
		});

	}
}
