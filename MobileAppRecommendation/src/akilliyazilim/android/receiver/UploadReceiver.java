package akilliyazilim.android.receiver;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.services.UploadService;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;

public class UploadReceiver extends BroadcastReceiver {

	String day, month;
	String year = "2014";
	int NumberOfIncomingCall = 0, NumberOfOutgoingCall = 0,
			TimeOfIncomingCall = 0, TimeOfOutgoingCall = 0,
			NumberOfDifferentCall = 0, NumberOfContact = 0;
	String date;
	ArrayList<String> phoneNumber;

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		/* UploadService calýsmaya baslar */

		/* Burada callLog db ye kaydedýlebilir */
		phoneNumber = new ArrayList<String>();
		day = String.valueOf(Calendar.DAY_OF_MONTH);
		month = String.valueOf(Calendar.MONTH);
		date = day + "-" + month + "-" + year;
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

			if (callDayTime.toString().equals(year + "-" + day + "-" + month)) {
				switch (dircode) {
				case CallLog.Calls.OUTGOING_TYPE:
					NumberOfOutgoingCall++;
					if(!phoneNumber.contains(phNumber)){
						phoneNumber.add(phNumber);
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
        DatabaseHelper database = new DatabaseHelper(context, androidId + ".db");
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("TelId", androidId);
		values.put("NumberOfIncomingCall", NumberOfIncomingCall);
		values.put("NumberOfOutgoingCall", NumberOfOutgoingCall);
		values.put("TimeOfIncomingCall", TimeOfIncomingCall);
		values.put("TimeOfOutgoingCall", TimeOfOutgoingCall);
		values.put("NumberOfDifferentCall", NumberOfDifferentCall);
		values.put("NumberOfContact", NumberOfContact);
		values.put("Date", date);
        db.insertOrThrow("CallLog", null, values);
        db.close();
        
        
        
		Intent i = new Intent(context, UploadService.class);
		context.startService(i);
	}
}
