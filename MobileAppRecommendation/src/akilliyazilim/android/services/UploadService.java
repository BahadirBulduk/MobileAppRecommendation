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
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;

public class UploadService extends Service {
	int serverResponseCode = 0;
	public String upLoadServerUri = "http://akilliyazilim.org/MobileSurveyApp/UploadToServer.php";
	String androidId;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressLint("NewApi") @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("control", "2");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		if (isNetworkAvailable()) {
			// internet var
			Log.i("control", "3");

			androidId = Settings.Secure.getString(getContentResolver(),
					Settings.Secure.ANDROID_ID);
			int resultResponse = uploadFile(getDatabasePath(
					androidId + ".db").toString());
			Log.i("control", resultResponse +"");

			if (resultResponse == 200) {
				// iþlem basarýlý. tablolar sýfýrlandý. Servis durduruldu.
				DatabaseHelper database = new DatabaseHelper(getApplicationContext(),androidId+".db");
				SQLiteDatabase db = database.getWritableDatabase();
				db.delete("CallLog", null, null);
				db.delete("AppTracking", null, null);
				db.close();
				stopSelf(); //Murat Bundan Emin Deðil :D
				Log.i("control", "basarili");

			}

		} else {
			// internet yok.
			Log.i("control", "internet yok");

		}
		return super.onStartCommand(intent, flags, startId);

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
				Log.i("control", "conn");
				conn.setDoInput(true); // inputlara izin ver
				conn.setDoOutput(true); // outputlara izin ver
				conn.setUseCaches(false); // cachlenmiþ kopya kullanma
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);
				Log.i("control", "output once");
				OutputStream o = conn.getOutputStream();
				Log.i("control","o sonrasý");
				dos = new DataOutputStream(conn.getOutputStream());
				Log.i("control", "output");
				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);
				Log.i("control", "write");
				// buffer maksimum size olustur
				bytesAvailable = fileInputStream.available();
				Log.i("control", "bytesAvailable");
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// dosyayý oku ve yaz
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				Log.i("control", "read");

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				Log.i("control", "dos write");

				// Serveerdan gelen response code
				Log.i("control", "responsecode");

				serverResponseCode = conn.getResponseCode();
				Log.i("control", "code sonra");

				String serverResponseMessage = conn.getResponseMessage();
				Log.i("control", "basarili");
				// stream kapanmalý // ?? exception olursa ?
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.i("control", "11");
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				Log.i("control", "12");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.i("control", "13");
				e.printStackTrace();
			} catch (Exception e){
				Log.i("control", "14");
				e.printStackTrace();
			}
		}
		return serverResponseCode;

	}

}
