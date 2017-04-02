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

//import com.example.root.braingames.MainActivity;
import com.example.root.braingames.R;
import com.example.root.braingames.StateChange;
import com.example.root.braingames.Timer;
import com.example.root.braingames.GameOver;

import java.util.HashSet;


public class MatchstickGame extends Activity implements StateChange {
    public Timer timer;
    //private Activity mainActivity;
    private Activity curActivity;
    private Board board;
    private int squaresGoal, clicksAllowed, clicksDone;
    private int[][] winMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.onCreate(null);
        setContentView(R.layout.matchstickgame);

        Intent serveIntent = getIntent();
        curActivity = this; //needs the empty default constructor to get "this"
        clicksDone = 0;
        board = new Board(curActivity, this);
        ((RelativeLayout) findViewById(R.id.board)).addView(board);

        generateProblem();
        TextView time = (TextView) findViewById(R.id.timer);
        timer = new Timer(time, curActivity);
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
                 //new MatchstickGame(curActivity);
                restart();
            }
        });
    }

    //This empty default constructor is important to make the intent work.
    public MatchstickGame(){}

    //This is the original constructor. We do not call it at the moment.
    //public MatchstickGame(final MainActivity mainActivity){
    public MatchstickGame(Activity mainActivity) {
        curActivity = mainActivity;
        curActivity.setContentView(R.layout.matchstickgame);
        board = new Board(curActivity, this);
        ((RelativeLayout) curActivity.findViewById(R.id.board)).addView(board);
        clicksDone = 0;
        generateProblem();
        TextView time = (TextView) curActivity.findViewById(R.id.timer);
        timer = new Timer(time, curActivity);
        timer.start();
        curActivity.findViewById(R.id.playPause).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePlayPause(view);
            }
        });
        curActivity.findViewById(R.id.restart).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                new MatchstickGame(curActivity);
            }
        });
    }

    private void restart() {
        setContentView(R.layout.matchstickgame);
        board = new Board(curActivity, this);
        ((RelativeLayout) curActivity.findViewById(R.id.board)).addView(board);
        clicksDone = 0;
        generateProblem();
        TextView time = (TextView) curActivity.findViewById(R.id.timer);
        timer = new Timer(time, curActivity);
        timer.start();
        curActivity.findViewById(R.id.playPause).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePlayPause(view);
            }
        });
        curActivity.findViewById(R.id.restart).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                restart();
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
        Log.i("MS Play", ""+clicksDone);
        Log.i("MS Play", ""+clicksAllowed);
        if (++clicksDone == clicksAllowed){
            Intent newGameIntent = new Intent (curActivity, GameOver.class);
            if (board.getSquares() == squaresGoal) {
                if (board.hasExtraneous()) {
                    Log.i("win status", "Lose! (Extraneous)");
                    newGameIntent.putExtra("GAME_STATUS", "extraneous.");
                    //newGameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //need to uncomment later
                    startActivity(newGameIntent);
                    //finish();  //just to debug
                } else {
                    Log.i("win status", "Win! (non-Extraneous)");
                    newGameIntent.putExtra("GAME_STATUS", "gameWon");
                    //newGameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//uncomment
                    startActivity(newGameIntent);
                    //finish(); //debug
                }

            } else {
                Log.i("win status", "Lose");
                newGameIntent.putExtra("GAME_STATUS", "gameLost");
                //newGameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newGameIntent);
                //finish();
            }


            /*if (board.hasExtraneous()) {  // not working
                Log.i("Win status", "Extraneous");
                newGameIntent.putExtra("GAME_STATUS", "gameLost");
                newGameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newGameIntent);
                finish();
            } else {
                if (board.getSquares() == squaresGoal) {
                    Log.i("win status", "Win!");
                    newGameIntent.putExtra("GAME_STATUS", "gameWon");
                    newGameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(newGameIntent);
                    finish();
                } else {
                    Log.i("win status", "Lose");
                    newGameIntent.putExtra("GAME_STATUS", "gameLost");
                    newGameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(newGameIntent);
                    finish();
                }
            }*/

            //Log.i("MS Play", "No Man LAND");
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
            tryAgain = Board.hasExtraneous(clickMap);
        }
        winMap = clickMap;
        squaresGoal = Board.getSquares(clickMap);
        //TextView stip = (TextView) mainActivity.findViewById(R.id.stip);
        TextView stip = (TextView) curActivity.findViewById(R.id.stip);

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