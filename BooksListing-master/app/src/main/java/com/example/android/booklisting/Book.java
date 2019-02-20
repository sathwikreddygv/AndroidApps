package com.example.android.booklisting;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alicantipi on 09.12.17.
 */

public class Book implements Parcelable {

    public static final String LOG_TAG = Book.class.getSimpleName();
    String mAuthorString;



    String mBookName;
    String mDate;
    String mBookImageURL;
    ArrayList<String> mAuthorsList;
    JSONArray mAuthorJsonArray;




    public Book(String authorName, String bookName, String date, String bookImageURL){
        mDate=date;
        mBookName=bookName;
        mBookImageURL=bookImageURL;
        mAuthorString = authorName;
    }

    public String getAuthor() {
        return mAuthorString;
    }

    public String getBookName() {
        return mBookName;
    }

    public String getDate() {
        return mDate;
    }

    public String getBookImage() {
        return mBookImageURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookImageURL);
        dest.writeString(mBookName);
        dest.writeString(mDate);
        dest.writeString(mAuthorString);
    }
    public Book (Parcel parcel) {
        this.mAuthorString = parcel.readString();
        this.mBookImageURL = parcel.readString();
        this.mBookName = parcel.readString();
        this.mDate= parcel.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };



}