package akilliyazilim.android.receiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import android.provider.CallLog;
import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.services.UploadService;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;

public class UploadReceiver extends BroadcastReceiver {

	String gun, ay;
	String yil ;
	int NumberOfIncomingCall = 0, NumberOfOutgoingCall = 0,
			TimeOfIncomingCall = 0, TimeOfOutgoingCall = 0,
			NumberOfDifferentCall = 0, NumberOfContact = 0;
//	String date;
	ArrayList<String> phoneNumber;

	@SuppressLint("SimpleDateFormat") @Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		/* UploadService calýsmaya baslar */

		/* Burada callLog db ye kaydedýlebilir */

		Calendar c2 = Calendar.getInstance();
		SimpleDateFormat d = new SimpleDateFormat("dd");
		SimpleDateFormat m = new SimpleDateFormat("MM");
		SimpleDateFormat y = new SimpleDateFormat("yyyy");
		String gun = d.format(c2.getTime());
		String ay = m.format(c2.getTime());
		String yil = y.format(c2.getTime());

		//		Time today = new Time(Time.getCurrentTimezone());
//		today.setToNow();
//		Toast.makeText(context, today.monthDay + "", Toast.LENGTH_LONG).show();

		
		phoneNumber = new ArrayList<String>();
//		day = String.valueOf(Calendar.DAY_OF_MONTH);
//		month = String.valueOf(Calendar.MONTH);
//		date = day + "-" + month + "-" + year;
		String androidId = Settings.Secure.getString(
				context.getContentResolver(), Settings.Secure.ANDROID_ID);

		Cursor managedCursor = context.getContentResolver().query(
				CallLog.Calls.CONTENT_URI, null, null, null, null);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		while (managedCursor.moveToNext()) {

			String phNumber = managedCursor.getString(number);
			String callType = managedCursor.getString(type);
			String callDate = managedCursor.getString(date);
			Date callDayTime = new Date(Long.valueOf(callDate));
			String callDuration = managedCursor.getString(duration);
			String dir = null;
			int dircode = Integer.parseInt(callType);
			Log.i("compare1", callDayTime.toString());
			Log.i("compare2", yil + "-" + ay + "-" + ay);

			if (callDayTime.toString().equals(yil + "-" + ay + "-" + gun)) {
				switch (dircode) {
				case CallLog.Calls.OUTGOING_TYPE:
					NumberOfOutgoingCall++;
					if(!phoneNumber.contains(phNumber)){
						phoneNumber.add(phNumber);
						Log.i("phoneNumber", "eklendi giden");
						NumberOfDifferentCall++;
					}
					TimeOfOutgoingCall +=Integer.parseInt(callDuration);
					break;
				case CallLog.Calls.INCOMING_TYPE:
					NumberOfIncomingCall++;
					if(!phoneNumber.contains(phNumber)){
						phoneNumber.add(phNumber);
						NumberOfDifferentCall++;
					}
					TimeOfIncomingCall += Integer.parseInt(callDuration);
					break;
				case CallLog.Calls.MISSED_TYPE:
					dir = "MISSED";
					break;
				}
			}
			phoneNumber.size();
			
		}
		managedCursor.close();

		ContentResolver c = context.getContentResolver();
		ArrayList<String> alContacts = new ArrayList<String>();
        Cursor cursor = c.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0){
                    Cursor pCur = c.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                    while (pCur.moveToNext()){
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        alContacts.add(contactNumber);
                        break;
                    }
                    pCur.close();
                }
            } while (cursor.moveToNext()) ;
        }
        NumberOfContact =alContacts.size();
		Calendar c3 = Calendar.getInstance();
		SimpleDateFormat sdf2 = new SimpleDateFormat("H:m:s");
		String strdate2 = sdf2.format(c3.getTime());
		DatabaseHelper database  = new DatabaseHelper(context, androidId+"-"+strdate2+".db");
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("TelId", androidId);
		values.put("NumberOfIncomingCall", NumberOfIncomingCall);
		values.put("NumberOfOutgoingCall", NumberOfOutgoingCall);
		values.put("TimeOfIncomingCall", TimeOfIncomingCall);
		values.put("TimeOfOutgoingCall", TimeOfOutgoingCall);
		values.put("NumberOfDifferentCall", NumberOfDifferentCall);
		values.put("NumberOfContact", NumberOfContact);
		values.put("Date", yil + "-" + gun + "-" + ay);
        db.insertOrThrow("CallLog", null, values);
        db.close();
        Log.i("db","database");
        
        
		Intent i = new Intent(context, UploadService.class);
		context.startService(i);
	}
}
