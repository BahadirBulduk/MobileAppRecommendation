package akilliyazilim.android.adapters;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.mobileapprecommendation.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class IknaTestiAdapter extends BaseAdapter {

	String[] sorular;
	private final LayoutInflater mLayoutInflater;
	Context context;
	DatabaseHelper dbHelper;
	SQLiteDatabase db;
	String id;

	public IknaTestiAdapter(Context context, String[] sorular) {
		// TODO Auto-generated constructor stub
		this.sorular = sorular;
		this.context = context;
		id = Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID);
		dbHelper = new DatabaseHelper(context, id + ".db");
		db = dbHelper.getWritableDatabase();
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sorular.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int pos, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;

		if (view == null) {
			holder = new ViewHolder();
			view = mLayoutInflater.inflate(R.layout.last_survey_first_page,
					null);
			holder.soru = (TextView) view.findViewById(R.id.soru);
			holder.spinner = (Spinner) view.findViewById(R.id.spinner1);
			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();
		}
		String soru = sorular[pos];
		holder.soru.setText(soru);

		holder.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("soru", sorular[pos]);
				values.put("cevap", holder.spinner.getSelectedItem().toString());
				db.insertOrThrow("iknaAnket", null, values);
				db.close();
				// holder.spinner.getSelectedItem().toString()
				// sorular[position]
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		return view;
	}

	private static class ViewHolder {

		TextView soru;
		Spinner spinner;

	}

}
