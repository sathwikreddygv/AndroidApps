package com.example.android.iitharwain;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static String CABSHARING_URL = "https://docs.google.com/spreadsheets/d/1nfAFky-_XdYslw-h0x-g9XnLSednXQFcWQVXbbKDnVA/edit#gid=0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView myAccount = (TextView) findViewById(R.id.my_account);
        TextView eventCalender = (TextView) findViewById(R.id.event_calender);
        TextView instituteCourses = (TextView) findViewById(R.id.institute_courses);
        TextView lostAndFound = (TextView) findViewById(R.id.lost_and_found);
        TextView cabSharing = (TextView) findViewById(R.id.cab_sharing);
        TextView busSchedule = (TextView) findViewById(R.id.bus_schedule);
        TextView messMenu = (TextView) findViewById(R.id.mess_menu);
        TextView logOut = (TextView) findViewById(R.id.logout);

        messMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),MessMenuActivity.class));
            }
        });

        lostAndFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),LostFoundActivity.class));
            }
        });

        cabSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri cabsharingUri = Uri.parse(CABSHARING_URL);
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, cabsharingUri);
                startActivity(websiteIntent);
            }
        });

        instituteCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),CoursesActivity.class));
            }
        });


    }
}
