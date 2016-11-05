package finalproject.danielolsen.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    //All use of the URL will need this
    private static final String IMAGE_URL_BASE = "https://image.deckbrew.com/mtg/multiverseid/";

    public TextView dNameTextView;

    @Override
    /*
     * On the creation of the details view, gets all the data pulled in from the intent and places it in the appropriate spot, including the picture.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.card_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = (ImageView) findViewById(R.id.big_card);
        dNameTextView = (TextView) findViewById(R.id.card_textBox);
        String cardID = this.getIntent().getExtras().getString("cardPicture");
        String cardName = this.getIntent().getExtras().getString("cardName");
        String mImageURL = IMAGE_URL_BASE + cardID + ".jpg";
        dNameTextView.setText("Card Name: " + cardName);
        String cardTypes = this.getIntent().getExtras().getString("cardTypes");
        cardTypes = cardTypes.replace("[","").replace("]","").replace("\"","");
        dNameTextView.append("\nCard Types: " + cardTypes);
        String superTypes = this.getIntent().getExtras().getString("cardSuperTypes");
        if(!(superTypes == null)) {
            superTypes = superTypes.replace("[", "").replace("]", "").replace("\"", "");
            dNameTextView.append("\nSupertypes: " + superTypes);
        }
        String subTypes = this.getIntent().getExtras().getString("cardSubTypes");
        if(!(subTypes == null)) {
            subTypes = subTypes.replace("[", "").replace("]", "").replace("\"", "");
            dNameTextView.append("\nSubtypes: " + subTypes);
        }
        String colors = this.getIntent().getExtras().getString("cardColors");
        if(!(colors == null)) {
            colors = colors.replace("[", "").replace("]", "").replace("\"", "");
            dNameTextView.append("\nColor Identity: " + colors);
        }
        String cardText = this.getIntent().getExtras().getString("cardText");
        if(!(cardText == null)) {
            dNameTextView.append("\nCard Text: \n\n" + this.getIntent().getExtras().getString("cardText") + "\n");
        }
        dNameTextView.append("\nCard Cost: " + this.getIntent().getExtras().getString("cardCost"));
        String cardToughness = this.getIntent().getExtras().getString("cardToughness");
        String cardPower = this.getIntent().getExtras().getString("cardPower");
        if(!(cardPower == null)) {
            dNameTextView.append("\nCard Power: " + this.getIntent().getExtras().getString("cardPower"));
        }
        if(!(cardToughness == null)) {
            dNameTextView.append("\nCard Toughness: " + this.getIntent().getExtras().getString("cardToughness"));
        }

        String cardLoyalty = this.getIntent().getExtras().getString("cardLoyalty");
        if(!(cardLoyalty == null)) {
            dNameTextView.append("\nCard Loyalty: " + this.getIntent().getExtras().getString("cardLoyalty"));
        }


        Picasso.with(this).load(mImageURL).placeholder(R.drawable.magic_card_back).into(imageView);

    }

}
