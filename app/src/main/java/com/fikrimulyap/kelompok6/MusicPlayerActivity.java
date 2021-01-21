package com.fikrimulyap.kelompok6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {

    Bundle songExtraData; // in order to get the values from previous activity
    ImageView prev,play, next, back, shuffle, repeat, favorite;
    int position;
    SeekBar mSeekBarTime;
    static MediaPlayer mMediaPlayer; // if not static then two or more than two songs will be played at the same time
    TextView songName;
    ArrayList<File> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top);

        // casting views
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        prev = findViewById(R.id.btnPrev);
        play = findViewById(R.id.btnPlay);
        next = findViewById(R.id.btnNext);
        mSeekBarTime = findViewById(R.id.seekBarTime);
        songName  = findViewById(R.id.tvTitle);
        back  = findViewById(R.id.btnback);
        shuffle  = findViewById(R.id.btnShuffle);
        repeat  = findViewById(R.id.btnRepeat);
        favorite  = findViewById(R.id.btnFavorite);
        // when the activity launches mediaplayer should be on stop

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // TODO Auto-generated method stub
//                Intent moveWithDataIntent = new Intent(MusicPlayerActivity.this, MainActivity.class);
//                moveWithDataIntent.putExtra(MusicPlayerActivity.musicPlayer);
//                startActivity(moveWithDataIntent);

                startActivity(new Intent(MusicPlayerActivity.this, MainActivity.class));
//                overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);

                finish();

                //menggunakan intent untuk berpindah ke activity sebelumnya
            }
        });

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "shuffle", duration);
                toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 400);
                toast.show();
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "repeat", duration);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 400);
                toast.show();
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "favorite", duration);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 400);
                toast.show();
            }
        });

        // getting values from previous activity
        Intent intent = getIntent();
        songExtraData = intent.getExtras();

        musicList = (ArrayList)songExtraData.getParcelableArrayList("songsList");
        position = songExtraData.getInt("position", 0);

        // creating a new method that initializes the media player on launch of activity

        initializeMusicPlayer(position);

        // play button

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position < musicList.size() -1) {
                    position++;
                } else {
                    position = 0;
                }
                initializeMusicPlayer(position);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position <= 0) {
                    position = musicList.size() -1;
                } else {
                    position--;
                }

                initializeMusicPlayer(position);
            }
        });


    }

    private void initializeMusicPlayer(int position) {

        // if mediaplayer is not null and playing reset it at the launch of activity

        if (mMediaPlayer!=null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.reset();
        }

        // getting out the song name
        String name = musicList.get(position).getName();
        songName.setText(name);

        // accessing the songs on storage

        Uri uri = Uri.parse(musicList.get(position).toString());

        // creating a mediaplayer
        // passing the uri

        mMediaPlayer = MediaPlayer.create(this, uri);

        // SETTING ON PREPARED MEDIAPLAYER

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                // seekbar
                mSeekBarTime.setMax(mMediaPlayer.getDuration());

                // while mediaplayer is playing the play button should display pause
                play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24_white);
                // start the mediaplayer
                mMediaPlayer.start();
            }
        });

        // setting the oncompletion listener
        // after song finishes what should happen // for now we will just set the pause button to play

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24_white);
            }
        });


        // if you want the the mediaplayer to go to next song after its finished playing one song its optional
        /*mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.play);

                int currentPosition = position;
                if (currentPosition < musicList.size() -1) {
                    currentPosition++;
                } else {
                    currentPosition = 0;
                }
                initializeMusicPlayer(currentPosition);

            }
        });*/


        // working on seekbar

        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // IF USER TOUCHES AND MESSES WITH SEEEKBAR
                if (fromUser) {
                    mSeekBarTime.setProgress(progress);
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // till here seekbar wont change on its own

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mMediaPlayer!=null) {
                    try {
                        if (mMediaPlayer.isPlaying()) {
                            Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);

                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mSeekBarTime.setProgress(msg.what);
        }
    };

    // lastly create a method for play

    private void play() {
        // if mediaplayer is not null and playing and if play button is pressed pause it

        if (mMediaPlayer!=null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            // change the image of playpause button to play when we pause it
            play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24_white);
        } else {
            mMediaPlayer.start();
            // if mediaplayer is playing // the image of play button should display pause
            play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24_white);

        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 2)
        {
            //do the things u wanted
        }
    }
}