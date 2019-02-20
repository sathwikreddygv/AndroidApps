package com.example.android.miwoklanguage;

/**
 * Created by srujana on 20/12/17.
 */

public class Word {
    String miwok;
    String english;
    public Word(String miwok,String english){
        this.miwok = miwok;
        this.english = english;
    }

    public String getMiwok(){
        return miwok;

    }

    public String getEnglish() {
        return english;
    }
}
