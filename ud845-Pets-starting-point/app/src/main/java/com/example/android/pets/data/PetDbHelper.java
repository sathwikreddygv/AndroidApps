package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContract.PetEntry;

/**
 * Created by srujana on 4/1/18.
 */

public class PetDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "shelter.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_ENTRIES ="CREATE TABLE pets ("+ PetEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PetEntry.COLUMN_PET_NAME+" TEXT, "+PetEntry.COLUMN_PET_BREED+" TEXT, "+PetEntry.COLUMN_PET_GENDER+" INTEGER, "+PetEntry.COLUMN_PET_WEIGHT+" INTEGER);";
    public static final String SQL_DELETE_ENTRIES ="DROP IF TABLE EXISTS"+PetEntry.TABLE_NAME;

   public PetDbHelper (Context context){
       super(context,DATABASE_NAME,null,DATABASE_VERSION);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
     db.execSQL(SQL_DELETE_ENTRIES);
     onCreate(db);
    }
}
