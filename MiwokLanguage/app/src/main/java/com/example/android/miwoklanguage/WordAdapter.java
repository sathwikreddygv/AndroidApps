package com.example.android.miwoklanguage;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by srujana on 20/12/17.
 */

public class WordAdapter extends ArrayAdapter<Word>{


    public WordAdapter(Context context , ArrayList<Word> words){
        super(context,0,words);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.array_adapter, parent, false);
        }
         Word currentWord = getItem(position);

        TextView miwok = (TextView) listItemView.findViewById(R.id.miwok);
        miwok.setText(currentWord.getMiwok());

        TextView english = (TextView) listItemView.findViewById(R.id.english);
        english.setText(currentWord.getEnglish());

        return listItemView;

    }
}
