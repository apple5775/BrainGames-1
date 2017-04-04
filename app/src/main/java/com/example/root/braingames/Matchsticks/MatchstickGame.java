package com.example.root.braingames.Matchsticks;

import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.braingames.R;
import com.example.root.braingames.Timer;
import com.example.root.braingames.GameOver;

import java.util.HashSet;


public class MatchstickGame extends Activity {
    public Timer timer;
    private int[][] winningMap;
    private Board board;
    private int squaresGoal, clicksAllowed, clicksDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchstickgame);

        clicksDone = 0;
        board = new Board(this);
        ((RelativeLayout) findViewById(R.id.board)).addView(board);

        generateProblem();
        TextView time = (TextView) findViewById(R.id.timer);
        timer = new Timer(time, this);
        timer.start();
        findViewById(R.id.playPause).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePlayPause(view);
            }
        });
        findViewById(R.id.restart).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                 restart();
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        timer.pause();
    }

    @Override
    public void onResume(){
        super.onResume();
        timer.resume();
    }

    public void restart(){
        Intent restarter = new Intent(this, MatchstickGame.class);
        restarter.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(restarter);
        finish();
    }

    public void registerClick() {
        Log.i("MS Play", ""+clicksDone);
        Log.i("MS Play", ""+clicksAllowed);
        if (++clicksDone == clicksAllowed){
            Intent newGameIntent = new Intent(this, GameOver.class);
            newGameIntent.putExtra("FROM_CLASS", "com.example.root.braingames.Matchsticks.MatchstickGame");
            if (board.getSquares() == squaresGoal) {
                if (board.hasExtraneous()) {
                    Log.i("win status", "Lose! (Extraneous)");
                    newGameIntent.putExtra("GAME_STATUS", "extraneous.");
                    newGameIntent.putExtra("WINNING_MAP", Board.arrayToString(winningMap));
                    startActivity(newGameIntent);
                } else {
                    Log.i("win status", "Win! (non-Extraneous)");
                    newGameIntent.putExtra("GAME_STATUS", "gameWon");
                    startActivity(newGameIntent);
                }

            } else {
                Log.i("win status", "Lose");
                newGameIntent.putExtra("GAME_STATUS", "gameLost");
                newGameIntent.putExtra("WINNING_MAP", Board.arrayToString(winningMap));
                startActivity(newGameIntent);
            }
            finish();
        }
    }

    public void generateProblem(){
        clicksAllowed = (int)(Math.random() * 5) + 3;
        int[][] clickMap = Board.makeDefaultBoard();
        boolean tryAgain = true;
        while (tryAgain){
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
            tryAgain = Board.hasExtraneous(clickMap) || Board.getSquares(clickMap) == 0;
        }
        winningMap = clickMap;
        squaresGoal = Board.getSquares(clickMap);
        TextView stip = (TextView) findViewById(R.id.stip);

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