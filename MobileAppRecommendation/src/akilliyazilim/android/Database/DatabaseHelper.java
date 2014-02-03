package akilliyazilim.android.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final int VERSION = 1;

	
	public DatabaseHelper(Context context,String db_name) {
		super(context, db_name, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE CallLog (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId INTEGER,NumberOfIncomingCall INTEGER,NumberOfOutgoingCall INTEGER"
				+ ",TimeOfIncomingCall INTEGER,TimeOfOutgoingCall INTEGER,NumberOfDifferentCall INTEGER,NumberOfContact INTEGER,Date STRING);");
		db.execSQL("CREATE TABLE AppList (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId INTEGER,AppName String,InstallDate STRING,DeletedDate STRING)");
		db.execSQL("CREATE TABLE Recommendation (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId INTEGER,AppName String,InstallDate STRING,DeletedDate STRING)");
		db.execSQL("CREATE TABLE AppTracking (id INTEGER PRIMARY KEY AUTOINCREMENT,TelId INTEGER,AppName STRING,DurationOfUse Integer,Date STRING)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXIST CallLog");
		db.execSQL("DROP TABLE IF EXIST AppList");
		db.execSQL("DROP TABLE IF EXIST Recommendation");
		db.execSQL("DROP TABLE IF EXIST AppTracking");		
		onCreate(db);

	}

}
