package com.example.root.braingames.Matchsticks;

import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.braingames.MainActivity;
import com.example.root.braingames.R;
import com.example.root.braingames.StateChange;
import com.example.root.braingames.Timer;

import java.util.HashSet;

public class MatchstickGame implements StateChange {
    public Timer timer;
    private MainActivity mainActivity;
    private Board board;
    private int squaresGoal, clicksAllowed, clicksDone;

    public MatchstickGame(final MainActivity mainActivity){
        this.mainActivity = mainActivity;
        mainActivity.setContentView(R.layout.matchstickgame);
        board = new Board(mainActivity, this);
        ((RelativeLayout) mainActivity.findViewById(R.id.board)).addView(board);

        generateProblem();
        TextView time = (TextView) mainActivity.findViewById(R.id.timer);
        timer = new Timer(time, mainActivity);
        timer.start();
        mainActivity.findViewById(R.id.playPause).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePlayPause(view);
            }
        });
        mainActivity.findViewById(R.id.restart).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                new MatchstickGame(mainActivity);
            }
        });
    }

    public void pauseActivity(){
        timer.pause();
    }

    public void resumeActivity(){
        timer.resume();
    }

    public void registerClick() {
        if (++clicksDone == clicksAllowed){
            if (board.hasExtraneous())
                Log.i("Win status", "Lose");
            if (board.getSquares() == squaresGoal)
                Log.i("win status", "Win!");
            new MatchstickGame(mainActivity);
        }
    }

    public void generateProblem(){
        clicksAllowed = (int)(Math.random() * 8) + 3;
        int[][] clickMap = null;
        while (true){
            clickMap = Board.makeDefaultBoard();
            HashSet<Integer> toRemove = new HashSet<>();
            while (toRemove.size() < clicksAllowed){
                int rand = 0;
                while (rand/7%2 == rand%7%2)
                    rand = (int)(Math.random() * 49);
                toRemove.add(rand);
            }
            for (int el : toRemove)
                clickMap[el/7][el%7] = 0;
            if (!Board.hasExtraneous(clickMap))
                break;
        }
        squaresGoal = Board.getSquares(clickMap);
        TextView stip = (TextView) mainActivity.findViewById(R.id.stip);
        String text = "Remove " + clicksAllowed + " matchsticks \n to form \n " + squaresGoal;
        if (clicksAllowed == 1)
            text = " matchstick \n to form \n " + squaresGoal;
        if (squaresGoal != 1)
            text += " squares";
        else
            text += " square";
        stip.setText(text);
    }

    public void togglePlayPause(View view){
        ImageButton playPause = (ImageButton) view;
        if (!timer.paused){
            playPause.setImageResource(R.drawable.play_button);
            timer.pause();
        }
        else {
            playPause.setImageResource(R.drawable.pause_button);
            timer.resume();
        }
    }
}