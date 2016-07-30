package com.example.haritha.myapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.example.haritha.myapp.R.id.decrementbutton;

public class MainActivity extends Activity {
    private ProgressBar progressBar;
    private Button playButton;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private TextView songtime;
    private long songStartTime;
    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListeners();
    }

    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            double seekPercentage = 100 * mediaPlayer.getCurrentPosition()
                                          /mediaPlayer.getDuration();
            progressBar.setProgress((int) seekPercentage);
            long seconds = (System.currentTimeMillis() - songStartTime) / 1000;
            songtime.setText(String.format("%02d:%02d", seconds/ 60, seconds %60));
            handler.postDelayed(this,1000);

        }
    };

    private  void initHandler() {
        handler.postDelayed(updateUI,1000);
    }

    private void initListeners() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    playButton.setText("PLAY");
                } else {
                    initHandler();
                    mediaPlayer.start();
                    songStartTime =  System.currentTimeMillis();
                    playButton.setText("STOP");
                }
            }
        });
    }


//Linking with xml files usnig id
    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        playButton = (Button) findViewById(R.id.playbutton);
        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        songtime = (TextView) findViewById(R.id.songtime);
        imageview = (ImageView) findViewById(R.id.imageview);
        songtime.setText(String.format("%02d:%02d",0,0));
        imageview.setImageDrawable(getResources().getDrawable(R.drawable.im1));
    }
}
