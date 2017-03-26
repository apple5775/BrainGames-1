package com.example.root.braingames.Matchsticks;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
        clickMap = new int[7][7];
        sticks = new Button[7][7];
        boolean isButton = false;
        GridLayout boardLayout = (GridLayout) mainActivity.findViewById(R.id.board);
        OnClickListener boardOnClickListener;

        boardOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean found = false;
//                int row=0, col=0;
                Log.i("Listener", "V may or may not be null");
                if (!matchstickGame.timer.paused){
                       if (v != null) {
                            v.setVisibility(View.INVISIBLE);
                            clickMap[((Button)v).getRow()][((Button)v).getCol()] = 0;
                            matchstickGame.registerClick();    //Welp
                            Log.i("Listener", "The listener was called");
                    }
                }
            }

        };

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                Button button = new Button(mainActivity, row, col);
                if (row % 2 == 0 && col % 2 == 1) {
                    clickMap[row][col] = 2;
                    button.setImageResource(R.drawable.matchstick_horizontal);
                    isButton = true;
                }
                if (row % 2 == 1 && col % 2 == 0) {
                    clickMap[row][col] = 1;
                    button.setImageResource(R.drawable.matchstick_vertical);
                    isButton = true;
                }
                if (isButton == true) {
                    button.setOnClickListener(boardOnClickListener);
                    sticks[row][col] = button;
                    boardLayout.addView(sticks[row][col], sticks[row][col].getParams((1 / 7.0), (2 / 21.0)));

                } else {
                    sticks[row][col] = null;
                }
                isButton = false;
            }
        }
    }

    public String toString(){
        String rString = "";
        for (int[] row : clickMap)
            for (int el : row)
                rString += Integer.toString(el);
        return rString;
    }
}
