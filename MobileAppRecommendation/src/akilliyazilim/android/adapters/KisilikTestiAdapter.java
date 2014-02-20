package akilliyazilim.android.adapters;

import akilliyazilim.android.mobileapprecommendation.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class KisilikTestiAdapter extends BaseAdapter {
	String[] sorular;
	private final LayoutInflater mLayoutInflater;
	Context context;

	public KisilikTestiAdapter(Context context, String[] sorular) {
		// TODO Auto-generated constructor stub
		this.sorular = sorular;
		this.context = context;

		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sorular.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;

		if (view == null) {
			holder = new ViewHolder();
			view = mLayoutInflater.inflate(R.layout.last_survey_second_page,
					null);
			holder.soru = (TextView) view.findViewById(R.id.soru);
			holder.radioGroup = (RadioGroup) view.findViewById(R.id.cevaplar);

			view.setTag(holder);
		} else {

			holder = (ViewHolder) view.getTag();
		}
		String soru = sorular[position];
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

						Toast.makeText(
								context,
								holder.radioButton.getText() + " "
										+ (position + 1), Toast.LENGTH_SHORT)
								.show();

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
