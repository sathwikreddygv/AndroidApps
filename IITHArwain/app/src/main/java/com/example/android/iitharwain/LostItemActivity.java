package com.example.android.iitharwain;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class LostItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_item);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LostItemFragment())
                .commit();
    }
}
