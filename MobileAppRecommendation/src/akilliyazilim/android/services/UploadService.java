package akilliyazilim.android.services;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.mobileapprecommendation.MainActivity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.provider.Settings;

public class UploadService extends Service {
	int serverResponseCode = 0;
	public String upLoadServerUri = "http://akilliyazilim.org/MobileSurveyApp/UploadToServer.php";
	String androidId;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		if (isNetworkAvailable()) {
			// internet var
			androidId = Settings.Secure.getString(getContentResolver(),
					Settings.Secure.ANDROID_ID);
			int resultResponse = uploadFile(getDatabasePath(
					androidId + ".db").toString());

			if (resultResponse == 200) {
				// i�lem basar�l�. tablolar s�f�rland�. Servis durduruldu.
				DatabaseHelper database = new DatabaseHelper(getApplicationContext(),androidId+".db");
				SQLiteDatabase db = database.getWritableDatabase();
				db.delete("CallLog", null, null);
				db.delete("AppTracking", null, null);
				db.close();
				stopSelf(); //Murat Bundan Emin De�il :D
			}

		} else {
			// internet yok.
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

			// source file problemi var burada
			return 0;

		} else {
			/* Servlet ' e URL connection olustur */
			try {
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);

				// URL ^e bir tane Http connection olustur
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // inputlara izin ver
				conn.setDoOutput(true); // outputlara izin ver
				conn.setUseCaches(false); // cachlenmi� kopya kullanma
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// buffer maksimum size olustur
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// dosyay� oku ve yaz
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
				String serverResponseMessage = conn.getResponseMessage();

				// stream kapanmal� // ?? exception olursa ?
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return serverResponseCode;

	}

}
