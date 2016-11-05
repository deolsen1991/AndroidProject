package finalproject.danielolsen.finalproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONAdapter extends BaseAdapter {

	private static final String IMAGE_URL_BASE = "https://image.deckbrew.com/mtg/multiverseid/";

	Context mContext;
	LayoutInflater mInflater;
	JSONArray mJsonArray;

	public JSONAdapter(Context context, LayoutInflater inflater) {
		mContext = context;
		mInflater = inflater;
		mJsonArray = new JSONArray();
	}

	@Override
	public int getCount() {
		return mJsonArray.length();
	}

	@Override
	public Object getItem(int position) {
		return mJsonArray.optJSONObject(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		String imageID = "";
		String color = "";

		// check if the view already exists
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.row_cards, parent, false);

			//grabs the information needed for the home view and places it in a view holder.
			holder = new ViewHolder();
			holder.nameTextView = (TextView) convertView.findViewById(R.id.card_title);
			holder.jTextView = (TextView) convertView.findViewById(R.id.card_text);
			holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
			//holder.costTextView = (TextView) convertView.findViewById(R.id.cost);
			//holder.colorTextView = (TextView) convertView.findViewById(R.id.colors);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		JSONObject jsonObject = (JSONObject) getItem(position);
		try {
			JSONArray multiverse_id = jsonObject.getJSONArray("editions");
			imageID = multiverse_id.getJSONObject(0).getString("multiverse_id");
			//JSONArray colors = jsonObject.getJSONArray("colors");
			//color = colors.toString();
		}
		catch (Exception e)
		{

		}

		String imageURL = IMAGE_URL_BASE + imageID + ".jpg";

		Picasso.with(mContext)
				.load(imageURL)
				.placeholder(R.drawable.magic_card_back)
				.into(holder.thumbnailImageView);
		String cardName = "";
		String cardText = "";
		String cardCost = "";

		if (jsonObject.has("name")) {
			cardName = jsonObject.optString("name");
		}

	   if (jsonObject.has("text")) {
			cardText = jsonObject.optString("text");
       }

		if (jsonObject.has("cost")) {
			cardCost = jsonObject.optString("cost");
		}

        // Send these Strings to the TextViews for display
		holder.nameTextView.setText("Card Name: " + cardName);
		holder.jTextView.setText("Card Text: " + cardText);
		//holder.costTextView.setText("Card Cost: " + cardCost);
		//holder.colorTextView.setText("Card Color Identity: " + color);

		return convertView;
	}
	public void updateData(JSONArray jsonArray) {
		// update the adapter's dataset
		mJsonArray = jsonArray;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		public ImageView thumbnailImageView;
		public TextView nameTextView;
		public TextView jTextView;
		public TextView costTextView;
		public TextView colorTextView;
		public TextView typeTextView;
	}
}