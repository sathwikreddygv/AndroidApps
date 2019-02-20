package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by alicantipi on 09.12.17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    /**Tag for Logging*/
    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public BookLoader(Context context,String url){
        super(context);
        mUrl=url;
    }

    @Override
    public  List<Book> loadInBackground() {

        if (mUrl==null){
            return null;
        }
        return QueryUtils.fetchBooksFromApi(mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


}
