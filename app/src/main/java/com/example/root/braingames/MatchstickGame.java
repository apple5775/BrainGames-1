package com.example.root.braingames;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class MatchstickGame {
    private Timer timer;
    private MainActivity mainActivity;
    private ImageButton playPause;
    private boolean playing;

    public MatchstickGame(final MainActivity mainActivity){
        this.mainActivity = mainActivity;
        mainActivity.setContentView(R.layout.matchstickgame);
        playPause = (ImageButton) mainActivity.findViewById(R.id.playPause);
        playing = true;
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
                buttons[row][col].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!timer.paused)
                            view.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }

        //Add views to layout
        for (Button[] b : buttons)
            for (Button button : b)
                gridLayout.addView(button, button.getParams((1/7.0), (2/21.0)));

        startTimer();
        playPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePlayPause();
            }
        });
        mainActivity.findViewById(R.id.restart).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                new MatchstickGame(mainActivity);
            }
        });
    }

    public void startTimer() {
        TextView time = (TextView) mainActivity.findViewById(R.id.timer);
        timer = new Timer(time, mainActivity);
        timer.start();
    }

    public void togglePlayPause(){
        if (playing){
            playPause.setImageResource(R.drawable.play_button);
            timer.pause();
            playing = false;
        }
        else {
            playPause.setImageResource(R.drawable.pause_button);
            timer.resume();
            playing = true;
        }
    }
}