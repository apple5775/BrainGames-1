package com.example.root.braingames;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class MatchstickGame implements StateChange {
    private Timer timer;
    private MainActivity mainActivity;
    private int[][] clickMap;
    private Button[][] sticks;
    private int squaresGoal, clicksAllowed, clicksDone;

    public MatchstickGame(final MainActivity mainActivity){
        this.mainActivity = mainActivity;
        mainActivity.setContentView(R.layout.matchstickgame);
        generateProblem();
        clickMap = new int[7][7];
        for (int row=0; row<7; row++)
            for (int col=0; col<7; col++){
                if (row % 2 == 0 && col % 2 == 1)
                    clickMap[row][col] = 2;
                else if (row % 2 == 1 && col % 2 == 0)
                    clickMap[row][col] = 1;
            }

        GridLayout gridLayout = (GridLayout) mainActivity.findViewById(R.id.board);
        sticks = new Button[7][7];
        gridLayout.removeAllViews();
        for(int row = 0; row < 7; row++)
            for (int col = 0; col < 7; col++) {
                Button h = new Button(mainActivity, row, col);
                if (row % 2 == 0 && col % 2 == 1)
                    h.setImageResource(R.drawable.matchstick_horizontal);
                else if (row % 2 == 1 && col % 2 == 0)
                    h.setImageResource(R.drawable.matchstick_vertical);
                sticks[row][col] = h;
                sticks[row][col].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button button = (Button) view;
                        if (!timer.paused) {
                            button.setVisibility(View.INVISIBLE);
                            clickMap[button.getRow()][button.getCol()] = 0;
                            if (++clicksDone == clicksAllowed){
                                if (hasExtraneous())
                                    Log.i("Win status", "Lose");
                                if (getSquares() == squaresGoal)
                                    Log.i("win status", "Win!");
                                new MatchstickGame(mainActivity);
                            }
                        }
                    }
                });
                gridLayout.addView(sticks[row][col], sticks[row][col].getParams((1/7.0), (2/ 21.0)));
            }
        startTimer();
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

    public void startTimer() {
        TextView time = (TextView) mainActivity.findViewById(R.id.timer);
        timer = new Timer(time, mainActivity);
        timer.start();
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

    public boolean hasExtraneous(){
        for (Button[] b : sticks)
            for (Button button : b) {
                int row = button.getRow();
                int col = button.getCol();
                boolean foundLeft = false;
                boolean foundRight = false;
                //These checks are used for all maps
                if (col > 0 && row > 0 && clickMap[row - 1][col - 1] == 1)
                    foundLeft = true;
                else if (col > 0 && row < 6 && clickMap[row + 1][col - 1] == 1)
                    foundLeft = true;
                if (col < 6 && row > 0 && clickMap[row - 1][col + 1] == 1)
                    foundRight = true;
                else if (col < 6 && row < 6 && clickMap[row + 1][col + 1] == 1)
                    foundRight = true;

                //If it is horizontal and both sides haven't been found
                if (row % 2 == 0 && !(foundRight && foundLeft)) {
                    if (col > 1 && clickMap[row][col - 2] == 1)
                        foundLeft = true;
                    if (col < 5 && clickMap[row][col + 2] == 1)
                        foundRight = true;
                }

                //If it is vertical and both sides haven't been found
                //foundRight indicates if it found the top here
                //foundLeft indicates if it found the bottom here
                if (row % 2 == 1 && !(foundRight && foundLeft)) {
                    if (row > 1 && clickMap[row - 2][col] == 1)
                        foundLeft = true;
                    if (row < 5 && clickMap[row + 2][col] == 1)
                        foundRight = true;
                }

                //If it didn't find one side, the stick is extraneous
                if (!foundLeft || !foundRight)
                    return true;
            }
        return false;
    }

    public int getSquares(){
        String board = "";
        for (int[] row : clickMap)
            for (int el : row)
                board += Integer.toString(el);
        String oXoReg = "020[0-2]{4}101[0-2]{4}020";
        String twXtwReg = "02020[0-2]{2}1[0-2]{3}1[0-2]{9}1[0-2]{3}1[0-2]{2}02020";
        String thXthReg = "02020201[0-2]{5}1[0-2]{7}1[0-2]{5}1[0-2]{7}1[0-2]{5}10202020";
        int totalCount = 0;
        //Check for 1x1 squares
        for (int start=0; start<33; start++)
            if (board.substring(start, start+17).matches(oXoReg))
                totalCount++;
        //Check for 2x2 squares
        for (int start=0; start<17; start++)
            if (board.substring(start, start+33).matches(twXtwReg))
                totalCount++;
        //Check for a 3x3 square
        if (board.matches(thXthReg))
            totalCount++;
        return totalCount;
    }
}