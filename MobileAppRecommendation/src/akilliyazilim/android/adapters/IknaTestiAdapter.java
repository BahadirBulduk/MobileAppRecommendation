package akilliyazilim.android.adapters;

import akilliyazilim.android.Database.DatabaseHelper;
import akilliyazilim.android.mobileapprecommendation.R;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

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
		id =Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID);
		dbHelper  = new DatabaseHelper(context, id+".db");
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
			holder.radioGroup = (RadioGroup) view.findViewById(R.id.cevaplar);
			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();
		}
		String soru = sorular[pos];
		holder.soru.setText(soru);

		holder.radioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						int selectedId = holder.radioGroup
								.getCheckedRadioButtonId();

						holder.radioButton = (RadioButton) group
								.findViewById(selectedId);
						
						//dbnin açýlmasýný kapanmasýný burda kontrol ettim.
						//Kontrol edeceðin yerler soru ve cevap deðiþkenleri dogru mu?
						db = dbHelper.getWritableDatabase();
						ContentValues values = new ContentValues();
						values.put("soru",  sorular[pos]);
						values.put("cevap",holder.radioButton.getText().toString());
						db.insertOrThrow("anket1", null, values);
						db.close();

					}
				});

		return view;
	}

	private static class ViewHolder {

		TextView soru;
		RadioGroup radioGroup;
		RadioButton radioButton;

	}

}
