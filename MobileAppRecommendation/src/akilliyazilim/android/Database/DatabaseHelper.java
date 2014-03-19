package akilliyazilim.android.Database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final int VERSION = 1;
	public DatabaseHelper(Context context, String db_name) {
		
		super(context, db_name, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE CallLog (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId STRING,NumberOfIncomingCall INTEGER,NumberOfOutgoingCall INTEGER"
				+ ",TimeOfIncomingCall INTEGER,TimeOfOutgoingCall INTEGER,NumberOfDifferentCall INTEGER,NumberOfContact INTEGER,Date STRING);");
		db.execSQL("CREATE TABLE AppList (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId STRING,AppName String,InstallDate STRING,DeletedDate STRING)");
		db.execSQL("CREATE TABLE Recommendation (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId STRING,AppName String,InstallDate STRING,DeletedDate STRING)");
		db.execSQL("CREATE TABLE AppTracking (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId STRING,AppName STRING,DurationOfUse Integer,Date STRING)");
		db.execSQL("CREATE TABLE Survey (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId STRING,recommendationAppName STRING,answer1 STRING,answer2 STRING,answer3 STRING,playLink STRING)");
		db.execSQL("CREATE TABLE Survey2 (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId STRING,recommendationAppName STRING,benzer STRING,hafiza STRING,guvenlik STRING,pil STRING,begenmedi STRING,ilgi STRING,diger STRING)");
		db.execSQL("CREATE TABLE NotifId (id INTEGER PRIMARY KEY AUTOINCREMENT,next INTEGER)");
		db.execSQL("CREATE TABLE oneri (id INTEGER PRIMARY KEY AUTOINCREMENT,gorus STRING)");
		db.execSQL("CREATE TABLE iknaAnket (id INTEGER PRIMARY KEY AUTOINCREMENT,soru STRING,cevap STRING)");
		db.execSQL("CREATE TABLE kisilikAnket (id INTEGER PRIMARY KEY AUTOINCREMENT,soru STRING,cevap STRING)");
		db.execSQL("CREATE TABLE cinsiyetbolum (id INTEGER PRIMARY KEY AUTOINCREMENT,cinsiyet STRING,bolum STRING)");
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXIST CallLog");
		db.execSQL("DROP TABLE IF EXIST AppList");
		db.execSQL("DROP TABLE IF EXIST Recommendation");
		db.execSQL("DROP TABLE IF EXIST AppTracking");
		db.execSQL("DROP TABLE IF EXIST Survey");
		db.execSQL("DROP TABLE IF EXIST Survey2");
		db.execSQL("DROP TABLE IF EXIST NotifId");
		db.execSQL("DROP TABLE IF EXIST oneri");
		db.execSQL("DROP TABLE IF EXIST iknaAnket");
		db.execSQL("DROP TABLE IF EXIST kisilikAnket");
		db.execSQL("DROP TABLE IF EXIST cinsiyetbolum");
		onCreate(db);
	}

}
