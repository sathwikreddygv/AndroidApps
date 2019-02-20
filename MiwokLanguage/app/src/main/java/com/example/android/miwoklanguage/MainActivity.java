package com.example.android.miwoklanguage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static android.support.v4.content.ContextCompat.startActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView numbers = (TextView) findViewById(R.id.numbers);
        TextView phrases = (TextView) findViewById(R.id.phrases);
        TextView family = (TextView) findViewById(R.id.family);
        TextView colors = (TextView) findViewById(R.id.colors);

        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext() ,NumbersActivity.class));
            }
        });

        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext() ,PhrasesActivity.class));
            }
        });

        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext() ,FamilyActivity.class));
            }
        });

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext() ,ColorsActivity.class));
            }
        });


    }

    public void viewPhrasesList(View view){
       Intent i = new Intent (this,PhrasesActivity.class);
       startActivity(i);
    }

    public void viewFamilyList(View view){
        Intent i = new Intent (this,FamilyActivity.class);
        startActivity(i);
    }

    public void viewColorsList(View view){
        Intent i = new Intent (this,ColorsActivity.class);
        startActivity(i);
    }
}
