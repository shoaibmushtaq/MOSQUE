package com.example.mymosque;

import android.app.ProgressDialog;
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

    private String FileName, FilePath, Type, Description, Time;
    private ImageView Image_Notify, Play, Pause, Stop;
    private TextView Time_Notify, Description_Notify;
    private FullscreenVideoView VideoView;
    private SeekBar AudioView;
    private int SeekValue;
    private RelativeLayout LayoutAudioButtons;
    private MediaPlayer AudioLink;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail_screen);

        getSupportActionBar().hide();

        AudioLink = new MediaPlayer();

        ImageView Back_BTN = findViewById(R.id.Back_BTN);
        Back_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iinent = new Intent(NotificationDetailScreen.this, MainActivity.class);
                startActivity(iinent);
                finish();

            }


        });

        InitViews();
        VideoView.setVisibility(View.GONE);
        Image_Notify.setVisibility(View.GONE);
        AudioView.setVisibility(View.GONE);
        LayoutAudioButtons.setVisibility(View.GONE);

        SharedPreferences Notification = getSharedPreferences("NotificationFragment", MODE_PRIVATE);
        FileName = Notification.getString("M_Filename", null);
        FilePath = Notification.getString("M_Filepath", null);
        Type = Notification.getString("M_Type", null);
        Description = Notification.getString("M_Description_", null);
        Time = Notification.getString("M_Time", null);

        if (Type.equals("I")) {

            Image_Notify.setVisibility(View.VISIBLE);
            path = "http://masjidi.co.uk/panel/userpanel/uploads/advertsments/" + FileName;

            Picasso.get().load(path).into(Image_Notify);

            Description_Notify.setText(Description);
            Time_Notify.setText(Time);

        } else if (Type.equals("V")) {

            VideoView.setVisibility(View.VISIBLE);
            path = "http://masjidi.co.uk/panel/userpanel/uploads/advertsments/" + FileName;
            VideoView.videoUrl(path).enableAutoStart().fastForwardSeconds(5).rewindSeconds(5);
            Description_Notify.setText(Description);
            Time_Notify.setText(Time);

        } else if (Type.equals("T")) {

            Description_Notify.setText(Description);
            Time_Notify.setText(Time);

        } else if (Type.equals("A")) {
            path = "http://masjidi.co.uk/panel/userpanel/uploads/advertsments/" + FileName;
            AudioView.setVisibility(View.VISIBLE);
            LayoutAudioButtons.setVisibility(View.VISIBLE);
            Description_Notify.setText(Description);
            Time_Notify.setText(Time);
            SeekThread seekThread = new SeekThread();
            seekThread.start();
        }

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PlayAudio();

            }
        });

        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PauseAudio();
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopAudio();
            }
        });

        AudioView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                SeekValue=progress;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                AudioLink.seekTo(SeekValue);
            }
        });



    }


    public  void  InitViews(){

        Image_Notify=findViewById(R.id.Image_Des);
        Time_Notify=findViewById(R.id.Date_Text);
        Description_Notify=findViewById(R.id.Text_Description_D);
        VideoView=findViewById(R.id.fullscreenVideoView);
        AudioView=findViewById(R.id.AudioView_);
        LayoutAudioButtons=findViewById(R.id.AudioLayout);
        Play=findViewById(R.id.Audio_Play);
        Pause=findViewById(R.id.Audio_Pause);
        Stop=findViewById(R.id.Audio_Stop);


    }

    public  void PlayAudio(){
        try {
            AudioLink.setDataSource(path);
            AudioLink.prepare();
            AudioLink.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  void PauseAudio(){
        if(AudioLink.isPlaying()){
            AudioLink.pause();
        }


    }
    public  void StopAudio(){

        if(AudioLink.isPlaying()){
            AudioLink.release();
            AudioLink=null;
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
                        if (AudioLink != null) {
                            AudioView.setProgress(AudioLink.getDuration());


                        }
                    }
                });


            }

        }

    }

}
