package com.example.root.braingames;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MatchstickGame {
    //time represents the elapsed time since first click in seconds
    private int time = 0;
    private MainActivity mainActivity;
    private boolean firstClick = true;

    public MatchstickGame(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        mainActivity.setContentView(R.layout.matchstickgame);
        GridLayout gridLayout = (GridLayout) mainActivity.findViewById(R.id.board);

        Button[][] buttons = new Button[7][7];
        gridLayout.removeAllViews();
        for(int row = 0; row < 7; row += 1) {
            for (int col = 0; col < 7; col += 1) {
                Button h = new Button(mainActivity, row, col);
                if (row % 2 == 0 && col % 2 == 1)
                    h.setImageResource(R.drawable.matchstick_horizontal);
                else if (row % 2 == 1 && col % 2 == 0)
                    h.setImageResource(R.drawable.matchstick_vertical);
                buttons[row][col] = h;
                buttons[row][col].setOnClickListener(view -> {
                    if (firstClick) {
                        firstClick = false;
                        startTimer();
                    }
                    view.setVisibility(View.INVISIBLE);
                });
            }
        }

        //Add views to layout
        for (Button[] b : buttons)
            for (Button button : b)
                gridLayout.addView(button, button.getParams((1/7.0), (2/21.0)));
    }

    public void startTimer(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mainActivity.runOnUiThread(() -> {
                    time++;
                    TextView timeDisp = (TextView) mainActivity.findViewById(R.id.timer);
                    int hours = time / 3600;
                    time %= 3600;
                    int minutes = time / 60;
                    time %= 60;
                    int seconds = time;
                    timeDisp.setText(makeTime(hours, minutes, seconds));
                });
            }
        };

        timer.schedule(task, 0, 1000);
    }

    public String makeTime(int hours, int minutes, int seconds){
        return format(hours) + " : " + format(minutes) + " : " + format(seconds);
    }

    public String format(int n){
        String number = Integer.toString(n);
        if (number.length() == 1)
            number = "0" + number;
        if (number.length() == 0)
            number = "00";
        return number;
    }
}