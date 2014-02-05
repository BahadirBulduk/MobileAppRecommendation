package AppList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import akilliyazilim.android.Database.DatabaseHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.util.Log;

public class AppList {

	public PackageManager packageManager;;
	public List<PackageInfo> packageList1;
	DatabaseHelper database;
	SQLiteDatabase db;
	String androidId;
	ContentValues values;
	
	@SuppressLint("NewApi") public AppList(Activity a,String id) {
		// TODO Auto-generated constructor stub
		androidId = id;
		database = new DatabaseHelper(a.getApplicationContext(), id+".db");
		db = database.getWritableDatabase();
		values = new ContentValues();
		
		packageManager = a.getPackageManager();
		List<PackageInfo> packageList = packageManager
				.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		packageList1 = new ArrayList<PackageInfo>();
		/* Sistem uygulamalarýný listeye ekleme */
		for (PackageInfo pi : packageList) {
			boolean b = isSystemPackage(pi);
			if (!b) {
				packageList1.add(pi);
				values.put("TelId", androidId);
				values.put("AppName", pi.packageName.toString());
				values.put("InstallDate", String.valueOf(pi.firstInstallTime));
				values.putNull("DeletedDate");
				db.insert("AppList", null, values);
			}
		}
		db.close();
	}
	private boolean isSystemPackage(PackageInfo packageInfo) {
		return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
				: false;
	}
	@SuppressLint("SimpleDateFormat")
	public String setDateFormat(long time) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strDate = formatter.format(date);
		return strDate;
	}
	
}
