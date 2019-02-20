package com.example.android.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scorea=0;
    int scoreb=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    public void threepointa(View view){
        scorea=scorea+3;
        displayForTeamA(scorea);
    }

    public void twopointa(View view){
        scorea=scorea+2;
        displayForTeamA(scorea);
    }

    public void onepointa(View view){
        scorea=scorea+1;
        displayForTeamA(scorea);
    }


    public void threepointb(View view){
        scoreb=scoreb+3;
        displayForTeamB(scoreb);
    }

    public void twopointb(View view){
        scoreb=scoreb+2;
        displayForTeamB(scoreb);
    }

    public void onepointb(View view){
        scoreb=scoreb+1;
        displayForTeamB(scoreb);
    }

    public void reset(View view){
        scorea=0;
        displayForTeamA(scorea);
        scoreb=0;
        displayForTeamB(scoreb);
    }
}
