package com.example.haritha.myapp;

import android.app.Activity;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.renderscript.Double3;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;



public class MainActivity extends Activity {
    //
    // private ProgressBar progressBar;
    //private Button playButton;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private TextView songtime;
    private long songStartTime;
    private ImageView imageview;
    private ImageButton playbuttonoriginal;
    private ImageButton pausebutton;
    private ImageButton forward;
    private ImageButton rewind;
    private ImageView nowplay;
    private ImageView namebar;
    private SeekBar seekBar;
    private double finaltime;
    private double startTime;
    private Handler myHandler = new Handler();

    public static int oneTimeOnly = 0;
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
            //progressBar.setProgress((int) seekPercentage);
            long seconds = (System.currentTimeMillis() - songStartTime) / 1000;
            songtime.setText(String.format("%02d:%02d", seconds/ 60, seconds %60));
            handler.postDelayed(this,1000);

        }
    };

    private  void initHandler() {
        handler.postDelayed(updateUI,1000);
    }

    private void initListeners() {
        playbuttonoriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updateUI);
                    mediaPlayer.pause();
                    playbuttonoriginal.setImageResource(R.drawable.play_button);


                }
                else {
                    initHandler();
                    mediaPlayer.start();
                    songStartTime = System.currentTimeMillis();
                    playbuttonoriginal.setImageResource(R.drawable.pause_button);

                    finaltime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekBar.setMax((int) finaltime);
                        oneTimeOnly = 1;
                    }

                    songtime.setText(String.format("%d , %d",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                    );

                    seekBar.setProgress((int)startTime);
                    myHandler.postDelayed(UpdateSongTime, 100);


                }
            }
        });
        
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            songtime.setText(String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };



//Linking with xml files usnig id
    private void initView() {
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //playButton = (Button) findViewById(R.id.playbutton);
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        songtime = (TextView) findViewById(R.id.songtime);
        imageview = (ImageView) findViewById(R.id.album_art);
        songtime.setText(String.format("%02d:%02d",0,0));
        imageview.setImageDrawable(getResources().getDrawable(R.drawable.album_art));

        playbuttonoriginal = (ImageButton) findViewById(R.id.playbuttonoriginal);
        playbuttonoriginal.setImageDrawable((getResources().getDrawable(R.drawable.play_button)));

        //pausebutton = (ImageButton) findViewById(R.id.pausebutton);
        //pausebutton.setImageDrawable((getResources().getDrawable(R.drawable.pause_button)));

        nowplay = (ImageView) findViewById(R.id.nowplay);
        nowplay.setImageDrawable(getResources().getDrawable(R.drawable.now_playing));

        namebar = (ImageView) findViewById(R.id.namebar);
        namebar.setImageDrawable(getResources().getDrawable(R.drawable.song_title_place_holder));

        forward = (ImageButton) findViewById(R.id.forward);
        forward.setImageDrawable(getResources().getDrawable(R.drawable.fast_forward_button));

        rewind = (ImageButton) findViewById(R.id.rewind);
        rewind.setImageDrawable(getResources().getDrawable(R.drawable.rewind_button));

        seekBar = (SeekBar) findViewById(R.id.seekBar);

    }
}
