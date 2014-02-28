package akilliyazilim.android.services;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.constants.Constants;
import akilliyazilim.android.mobileapprecommendation.SurveySecondPage;
import akilliyazilim.android.receiver.AlarmReceiver;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class UploadService extends Service {
	int serverResponseCode = 0;
	public String upLoadServerUri = "http://akilliyazilim.org/MobileSurveyApp/UploadToServer.php";
	String androidId;
	Context context;
	int resultResponse;
	static Activity activity;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		context = getApplicationContext();
		if (isNetworkAvailable()) {
			// internet var
			Constants.code = 1;
			androidId = Settings.Secure.getString(getContentResolver(),
					Settings.Secure.ANDROID_ID);
			new AsynUpload().execute();
			Log.i("control", resultResponse + "");

			if (resultResponse == 200) {
				// iþlem basarýlý. tablolar sýfýrlandý. Servis durduruldu.

				DatabaseHelper database = new DatabaseHelper(
						getApplicationContext(), androidId + ".db");
				SQLiteDatabase db = database.getWritableDatabase();
				db.delete("CallLog", null, null);
				db.delete("AppTracking", null, null);
				db.close();
				stopSelf(); // Murat Bundan Emin Deðil :D
				Log.i("control", "basarili");

			}

		} else {
			// internet yok.
			Log.i("control", "internet yok");
			Toast.makeText(getApplicationContext(),
					"Lütfen internetinizi açýnýz", Toast.LENGTH_LONG).show();
			Constants.code = 1111;
		}
		return super.onStartCommand(intent, flags, startId);

	}

	public static void setActivity(Activity a) {
		activity = a;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/**
	 * 
	 * @param sourceFileUri
	 *            database yolu
	 * @return server dan gelen response kod
	 */
	private int uploadFile(String sourceFileUri) {

		String fileName = sourceFileUri;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {
			Log.i("control", "dosya problemi");
			// source file problemi var burada
			return 0;

		} else {
			/* Servlet ' e URL connection olustur */
			try {
				Log.i("control", "gonderme deneme");
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);
				Log.i("control", "url");
				// URL ^e bir tane Http connection olustur
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // inputlara izin ver
				conn.setDoOutput(true); // outputlara izin ver
				conn.setUseCaches(false); // cachlenmiþ kopya kullanma
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);
				OutputStream o = conn.getOutputStream();
				dos = new DataOutputStream(conn.getOutputStream());
				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);
				// buffer maksimum size olustur
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// dosyayý oku ve yaz
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Serveerdan gelen response code

				serverResponseCode = conn.getResponseCode();
				Log.i("control", serverResponseCode + "");
				String serverResponseMessage = conn.getResponseMessage();
				Log.i("control", "basarili");
				// stream kapanmalý // ?? exception olursa ?
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.i("control", "FileNotFoundException");
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				Log.i("control", "MalformedURLException");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.i("control", "IOException");
				e.printStackTrace();
			} catch (Exception e) {
				Log.i("control", "Exception");
				e.printStackTrace();
			}
		}
		return serverResponseCode;

	}

	public class AsynUpload extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(activity);
			dialog.setMessage("Cevaplarýnýz kaydediliyor lütfen bekleyiniz...");
			dialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			resultResponse = uploadFile(getDatabasePath(androidId + ".db")
					.toString());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Intent service1 = new Intent(getApplicationContext(),
					RecomendationService.class);
			stopService(service1);
			dialog.dismiss();
			Toast.makeText(
					getApplicationContext(),
					"Deneyimiz burada bitmiþtir. Yardýmlarýnýz için teþekkür ederiz.",
					Toast.LENGTH_LONG).show();

			Intent service2 = new Intent(getApplicationContext(),
					TimerService.class);
			stopService(service2);
			Intent service3 = new Intent(getApplicationContext(),
					UploadService.class);
			stopService(service3);

			Context ctx = getApplicationContext();
			/** */
			AlarmManager am = (AlarmManager) ctx
					.getSystemService(Context.ALARM_SERVICE);
			Intent cancelServiceIntent = new Intent(ctx, TimerService.class);
			PendingIntent cancelServicePendingIntent = PendingIntent
					.getBroadcast(ctx, 0, // integer constant used to identify
											// the service
							cancelServiceIntent, 0 // no FLAG needed for a
													// service cancel
					);
			am.cancel(cancelServicePendingIntent);
			
			 ComponentName receiver = new ComponentName(UploadService.this, AlarmReceiver.class);
			     PackageManager pm = getPackageManager();
			     pm.setComponentEnabledSetting(receiver,
			             PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
			             PackageManager.DONT_KILL_APP);

//			System.gc();
//			android.os.Process.killProcess(android.os.Process.myPid());
//			System.exit(0);
			
		}

	}
}
