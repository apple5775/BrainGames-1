package com.example.root.braingames.Matchsticks;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.root.braingames.Button;
import com.example.root.braingames.MainActivity;
import com.example.root.braingames.R;

public class Board extends GridLayout {
    public int[][] clickMap;
    public Button[][] sticks;
    private MatchstickGame matchstickGame;

    public Board(MainActivity mainActivity, MatchstickGame matchstickG) {
        super(mainActivity);
        setColumnCount(7);
        setRowCount(7);

        matchstickGame = matchstickG;
        clickMap = makeDefaultBoard();
        sticks = new Button[7][7];
        OnClickListener boardOnClickListener;

        boardOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!matchstickGame.timer.paused){
                       if (v != null) {
                            v.setVisibility(View.INVISIBLE);
                            clickMap[((Button)v).getRow()][((Button)v).getCol()] = 0;
                            matchstickGame.registerClick();
                    }
                }
            }

        };

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                Button button = new Button(mainActivity, row, col);
                if (row % 2 == 0 && col % 2 == 1)
                    button.setImageResource(R.drawable.matchstick_horizontal);
                if (row % 2 == 1 && col % 2 == 0)
                    button.setImageResource(R.drawable.matchstick_vertical);
                if (row % 2 != col % 2) {
                    button.setOnClickListener(boardOnClickListener);
                    sticks[row][col] = button;
                    addView(sticks[row][col], sticks[row][col].getParams(Button.MATCHSTICK_PARAMS));
                }
            }
        }
    }

    public static int[][] makeDefaultBoard(){
        int[][] defaultBoard = new int[7][7];
        for (int r=0; r<7; r++)
            for (int c=0; c<7; c++)
                if (r % 2 != c % 2)
                    defaultBoard[r][c] = (c%2)+1;
        return defaultBoard;
    }

    public boolean hasExtraneous(){
        return hasExtraneous(clickMap);
    }

    public static boolean hasExtraneous(int[][] clicks){
        for (int row=0; row<7; row++)
            for (int col=(row+1)%2; col<7; col += 2) {
//             for (int col=0; col<7; col++){
                boolean foundLeft = false;
                boolean foundRight = false;
                boolean[] directions = new boolean[8];
                //These checks are used for all maps
                if (col > 0 && row > 0 && clicks[row - 1][col - 1] != 0)
                    directions[0] = true;
                if (col > 0 && row < 6 && clicks[row + 1][col - 1] != 0)
                    directions[5] = true;
                if (col < 6 && row > 0 && clicks[row - 1][col + 1] != 0)
                    directions[2] = true;
                if (col < 6 && row < 6 && clicks[row + 1][col + 1] != 0)
                    directions[7] = true;

                if (col > 1 && clicks[row][col - 2] != 0)
                    directions[3] = true;
                if (col < 5 && clicks[row][col + 2] != 0)
                    directions[4] = true;

                if (row > 1 && clicks[row - 2][col] != 0)
                    directions[1] = true;
                if (row < 5 && clicks[row + 2][col] != 0)
                    directions[6] = true;

                //If it is horizontal
                if (row % 2 == 0 && (directions[0] || directions[3] || directions[5]))
                    foundLeft = true;
                if (row % 2 == 0 && (directions[2] || directions[4] || directions[7]))
                    foundRight = true;

                //If vertical
                //left = top
                //right = bottom
                if (row % 2 == 1 && (directions[0] || directions[1] || directions[2]))
                    foundLeft = true;
                if (row % 2 == 1 && (directions[5] || directions[6] || directions[7]))
                    foundRight = true;

                if (!foundLeft || !foundRight) {
/**
                    String notFound = "left";
                    if (!foundRight)
                        notFound = "right";
                    if (!foundLeft && !foundLeft)
                        notFound = "either";
                    Log.i("Rval", "Row: " + row + " Col: " + col + " Not found: " + notFound);
*/
                    return true;
                }
            }
        return false;
    }

    public int getSquares(){
        return getSquares(clickMap);
    }

    public static int getSquares(int[][] clicks){
        String clickMapString = "";
        for (int[] row : clicks)
            for (int el : row)
                clickMapString += Integer.toString(el);
        String oXoReg = "020[0-2]{4}101[0-2]{4}020[0-2]*";
        String twXtwReg = "02020[0-2]{2}1[0-2]{3}1[0-2]{9}1[0-2]{3}1[0-2]{2}02020[0-2]*";
        String thXthReg = "02020201[0-2]{5}1[0-2]{7}1[0-2]{5}1[0-2]{7}1[0-2]{5}10202020";
        int totalCount = 0;
        //Check for 1x1 squares and 2x2 squares
        for (int start=0; start<clickMapString.length(); start++){
            if (clickMapString.substring(start).matches(oXoReg))
                totalCount++;
            if (clickMapString.substring(start).matches(twXtwReg))
                totalCount++;
        }
        //Check for 3x3 squares
        if (clickMapString.matches(thXthReg))
            totalCount++;
        return totalCount;
    }
}
