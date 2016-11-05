package finalproject.danielolsen.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener, AdapterView.OnItemClickListener {

    JSONAdapter mJSONAdapter;
    Button toLC;
    Button mainButton;
    EditText mainEditText;
    ListView mainListView;
    private static final String QUERY_URL = "http://api.deckbrew.com/mtg/cards?name=";
    ProgressDialog mDialog;
    int a = 0;
    JSONArray fedInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainButton = (Button) findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);
        mainEditText = (EditText) findViewById(R.id.main_edittext);
        mainListView = (ListView) findViewById(R.id.main_listview);
        mainListView.setOnItemClickListener(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        mJSONAdapter = new JSONAdapter(this, getLayoutInflater());
        mainListView.setAdapter(mJSONAdapter);
        toLC = (Button) findViewById(R.id.toLC_button);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Searching for Cards");
        mDialog.setCancelable(true);
        toLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TestIntent", v.toString());
                Intent lcIntent = new Intent(MainActivity.this, LifeCounterActivity.class);
                startActivity(lcIntent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v == mainButton) {
            if (mainEditText.getText().toString().isEmpty()) {
                return;
            }
            queryCards(mainEditText.getText().toString());
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
        Intent detailIntent = new Intent(this, DetailActivity.class);
        String cardID = "";
        try
        {
            //API is borked for colorless creatures/cards right now
            //Color is now read in last, but a fix for colorless cards is needed.
            //This is not a good way to read in cards, but it will work for now.
            //JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
            JSONArray multiverse_id = jsonObject.getJSONArray("editions");
            cardID = multiverse_id.getJSONObject(0).getString("multiverse_id");
            detailIntent.putExtra("cardName", jsonObject.getString("name"));
            detailIntent.putExtra("cardTypes", jsonObject.getJSONArray("types").toString());
            if(!(jsonObject.getString("cost") == null))
            {
                detailIntent.putExtra("cardCost", jsonObject.getString("cost"));
            }
            if(!(jsonObject.getString("text") == null))
            {
                detailIntent.putExtra("cardText", jsonObject.getString("text"));
            }
            if(!(jsonObject.getJSONArray("subtypes").toString() == null))
            {
                detailIntent.putExtra("cardSubTypes", jsonObject.getJSONArray("subtypes").toString());
            }
            if(!(jsonObject.getString("power") == null))
            {
                detailIntent.putExtra("cardPower", jsonObject.getString("power"));
            }
            if(!(jsonObject.getString("toughness") == null))
            {
                detailIntent.putExtra("cardToughness", jsonObject.getString("toughness"));
            }
            if(!(jsonObject.getJSONArray("supertypes").toString() == null))
            {
                detailIntent.putExtra("cardSuperTypes", jsonObject.getJSONArray("supertypes").toString());
            }
            if(!(jsonObject.getString("loyalty") == null))
            {
                detailIntent.putExtra("cardLoyalty", jsonObject.getString("loyalty"));
            }
            if(!(jsonObject.getJSONArray("colors").toString() == null))
            {
                detailIntent.putExtra("cardColors", jsonObject.getJSONArray("colors").toString());
            }
        }
        catch (Exception e)
        {
            Log.d("Exception", e.toString());
        }


        detailIntent.putExtra("cardPicture", cardID);

        startActivity(detailIntent);
    }

    private void queryCards(final String searchString) {

        String urlString = "";
        try {
            urlString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }



        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);



        mDialog.show();

        client.get(QUERY_URL + urlString, new JsonHttpResponseHandler()
        {

            String name;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray)
            {
                fedInArray = jsonArray;
                mDialog.dismiss();
                try
                {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject currObject = jsonArray.getJSONObject(i);
                        name = currObject.getString("name");
                        JSONArray versions = currObject.getJSONArray("editions");
                        a++;
                        for (int j = 0; j < versions.length(); j++)
                        {
                            String EditionName = versions.getJSONObject(j).getString("multiverse_id");
                            Log.d("Edition Test", EditionName);
                        }
                        Log.d("Card Name Test", name);
                    }
                        Toast.makeText(getApplicationContext(), a + " cards found.", Toast.LENGTH_LONG).show();
                        a = 0;
                        mJSONAdapter.updateData(jsonArray);

                }
                catch (Exception e)
                {
                    Log.d("Exception", "Something went wrong, internet issues likely.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject error)
            {
                mDialog.dismiss();

                Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
            }
        );
    }

}
