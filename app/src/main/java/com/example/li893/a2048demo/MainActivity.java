package com.example.li893.a2048demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends AppCompatActivity{
    private TextView tvScore;
    private GameView gameView;
    private Chronometer time;
    private Long recordTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        String isLoad = intent.getStringExtra("isLoad");
        gameView = findViewById(R.id.gameView);
        tvScore = findViewById(R.id.tvScore);
        time = findViewById(R.id.time);
        time.setTextColor(Color.GRAY);
        time.setBase(SystemClock.elapsedRealtime());
        time.setFormat("%s");
        if (isLoad.equals("true")) {
            gameView.setCube(load());
            gameView.setCard();
            tvScore.setText(gameView.getCube().getGrade() + "");
            gameView.step.removeAll(gameView.step);
            gameView.gradeStep.removeAll(gameView.gradeStep);
            gameView.gradeStep.add(gameView.getCube().getGrade());
            gameView.step.add(gameView.getCopyCube());
        }
            time.start();
            move();
            tvScore.setTextSize(30);

        Button stepBack = findViewById(R.id.stepback);
        stepBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.back();
                gameView.setCard();
                tvScore.setText(gameView.getCube().getGrade() + "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();

    }

    @SuppressLint("ClickableViewAccessibility")
    public void move() {
        gameView.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @SuppressLint("SetTextI18n")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;

                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        int a;
                        int b;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {

                            if (offsetX < -5) {
                                a = 0;
                                b = -1;

                            } else {
                                a = 0;
                                b = 1;
                            }
                        } else {
                            if (offsetY < -5) {
                                a = -1;
                                b = 0;
                            } else {
                                a = 1;
                                b = 0;
                            }
                        }
                        gameView.getCube().keepGoing(a, b);
                        if (gameView.getCube().getIsMove()) {
                            gameView.getCube().newOut();
                            tvScore.setText(gameView.getCube().getGrade() + "");
                            gameView.setCard();
                            gameView.step.add(gameView.getCopyCube());
                            gameView.gradeStep.add(gameView.getCube().getGrade());
                        }
                        gameView.getCube().check();
                        if (gameView.getCube().getIsOver()) {
                            //跳转到下一个acitivity
                        }

                        break;
                    default:
                        break;
                }

                return true;
            }
        });

    }

    public void save() {
        saveTime();
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = openFileOutput("cube", MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gameView.getCube());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Cube load() {
        loadTime();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput("cube");
            ois = new ObjectInputStream(fis);
            return (Cube) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void saveTime(){
        SharedPreferences myPreference=getSharedPreferences("time", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPreference.edit();
        time.stop();
        recordTime=SystemClock.elapsedRealtime();
        editor.putLong("time", time.getBase());
        editor.putLong("recordTime", recordTime);
        editor.commit();
    }

    public void loadTime(){
        SharedPreferences myPreference=getSharedPreferences("time", Context.MODE_PRIVATE);
        time.setBase(myPreference.getLong("time",0) + (SystemClock.elapsedRealtime() - myPreference.getLong("recordTime",0)));

    }
}


