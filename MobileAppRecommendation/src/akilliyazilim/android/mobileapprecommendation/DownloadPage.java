package akilliyazilim.android.mobileapprecommendation;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.constants.Constants;
import akilliyazilim.android.services.RecomendationService;
import akilliyazilim.android.services.TimerService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

@SuppressLint("NewApi")
public class DownloadPage extends Activity {

	Button textLink1, textLink2, textLink3, textLink4;
	String whereDatabase;
	SQLiteDatabase db;
	ContentValues values, values2;
	String appPopulerLinkList, appEditorLinkList,appMostUsedLinkList;
	String androidId;
	int count;
	DatabaseHelper database;
	int count2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		TextView tv = (TextView)findViewById(R.id.textView1);
		

		androidId = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		
		database  = new DatabaseHelper(getApplicationContext(), androidId+".db");
		db = database.getReadableDatabase();
		String query = "SELECT next FROM NotifId";
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		count = Integer.parseInt(c.getString(0));
		whereDatabase = Constants.appNameList[count];
		tv.setText(whereDatabase);
		appPopulerLinkList = Constants.appPopulerLinkList[count];
		appEditorLinkList = Constants.appEditorLinkList[count];
		appMostUsedLinkList = Constants.appEditorLinkList[count];
		count2 = count;
		db.close();
		values = new ContentValues();
		values2 = new ContentValues();
		textLink1 = (Button) findViewById(R.id.textLink1);	//pop�ler uygulama linki.
		textLink2 = (Button) findViewById(R.id.textLink2);	//edit�r�n se�imi linki.
		textLink3 = (Button) findViewById(R.id.textLink3);	//y�klemek istemiyorum butonu.
		textLink4 = (Button) findViewById(R.id.textLink4); // En �ok kullan�lan uygulamalar linki.
		
		// TextLink1--> appPopulerLinkList TextLink2-->appEditorLinkList
		textLink1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textLink1.setClickable(false);
				textLink2.setClickable(false);
				textLink3.setClickable(false);
				textLink4.setClickable(false);

				db = database.getWritableDatabase();
				values.put("playLink", appPopulerLinkList+"-populer uygulama");
				db.update("Survey", values, "recommendationAppName = ?",
						new String[] { whereDatabase });
				if (count < 9) {
					values2.put("next", (count + 1));
					db.update("NotifId", values2, null, null);
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Deneyimiz burada bitmi�tir. Yard�mlar�n�z i�in te�ekk�r ederiz.",
							Toast.LENGTH_LONG).show();
					Intent stopIntent = new Intent(DownloadPage.this,
							RecomendationService.class);
					stopService(stopIntent);
					Intent stopIntent1 = new Intent(DownloadPage.this,
							TimerService.class);
					stopService(stopIntent1);
				}

				db.close();
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(appPopulerLinkList));
				startActivity(browserIntent);
				finish();
			}
		});
		textLink2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textLink1.setClickable(false);
				textLink2.setClickable(false);
				textLink3.setClickable(false);
				textLink4.setClickable(false);


				db = database.getWritableDatabase();
				values.put("playLink", appEditorLinkList+"-editorun secimi");
				db.update("Survey", values, "recommendationAppName = ?",
						new String[] { whereDatabase });
				if (count < 9) {
					values2.put("next", (count + 1));
					db.update("NotifId", values2, null, null);
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Deneyimiz burada bitmi�tir. Yard�mlar�n�z i�in te�ekk�r ederiz.",
							Toast.LENGTH_LONG).show();
					Intent stopIntent = new Intent(DownloadPage.this,
							RecomendationService.class);
					stopService(stopIntent);
					Intent stopIntent1 = new Intent(DownloadPage.this,
							TimerService.class);
					stopService(stopIntent1);
				}
				db.close();
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(appEditorLinkList));
				startActivity(browserIntent);
				finish();

			}
		});
		textLink3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textLink1.setClickable(false);
				textLink2.setClickable(false);
				textLink3.setClickable(false);
				textLink4.setClickable(false);

				final Dialog dialog = new Dialog(DownloadPage.this);

				dialog.setContentView(R.layout.dialog);
				dialog.setTitle("Uygulamay� y�klemeyi tercih etmeme sebeplerinizi l�tfen a�a��da");

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
						db = database.getWritableDatabase();
						values.put("playLink", "yuklemedi");
						db.update("Survey", values,
								"recommendationAppName = ?",
								new String[] { whereDatabase });
						if (count < 9) {
							values2.put("next", (count + 1));
							db.update("NotifId", values2, null, null);
						} else {
							Toast.makeText(
									getApplicationContext(),
									"Deneyimiz burada bitmi�tir. Yard�mlar�n�z i�in te�ekk�r ederiz.",
									Toast.LENGTH_LONG).show();
							Intent stopIntent = new Intent(DownloadPage.this,
									RecomendationService.class);
							stopService(stopIntent);
							Intent stopIntent1 = new Intent(DownloadPage.this,
									TimerService.class);
							stopService(stopIntent1);

						}
						int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0;
						if (checkBox1.isChecked()) {
							c1 = 1;
						}
						if (checkBox2.isChecked()) {
							c2 = 1;
						}
						if (checkBox3.isChecked()) {
							c3 = 1;
						}
						if (checkBox4.isChecked()) {
							c4 = 1;
						}
						if (checkBox5.isChecked()) {
							c5 = 1;
						}
						if (checkBox6.isChecked()) {
							c6 = 1;
						}
						// editText.getText();
						ContentValues value = new ContentValues();
						value.put("recommendationAppName",
								Constants.appNameList[count2]);
						value.put("benzer", c1 + "");
						value.put("hafiza", c2 + "");
						value.put("guvenlik", c3 + "");
						value.put("pil", c4 + "");
						value.put("begenmedi", c5 + "");
						value.put("ilgi", c6 + "");
						if (!editText.getText().toString().isEmpty()) {
							value.put("diger", editText.getText().toString());
						} else {
							value.put("diger", "baska neden yok");
						}
						db.insertOrThrow("Survey2", null, value);
						db.close();
						dialog.dismiss();
						finish();
					}
				});

				btnCancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						db = database.getWritableDatabase();
						ContentValues cv = new ContentValues();
						cv.put("next", (count - 1));
						db.update("NotifId", cv, null, null);
						db.close();
						textLink1.setClickable(true);
						textLink2.setClickable(true);
						textLink3.setClickable(true);
						textLink4.setClickable(true);
						dialog.dismiss();
					}
				});
			}
		});
		textLink4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				textLink1.setClickable(false);
				textLink2.setClickable(false);
				textLink3.setClickable(false);
				textLink4.setClickable(false);

				db = database.getWritableDatabase();
				values.put("playLink", appMostUsedLinkList+"-cok kullanilan uygulama");
				db.update("Survey", values, "recommendationAppName = ?",
						new String[] { whereDatabase });
				if (count < 9) {
					values2.put("next", (count + 1));
					db.update("NotifId", values2, null, null);
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Deneyimiz burada bitmi�tir. Yard�mlar�n�z i�in te�ekk�r ederiz.",
							Toast.LENGTH_LONG).show();
					Intent stopIntent = new Intent(DownloadPage.this,
							RecomendationService.class);
					stopService(stopIntent);
					Intent stopIntent1 = new Intent(DownloadPage.this,
							TimerService.class);
					stopService(stopIntent1);
				}
				db.close();
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(appMostUsedLinkList));
				startActivity(browserIntent);
				finish();
			}
		});
		
	
	}
}
