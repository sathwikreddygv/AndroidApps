package com.example.android.iitharwain;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by srujana on 19/1/18.
 */

public class MessMenuActivity extends AppCompatActivity {

    private static final String MESSMENU_URL ="https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1IW3-KInk1-RQsCGgkYcmQ7SBBiCqiayEWehkAYpatDM&sheet=Sheet1";
    private static final String LOG_TAG = MessMenuActivity.class.getSimpleName();

    private Button mSearch;
    private TextView mMenuData;
    private Spinner mDaySpinner;
    private Spinner mSessionSpinner;
    private static String mDay;
    private static String mSession;
    private static int i;


     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messmenu);

         mSearch = (Button) findViewById(R.id.search_button);
         mMenuData = (TextView) findViewById(R.id.menu_data);

         mDaySpinner = (Spinner)findViewById(R.id.spinner_day);
         mSessionSpinner = (Spinner)findViewById(R.id.spinner_session);

         mMenuData.setVisibility(View.GONE);
         setupSpinner1();
         setupSpinner2();

         mSearch.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MessMenuAsyncTask menu = new MessMenuAsyncTask();
                 menu.execute(MESSMENU_URL);

             }
         });
    }

    private String fetchdata(String requestUrl) {
        Log.i(LOG_TAG,"fetchMessMenuData");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        String data = extractFeatureFromJson(jsonResponse);

    return data;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the messmenu JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static String extractFeatureFromJson(String messmenuJSON) {

        if (TextUtils.isEmpty(messmenuJSON)) {
            return null;
        }

        String menu="flop";

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(messmenuJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray menuArray = baseJsonResponse.getJSONArray("Sheet1");

            if ( mDay == "Sunday" ){ i=0;}
            else if(mDay == "Monday"){i=1;}
            else if(mDay == "Tuesday"){i=2;}
            else if(mDay == "Wednesday"){i=3;}
            else if(mDay == "Thursday"){i=4;}
            else if(mDay == "Friday"){i=5;}
            else if(mDay == "Saturday"){i=6;}

            JSONObject currentMenu = menuArray.getJSONObject(i);
            menu = currentMenu.getString(mSession);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the messmenu JSON results", e);
        }
        return menu;
    }

    private void displaydata(String data) {
         mMenuData.setVisibility(View.VISIBLE);
         data = mDay + "     " + mSession + "\n" + data;
         mMenuData.setText(data);
    }

    private void setupSpinner1() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_day_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDaySpinner.setAdapter(daySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Sunday")) {
                        mDay = "Sunday";
                    } else if (selection.equals("Monday")) {
                        mDay = "Monday";
                    } else if(selection.equals("Tuesday")) {
                        mDay = "Tuesday";
                    }else if(selection.equals("Wednesday")){
                        mDay = "Wednesday";
                    }else if(selection.equals("Thursday")){
                        mDay = "Thursday";
                    }else if(selection.equals("Friday")){
                        mDay = "Friday";
                    }else {
                        mDay = "Saturday";
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDay = "Sunday";
            }
        });
    }
    private void setupSpinner2() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter sessionSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_session_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        sessionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSessionSpinner.setAdapter(sessionSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mSessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Breakfast")) {
                        mSession = "Breakfast";
                    } else if (selection.equals("Lunch")) {
                        mSession = "Lunch";
                    } else if (selection.equals("Snacks")) {
                        mSession = "Snacks";
                    } else {
                        mSession = "Dinner";
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSession = "Breakfast";
            }
        });
    }

    private class  MessMenuAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            displaydata(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String data = fetchdata(MESSMENU_URL);
            return data;
        }
    }
}

