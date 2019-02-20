package com.example.android.booklisting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alicantipi on 09.12.17.
 */

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();





public static List<Book> fetchBooksFromApi(String requestUrl){

    //Create the URL object
    URL url = createUrl(requestUrl);

    //Perform an HTTP request
    String jsonResponse=null;

    try {
        jsonResponse = makeHttpRequest(url);
    } catch (IOException e){
        Log.e(LOG_TAG, "Request failed "+ e);
    }

    //Get data from the response json
    List<Book> booksList = extractFromJSON(jsonResponse);

    // Return the list
    return booksList;
}

    /** This method called by doInBackground method
     * input:url
     * output: Bitmap image
     *
     **/
    public static Bitmap fetchBookImages(String requestUrl){
        URL bookURL= null;
        Bitmap bmp=null;
        HttpURLConnection httpURLConnection = null;


        try {
            bookURL = new URL(requestUrl);
            Log.i("BOOKURL ", "bookUrlMaker: " +bookURL.toString());
        } catch (MalformedURLException e){
            Log.e("Malformed URL IMAGE", " IMAGE URL MALFORMED" +e);
        }
        try {
            if (bookURL != null) {
                httpURLConnection = (HttpURLConnection) bookURL.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() == 200) {
                    bmp = BitmapFactory.decodeStream(bookURL.openConnection().getInputStream());

                } else{
                    Log.e(LOG_TAG, "Error Response code: " + httpURLConnection.getResponseCode());
                }

            }
        } catch (IOException e){
            Log.e("e", "error "+e);


        }finally {
            if(httpURLConnection!=null) {

                httpURLConnection.disconnect();
            }
        }
        return bmp;

    }



    /**Make a http request for a given URL and return a string response*/
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else{
                Log.e(LOG_TAG, "Error Response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, " Problems connecting to the URL" + e);
        } finally {
            if (httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }


        }
        return jsonResponse;

    }

    /** Read from input stream and return string*/
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if (inputStream!=null){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();

            while (line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }


    /**Read the URL to populate necessary BOOKs*/
    private static List<Book> extractFromJSON(String booksJSON) {

        List<Book> booksList = new ArrayList();

        // early return if empty
        if (TextUtils.isEmpty(booksJSON)) {
            return null;
        }

        try {
            JSONObject baseJSON = new JSONObject(booksJSON);
            JSONArray itemsArray = baseJSON.getJSONArray("items");

            // If check for the empty books array.
            if (itemsArray.length() > 0) {
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject tempBookItem = itemsArray.getJSONObject(i);
                    JSONArray tempAuthorsJsonArray=null;
                    String tempDate= "";
                    String tempImageURL ="";
                    JSONObject tempImageLinks = null;

                    // getting the data from json
                    JSONObject tempVolumeInfo = tempBookItem.getJSONObject("volumeInfo");
                    String tempBookName = tempVolumeInfo.getString("title");

                    //how to handle this better ?
                    if (tempVolumeInfo.has("authors")){
                        tempAuthorsJsonArray= tempVolumeInfo.getJSONArray("authors");
                    }
                    if (tempVolumeInfo.has("publishedDate")){
                         tempDate = tempVolumeInfo.getString("publishedDate");

                    }
                    if (tempVolumeInfo.has("imageLinks")){
                         tempImageLinks= tempVolumeInfo.getJSONObject("imageLinks");
                         if (tempImageLinks.has("smallThumbnail")){
                             tempImageURL = tempVolumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");

                         }
                    }


                    booksList.add(new Book(authorListReader(tempAuthorsJsonArray), tempBookName, tempDate, tempImageURL));

                }
                return booksList;
            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error with JSON" + e);
        }

        return null;
    }

    /**Create a URL from the string*/
    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, " Malformed URL Exception caught" + e);
        }
        return url;

    }
    public static String authorListReader(JSONArray authorsList) throws JSONException {

        JSONArray mTempJsonArray = authorsList;
        ArrayList<String> mTempArrayList = new ArrayList<>();
        String mTempString;
        StringBuilder builder = new StringBuilder();


        if (mTempJsonArray != null) {
            for (int j=0;j<mTempJsonArray.length();j++)
            {
                mTempArrayList.add(mTempJsonArray.get(j).toString());

            }

        }

        if (mTempArrayList != null && mTempArrayList.size()>0){
            for (int i = 0 ; i < mTempArrayList.size() ; i ++){
                builder.append(mTempArrayList.get(i));
                if (mTempArrayList.size() > 1 && mTempArrayList.size()!=i) {
                    builder.append(", ");
                }
            }
            mTempString = builder.toString();
        } else {
            mTempString ="Author Unknown";
        }
        return mTempString;
    }
}
