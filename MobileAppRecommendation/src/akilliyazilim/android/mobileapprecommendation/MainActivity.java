package akilliyazilim.android.mobileapprecommendation;

import AppList.AppList;
import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.services.AppTrackingService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;

import com.example.mobileapprecommendation.R;

public class MainActivity extends Activity {

	public static DatabaseHelper database;
	public static String androidId;
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        database = new DatabaseHelper(getApplicationContext(),androidId+".db");
        database.close();
//        AppList a = new AppList(this);
//        
//        for(int i =0;i<a.packageList1.size();i++){
//        	Log.i(a.packageList1.get(i).applicationInfo.packageName.toString(),a.setDateFormat(a.packageList1.get(i).firstInstallTime));
//        }
        
       initialize();
        
    }

    private void initialize() {
		/* Manual Kontrol */
		getBaseContext().getApplicationContext().sendBroadcast(
				new Intent("Manual_Start"));
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
