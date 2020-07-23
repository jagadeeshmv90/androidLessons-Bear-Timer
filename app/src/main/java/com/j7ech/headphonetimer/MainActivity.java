package com.j7ech.headphonetimer;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.number.Precision;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView timeDisplay;
    SeekBar timeSeekBar;
    Boolean counterActive = false;
    Button hitButton;
    CountDownTimer countDownTimer;
    ImageView bearImage;

    public void updateTimer(int seekTime){
        int minute = seekTime/60;
        int second = seekTime - (minute * 60);
        String secondString="";
        if(second<=9) {
            secondString= "0" + (String.valueOf(second));
        } else {
            secondString = String.valueOf(second);
        }
        timeDisplay = findViewById(R.id.timerDisplay);
        timeDisplay.setText(minute + ":" + secondString);
    }
    public void resetTimer() {
        hitButton = findViewById(R.id.hitMeButton);
        hitButton.setText("Hit Me!");
        timeSeekBar.setEnabled(true);
        timeSeekBar.setProgress(30);
        timeDisplay.setText("0:30");
        counterActive = false;
        countDownTimer.cancel();
        photoChanger();
    }

    public void photoChanger() {
        bearImage = (ImageView) findViewById(R.id.bearImage);
        CountDownTimer photoCountDown = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                bearImage.setImageResource(R.drawable.bear1);
            }

            @Override
            public void onFinish() {
                bearImage.setImageResource(R.drawable.bear);
            }
        }.start();
    }

    public void counterFunction(){
        if(counterActive){
            resetTimer();

        } else {
            counterActive = true;
            timeSeekBar.setEnabled(false);
            hitButton = findViewById(R.id.hitMeButton);
            hitButton.setText("STOP!");
            countDownTimer = new CountDownTimer(timeSeekBar.getProgress()*1000 + 100, 1000){

                @Override
                public void onTick(long l) {
                    updateTimer((int) l/1000);
                }

                @Override
                public void onFinish() {
                    Log.i("test", "Test");
                    MediaPlayer clinker = MediaPlayer.create(getApplicationContext(), R.raw.boom);
                    clinker.start();
                    resetTimer();
                }
            }.start();




        }
    }

    public void buttonHit(View view) {
        counterFunction();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSeekBar = findViewById(R.id.timeSeekBar);
        timeSeekBar.setMax(300);
        timeSeekBar.setProgress(30);
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}