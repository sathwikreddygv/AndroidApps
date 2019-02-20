package com.example.android.musicplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   // private MediaPlayer media;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button)findViewById(R.id.play);
        Button b2 = (Button)findViewById(R.id.pause);
        final MediaPlayer media = MediaPlayer.create(this,R.raw.number_one);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"play",Toast.LENGTH_SHORT).show();
                 media.start();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"pause",Toast.LENGTH_SHORT).show();
                media.pause();
            }
        });

        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(),"I`M DONE",Toast.LENGTH_SHORT).show();
            }

        });

   }

    /*public void play(View view){
        media.start();
    }

    public void pause(View view){
        media.pause();
    }*/
}




