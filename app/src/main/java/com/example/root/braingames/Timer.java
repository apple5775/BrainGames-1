package com.example.root.braingames;

import android.util.Log;
import android.app.Activity;
import android.widget.TextView;
import java.util.TimerTask;

public class Timer {
    private int time;
    private java.util.Timer timer;
    private TimerTask task, backupTask;
    private TextView textView;
    private Activity mainActivity;
    public boolean paused;

    public Timer(TextView text, Activity main){
        textView = text;
        mainActivity = main;
        time = 0;
        timer = new java.util.Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        int hours = time / 3600;
                        time %= 3600;
                        int minutes = time / 60;
                        time %= 60;
                        int seconds = time;
                        textView.setText(format(hours) + " : " + format(minutes) + " : " + format(seconds));
                    }
                });
            }
        };
    }

    public void start(){
        paused = false;
        timer.schedule(task, 0, 1000);
    }

    public void reset(){
        timer.cancel();
        timer = new java.util.Timer();
        time = 0;
    }

    public void pause(){
        paused = true;
        timer.cancel();
    }

    public void resume() {
        if (paused){
            timer = new java.util.Timer();
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            time++;
                            int hours = time / 3600;
                            time %= 3600;
                            int minutes = time / 60;
                            time %= 60;
                            int seconds = time;
                            textView.setText(format(hours) + " : " + format(minutes) + " : " + format(seconds));
                        }
                    });
                }
            };
            timer.schedule(t, 0, 1000);
            paused = false;
        }
    }

    public static String format(int n){
        String time = Integer.toString(n);
        if (time.length() == 1)
            time = "0" + time;
        if (time.length() == 0)
            time = "00";
        return time;
    }
}
