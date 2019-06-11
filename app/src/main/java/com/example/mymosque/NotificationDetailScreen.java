package com.example.mymosque;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;

public class NotificationDetailScreen extends AppCompatActivity {

    //declaring varriables
    private String fileName, filePath, type, description, time;
    private ImageView imageNotify, play, pause, stop;
    private TextView timeNotify, descriptionNotify;
    private FullscreenVideoView videoView;
    private SeekBar audioView;
    private int seekValue;
    private RelativeLayout layoutAudioButtons;
    private MediaPlayer audioLink;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail_screen);

        //hiding action bar
        getSupportActionBar().hide();



        //starting media player
        audioLink = new MediaPlayer();

        ImageView Back_BTN = findViewById(R.id.Back_BTN);
        Back_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iinent = new Intent(NotificationDetailScreen.this, MainActivity.class);
                startActivity(iinent);
                finish();

            }


        });

        initViews();


        videoView.setVisibility(View.GONE);
        imageNotify.setVisibility(View.GONE);
        audioView.setVisibility(View.GONE);
        layoutAudioButtons.setVisibility(View.GONE);


        //getting notifications from shared preferences
        SharedPreferences Notification = getSharedPreferences("NotificationFragment", MODE_PRIVATE);
        fileName = Notification.getString("M_Filename", null);
        filePath = Notification.getString("M_Filepath", null);
        type = Notification.getString("M_Type", null);
        description = Notification.getString("M_Description_", null);
        time = Notification.getString("M_Time", null);

        if (type.equals("I")) {

            imageNotify.setVisibility(View.VISIBLE);
            path = "http://masjidi.co.uk/panel/userpanel/uploads/advertsments/" + fileName;

            Picasso.get().load(path).into(imageNotify);

            descriptionNotify.setText(description);
            timeNotify.setText(time);

        }


        else if (type.equals("V")) {

            videoView.setVisibility(View.VISIBLE);
            path = "http://masjidi.co.uk/panel/userpanel/uploads/advertsments/" + fileName;
            videoView.videoUrl(path).enableAutoStart().fastForwardSeconds(5).rewindSeconds(5);
            descriptionNotify.setText(description);
            timeNotify.setText(time);

        }

        else if (type.equals("T")) {

            descriptionNotify.setText(description);
            timeNotify.setText(time);

        }

        else if (type.equals("A")) {
            path = "http://masjidi.co.uk/panel/userpanel/uploads/advertsments/" + fileName;
            audioView.setVisibility(View.VISIBLE);
            layoutAudioButtons.setVisibility(View.VISIBLE);
            descriptionNotify.setText(description);
            timeNotify.setText(time);
            SeekThread seekThread = new SeekThread();
            seekThread.start();
        }


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PlayAudio();

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PauseAudio();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopAudio();
            }
        });

        audioView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                seekValue =progress;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                audioLink.seekTo(seekValue);
            }
        });



    }


    public  void initViews(){

        imageNotify =findViewById(R.id.Image_Des);
        timeNotify =findViewById(R.id.Date_Text);
        descriptionNotify =findViewById(R.id.Text_Description_D);
        videoView =findViewById(R.id.fullscreenVideoView);
        audioView =findViewById(R.id.AudioView_);
        layoutAudioButtons =findViewById(R.id.AudioLayout);
        play =findViewById(R.id.Audio_Play);
        pause =findViewById(R.id.Audio_Pause);
        stop =findViewById(R.id.Audio_Stop);


    }
    public  void PlayAudio(){
        try {
            audioLink.setDataSource(path);
            audioLink.prepare();
            audioLink.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  void PauseAudio(){
        if(audioLink.isPlaying()){
            audioLink.pause();
        }


    }
    public  void StopAudio(){

        if(audioLink.isPlaying()){
            audioLink.release();
            audioLink =null;
        }


    }


    class SeekThread extends Thread {

        public void run() {
            while (true) {
                try {


                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (audioLink != null) {
                            audioView.setProgress(audioLink.getDuration());


                        }
                    }
                });


            }

        }

    }

}
