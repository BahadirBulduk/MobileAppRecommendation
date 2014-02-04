package akilliyazilim.android.mobileapprecommendation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import akilliyazilim.android.Database.DatabaseHelper;
import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.Menu;

import com.example.mobileapprecommendation.R;

public class MainActivity extends Activity {

	public static DatabaseHelper database;
	public static String androidId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        database = new DatabaseHelper(getApplicationContext(),androidId+".db");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
