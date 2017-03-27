package com.example.root.braingames.Matchsticks;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridLayout;

import com.example.root.braingames.Button;
import com.example.root.braingames.MainActivity;
import com.example.root.braingames.R;
import com.example.root.braingames.StateChange;
import com.example.root.braingames.Timer;

public class MatchstickGame implements StateChange {
    public Timer timer;
    private MainActivity mainActivity;
    private Board board;
    private int squaresGoal, clicksAllowed, clicksDone;

    public MatchstickGame(final MainActivity mainActivity){
        this.mainActivity = mainActivity;
        mainActivity.setContentView(R.layout.matchstickgame);
        board = new Board(mainActivity, this);
        ((GridLayout) mainActivity.findViewById(R.id.board)).addView(board);

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
            if (hasExtraneous())
                Log.i("Win status", "Lose");
            if (getSquares() == squaresGoal)
                Log.i("win status", "Win!");
            new MatchstickGame(mainActivity);
        }
    }

    public void generateProblem(){
        squaresGoal = 7;
        clicksAllowed = 4;
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

    /*
        0 1 2
        3   4
        5 6 7
     */

    public boolean hasExtraneous(){
        for (int row=0; row<7; row++)
            for (int col=(row+1)%2; col<7; col += 2) {
                boolean foundLeft = false;
                boolean foundRight = false;
                boolean[] directions = new boolean[8];
                //These checks are used for all maps
                if (col > 0 && row > 0 && board.clickMap[row - 1][col - 1] != 0)
                    directions[0] = true;
                if (col > 0 && row < 6 && board.clickMap[row + 1][col - 1] != 0)
                    directions[5] = true;
                if (col < 6 && row > 0 && board.clickMap[row - 1][col + 1] != 0)
                    directions[2] = true;
                if (col < 6 && row < 6 && board.clickMap[row + 1][col + 1] != 0)
                    directions[7] = true;

                if (col > 1 && board.clickMap[row][col - 2] != 0)
                    directions[3] = true;
                if (col < 5 && board.clickMap[row][col + 2] != 0)
                    directions[4] = true;

                if (row > 1 && board.clickMap[row - 2][col] != 0)
                    directions[1] = true;
                if (row < 5 && board.clickMap[row + 2][col] != 0)
                    directions[6] = true;

                //If it is horizontal
                if (row % 2 == 0 && directions[0] || directions[3] || directions[5])
                    foundLeft = true;
                if (row % 2 == 0 && directions[2] || directions[4] || directions[7])
                    foundRight = true;

                //If vertical
                //left = top
                //right = bottom
                if (row % 2 == 1 && directions[0] || directions[1] || directions[2])
                    foundLeft = true;
                if (row % 2 == 1 && directions[5] || directions[6] || directions[7])
                    foundRight = true;

                if (!foundLeft || !foundRight){
                    String notFound = "left";
                    if (!foundRight)
                        notFound = "right";
                    if (!foundRight && !foundLeft)
                        notFound = "both";
                    Log.i("Found", "Row: " + row + " Col: " + col + " Didn't find: " + notFound);
                    return true;
                }
            }
        return false;
    }

    public int getSquares(){
        String clickMap = board.toString();
        String oXoReg = "020[0-2]{4}101[0-2]{4}020[0-2]*";
        String twXtwReg = "02020[0-2]{2}1[0-2]{3}1[0-2]{9}1[0-2]{3}1[0-2]{2}02020[0-2]*";
        String thXthReg = "02020201[0-2]{5}1[0-2]{7}1[0-2]{5}1[0-2]{7}1[0-2]{5}10202020";
        int totalCount = 0;
        //Check for 1x1 squares and 2x2 squares
        for (int start=0; start<clickMap.length(); start++){
            if (clickMap.substring(start).matches(oXoReg))
                totalCount++;
            if (clickMap.substring(start).matches(twXtwReg))
                totalCount++;
        }
        //Check for 3x3 squares
        if (clickMap.matches(thXthReg))
            totalCount++;
        return totalCount;
    }
}